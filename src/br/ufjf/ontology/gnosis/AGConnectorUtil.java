/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.ontology.gnosis;

import br.ufjf.ontology.gnosis.ag.domain.FuncaoComposta;
import br.ufjf.ontology.gnosis.ag.domain.FuncaoSimples;
import br.ufjf.ontology.gnosis.ag.domain.IFuncao;
import br.ufjf.ontology.gnosis.ag.genetic.Gerador;
import br.ufjf.ontology.gnosis.ag.genetic.SimulaVida;
import br.ufjf.ontology.gnosis.similarity.FunctionContainer;
import br.ufjf.ontology.gnosis.similarity.common.FunctionWeightVO;
import com.hp.hpl.jena.ontology.OntResource;
import fr.inrialpes.exmo.align.parser.AlignmentParser;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.Cell;
import org.semanticweb.owl.model.OWLException;

/**
 *
 * @author jairo
 */
public class AGConnectorUtil {

    private List<OntResource> conceitosOnto1, conceitosOnto2;
    String filePath;
    ArrayList<IFuncao> equacoes = new ArrayList<IFuncao>();

    public AGConnectorUtil(String filePath, List<OntResource> conceitosOnto1, List<OntResource> conceitosOnto2) {
        this.conceitosOnto1 = conceitosOnto1;
        this.conceitosOnto2 = conceitosOnto2;
        this.filePath = filePath;
    }



    public void setPesosToContainer(FunctionContainer container) throws Exception {

        Alignment prealign = new AlignmentParser(0).parse("file://"+filePath);
        double[] resultadoDasEquacoes = new double[prealign.nbCells()];

        parserContainerToEquacoes(prealign, container, resultadoDasEquacoes);

        runAG(resultadoDasEquacoes);

        //Tendo o resultado do AG, preciso passar os pesos encontrados para o MainContainer
        parserEquacoesToContainer(((FuncaoComposta)equacoes.get(0)).getFilhos(), (List)container.getContainer());

    }

    private void parserContainerToEquacoes(Alignment prealign, FunctionContainer container, double[] resultadoDasEquacoes) throws AlignmentException, OWLException {
        int i = 0;
        for (Enumeration e = prealign.getElements(); e.hasMoreElements();) {
            Cell cell = (Cell) e.nextElement();
            if (cell.getObject1AsURI() != null && cell.getObject2AsURI() != null) {
                FuncaoComposta funcao = new FuncaoComposta();
                parserContainerToEquacoes(container, funcao, getEntidade(cell.getObject1AsURI().getFragment(), conceitosOnto1), getEntidade(cell.getObject2AsURI().getFragment(), conceitosOnto2));
                equacoes.add(funcao);
                resultadoDasEquacoes[i++] = cell.getStrength();
            }
        }
    }

    private void runAG(double[] resultadoDasEquacoes) {
        SimulaVida sv;
        try {
            sv = new SimulaVida(new Gerador(equacoes, resultadoDasEquacoes), "genetics.properties");
            sv.run();
        } catch (ConfigurationException ex) {
            Logger.getLogger(AGConnectorUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parserEquacoesToContainer(List<IFuncao> to, List<FunctionWeightVO> from) {

        for (int i=0; i<to.size(); i++) {
            from.get(i).getWeight().setWeight((float)to.get(i).getPeso());
            if(to.get(i) instanceof FuncaoComposta)
                parserEquacoesToContainer(to.get(i).getFilhos(), (List)((FunctionContainer)from.get(i).getFunction()).getContainer());
        }
    }

    
    private void parserContainerToEquacoes(FunctionContainer from, FuncaoComposta to, OntResource conceito1, OntResource conceito2) {
        for (FunctionWeightVO functionWeightVO : from.getContainer()) {
            if (functionWeightVO.getFunction() instanceof FunctionContainer) {
                FuncaoComposta novaFuncao = new FuncaoComposta();
                to.add(novaFuncao);
                parserContainerToEquacoes((FunctionContainer)functionWeightVO.getFunction(), novaFuncao, conceito1, conceito2);
            } else {
                FuncaoSimples novaFuncao = new FuncaoSimples(functionWeightVO.getFunction().execute(conceito1, conceito2));
                to.add(novaFuncao);
            }
        }
    }

    private OntResource getEntidade(String nome, List<OntResource> conceitos) throws OWLException {
        for (OntResource entidade : conceitos)
            if(nome.equals(entidade.getLocalName()))
                return (OntResource) entidade;
        
        return null;
    }

}
