package br.ufjf.ontology.gnosis.test;

import br.ufjf.ontology.gnosis.test.SimVO;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.*;

public class TesteInutil {
public static void main(String[] args) {
    
        String owlNome = "/home/jairo/Dropbox/documentos/AcadÍmicos/Doutorado/Fase_final/Avaliacao/Teste/benchmarks/206/onto.rdf";
    
        OntModel m1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        OntDocumentManager dm1 = m1.getDocumentManager();
        dm1.setProcessImports(false);
        m1.read("file://" + owlNome,  null);
    
        
        OntClass classe = null;
        OntProperty op=null;
        Property p = op;
        
        for (OntClass oc : m1.listClasses().toList()) 
           if ("Personnes".equals(oc.getLocalName())) {
                classe = oc;
                break;
            }

        System.out.println(classe.getLocalName());
        
        
        //Set lista = new HashSet();
        //TesteInutil.addPropriedadesHerdadas(classe, lista);
        
        //lista.addAll(classe.listDeclaredProperties(true).toList());
                
          /*              
        System.out.println("No final:");
        for (Object object : lista) {
            System.out.println(object);
        }*/
        
     /**       for (Restriction re : m1.listRestrictions().toList()) {
                if (re.getOnProperty() != null && re.getOnProperty().getDomain() != null) {
                    if (re.isCardinalityRestriction()) {
                        System.out.println(re.getOnProperty().getInverse().getrLocalName());
                    }
                    //System.out.println(re.getOnProperty().getDomain().getLocalName() + " = " + re.getOnProperty().getLocalName());
                }
            }**/
                
//        for(Statement ont : classe.listProperties().toList())
//            System.out.println(">> " + ont.getPredicate().getLocalName());
   
        
        

  /*      List<Individual> individuos = m1.listIndividuals().toList();
       
        for (OntResource or : classe.listInstances(true).toList()) {
            System.out.println(">>>Imprimindo individuo: "+ (or.isAnon() ? "vazio" : or.getLocalName()));
            
            for(Statement st : or.listProperties().toList()) {
                if (!st.getObject().isAnon() && !st.getPredicate().getLocalName().equals("type")) {
                    String valor1="";
                    if(!st.getPredicate().getLocalName().equals("type")) {                    
                        valor1 = st.getObject().toString();
                        if (valor1.indexOf("^^") != -1)
                            valor1 = valor1.substring(0, valor1.indexOf("^^"));

                        for (Individual ind : individuos) {
                            for (Statement st2 : ind.listProperties().toList()) {
                                if (!st2.getObject().isAnon() && !st2.getPredicate().getLocalName().equals("type")) {
                                    String valor2="";
                                    if(st2.getPredicate().getLocalName().equals(st.getPredicate().getLocalName())) {                    
                                        valor2 = st2.getObject().toString();
                                        if (valor2.indexOf("^^") != -1)
                                            valor2 = valor2.substring(0, valor2.indexOf("^^"));

                                        System.out.println("Comparando: " + st.getPredicate().getLocalName()+"#"+ valor1 + " x " + st2.getPredicate().getLocalName()+"#"+ valor2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println();
        }
*/
    
    Collection<SimVO> similaridades = new ArrayList<SimVO>();

    //Esse teste abaixo falha com o Deep...
    /*similaridades.add(new SimVO("a", "b", 1));
    similaridades.add(new SimVO("c", "d", 1));*/
    
/*    similaridades.add(new SimVO("a1", "b3", 0.6f));
    similaridades.add(new SimVO("a2", "b1", 0.4f));
    similaridades.add(new SimVO("a2", "b2", 0.7f));
    similaridades.add(new SimVO("a1", "b1", 0.3f));
    similaridades.add(new SimVO("a3", "b1", 0.3f));
//    similaridades.add(new SimVO("a1", "b2", 0.8f));
    
    similaridades = TesteInutil.exploreFirstMatch(similaridades);
    for (SimVO simVO : similaridades) {
        System.out.println(simVO);
    }
  */  
    
    
    }


    public static Collection<SimVO> exploreFirstMatch(Collection<SimVO> similaridade){
        
        Collections.sort((List)similaridade, new Comparator<SimVO>() {
            public int compare(SimVO o1, SimVO o2) {
                return Float.compare(o2.getSimilarity(), o1.getSimilarity());
            }
        });
        
        //Copia uma lista de similaridade para poder fazer a remo√ß√£o
        Collection<SimVO> similaridadeAUX = new ArrayList<SimVO>();
        Collection<SimVO> similaridadeFINAL = new ArrayList<SimVO>();
        similaridadeAUX.addAll(similaridade);


       for (Iterator<SimVO> i = similaridade.iterator();i.hasNext();){
           SimVO similarityVO = (SimVO) i.next();
           if (similaridadeAUX.contains(similarityVO)){
               similaridadeFINAL.add(similarityVO);
               //Procura em todas as linhas os indicadores j√° combinados
               for (Iterator<SimVO> j = similaridade.iterator(); j.hasNext();){
                   SimVO similarityVOAUX = (SimVO) j.next();
                   if (!similaridadeFINAL.contains(similarityVOAUX)) {
                       if (similarityVO.getElementA().equals(similarityVOAUX.getElementA()) || similarityVO.getElementB().equals(similarityVOAUX.getElementB())){
                           similaridadeAUX.remove(similarityVOAUX);
                       }
                   }
               }
           }
        }
        return similaridadeAUX;
    }


    
    public  static Collection<SimVO> exploreDeep(Collection<SimVO> valores){
        Collection<SimVO> valorFinal = new ArrayList<SimVO>();
        Collection<SimVO> copiaValores = new ArrayList<SimVO>();
        Collection<SimVO> copiaValoresInicial = new ArrayList<SimVO>();
        Object v1Aux = null, v2Aux = null;
        double max = 0, soma = 0;
        boolean caminha = true;
        int cont = 0, cont1 = 0, cont2 = 0;

        //Calcule o numero de elementos que o resultado final ira possuir
        int value;
        if (valores.size()%2 == 0 ){
            value = valores.size()/2;
        }else {
            value = valores.size()/2 - valores.size()%2;
            if (value == -1) value = 1;
        }
        
        for(Iterator<SimVO> j = valores.iterator(); j.hasNext();){
            j.next();
            copiaValores.clear();
            for (Iterator<SimVO> i = valores.iterator(); i.hasNext();){
                SimVO s1 = (SimVO) i.next();
                if (cont2 < cont1){
                   s1.setSimilarity(0);
                 }
                copiaValores.add(s1);
                copiaValoresInicial.add(s1);
                ++cont2;
            }
            cont2 = 0;
            for (Iterator<SimVO> i = valores.iterator(); i.hasNext();){
               SimVO s2 = (SimVO) i.next();
               if (s2.getSimilarity() != 0){
                   ++cont;
                   v1Aux = s2.getElementA();
                   v2Aux = s2.getElementB();
                   for (Iterator<SimVO> x = copiaValores.iterator(); x.hasNext();){
                       SimVO s3 = (SimVO) x.next();
                       if (caminha){
                           while(!s2.equals(s3)){
                                s3 = (SimVO) x.next();
                           }                          
                           caminha = false;
                       }
                       if (!s2.equals(s3))
                       if (s3.getElementA().equals(v1Aux) || s3.getElementB().equals(v2Aux)){
                           s3.setSimilarity(0);
                       }
                   }
                   if (cont == 1) {
                       copiaValoresInicial.clear();
                       copiaValoresInicial.addAll(copiaValores);
                   }
                   if (cont == value){
                   for (Iterator<SimVO> x = copiaValores.iterator(); x.hasNext();){
                       SimVO s3 = (SimVO) x.next();
                       if (s3.getSimilarity() != 0){
                           soma += s3.getSimilarity();
                       }
                   }

                   //Verifica se a soma dos valores √© a maior e seta os valores na matriz final
                   if (soma > max){
                       max = soma;
                       for (Iterator<SimVO> x = copiaValores.iterator(); x.hasNext();){
                           SimVO s3 = (SimVO) x.next();
                           if (s3.getSimilarity() != 0){
                               //Cria um novo objeto setando os seus valores para guardar ao resultado final
                               SimVO similarityVO = new SimVO(s3.getElementA(), s3.getElementB(), s3.getSimilarity());
                               valorFinal.add(similarityVO);
                           }
                       }
                   }
                        s2.setSimilarity(0);
                        copiaValoresInicial.add(s2);
                        copiaValores.clear();
                        copiaValores.addAll(copiaValoresInicial);
                        cont = 1;
                   }
               }
           }
           cont = 0;
           ++cont1;
           cont2 = 0;
           caminha = true;
        }

        return valorFinal;

    }
    
    public static void addPropriedadesHerdadas(OntClass classe, Set lista) {
        for (OntClass superclasses : classe.listSuperClasses(true).toList()) {
            if (!superclasses.isAnon() && superclasses.getURI().startsWith(classe.getURI().substring(0,classe.getURI().indexOf("#")))) {
                addPropriedadesHerdadas(superclasses, lista);
                System.out.println("Superclasse: " + superclasses.getURI());
            }
            else if (superclasses.isRestriction()) {
                Restriction r = superclasses.asRestriction();
                lista.add(r.getOnProperty());
                System.out.println( "RestriÁ„o na propriedade " +
                            r.getOnProperty().getURI() );
            }
        }

    }
    
    
}


