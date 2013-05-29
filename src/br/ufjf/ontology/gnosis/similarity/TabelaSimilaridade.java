/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufjf.ontology.gnosis.similarity;

import br.ufjf.ontology.gnosis.similarity.combination.FirstMatchCombination;
import br.ufjf.ontology.gnosis.similarity.combination.ICombination;
import br.ufjf.ontology.gnosis.similarity.common.SimilarityNameComparator;
import br.ufjf.ontology.gnosis.similarity.common.SimilarityVO;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 *
 * @author Isabella
 */
public class TabelaSimilaridade {

    private static List<SimilarityVO> listaConceitos;

    public TabelaSimilaridade(List<SimilarityVO> lista) {
        listaConceitos = lista;
    }

    public static void inializa() {
        listaConceitos = new ArrayList<SimilarityVO>();
    }

    public static void adiciona(SimilarityVO vo) {
        listaConceitos.add(vo);
    }

    public static List<SimilarityVO> getListaSimilaridades() {
        return listaConceitos;
    }

    private void espaco(int espaco, PrintWriter printer) {
        for (int i = 0; i < espaco; i++) {
            printer.print(" ");
        }
    }

    private void tamanhoNome(int tam, String nome, PrintWriter printer) {
        if (nome.length() > tam) {
            printer.print(nome.substring(0, tam) + ".    ");
        } else {
            printer.print(nome);
            espaco(tam+5 - nome.length(), printer);
        }
    }

    public void imprimeLinha(PrintWriter printer) {
        
        Collections.sort(this.listaConceitos, new SimilarityNameComparator());
        printer.print("############################\n#Similaridade dos recursos\n\n");
        for(SimilarityVO similarityVO : listaConceitos) {
            printer.print("(");
            tamanhoNome(15, similarityVO.getElementA().getLocalName(), printer);
            printer.print(",");
            tamanhoNome(15, similarityVO.getElementB().getLocalName(), printer);
            printer.print(","+similarityVO.getSimilarity()+")\n");
        }
    }
    
    
    public void imprimeTabela(PrintWriter printer) {
        espaco(15, printer);
        String nome = "";
        NumberFormat f = new DecimalFormat("0.00");
        for (Iterator<SimilarityVO> j = listaConceitos.iterator(); j.hasNext();) {
            SimilarityVO s = (SimilarityVO) j.next();
            if (nome.isEmpty()) {
                nome = s.getElementB().getLocalName();
                tamanhoNome(10, nome, printer);
            } else {
                if (nome.equals(s.getElementB().getLocalName())) {
                    break;
                }
                tamanhoNome(10, s.getElementB().getLocalName(), printer);
            }
        }
        nome = "";
        for (Iterator<SimilarityVO> j = listaConceitos.iterator(); j.hasNext();) {
            SimilarityVO s1 = (SimilarityVO) j.next();
            if (!nome.equals(s1.getElementA().getLocalName())) {
                tamanhoNome(10, "\n" + s1.getElementA().getLocalName(), printer);
                nome = s1.getElementA().getLocalName();
            }
            espaco(2, printer);
            printer.print(f.format(s1.getSimilarity()));
            espaco(9, printer);
        }
    }

    private boolean contains(Collection<SimilarityVO> collec, SimilarityVO sVO) {
        for (SimilarityVO similarityVO : collec) {
            if (similarityVO.getElementA()== sVO.getElementA())// || similarityVO.getElementB() == sVO.getElementB())
                return true;
        }

        return false;
    }

    private Collection<SimilarityVO> getSimilaridadeOneToOneTrue() {        
        
        ICombination fm = new FirstMatchCombination();
        List<SimilarityVO> a = (List) fm.explore(listaConceitos);
        Collections.sort(a, new SimilarityNameComparator());
        return a;
    }


    /**
     * Esse método retorna os maiores alinhamentos encontrados para cada recurso
     * O parâmetro booleano determina se deseja alinhamentos serão sempre 1:1 ou se poderão ser n:1.
     * Ou seja, caso o parâmetro seja verdadeiro, os alinhamentos serão somente 1:1. 
     * Caso seja falso, poderão ser n:1, o que indica que mais de um conceito da ontologia 1 poderá ser alinhado com um elemento da ontologia 2.
     * @param allAlign 
     * @return 
     */
    public Collection<SimilarityVO> getSimilaridadeOneToOne(boolean allAlign) {        
        return allAlign ? getSimilaridadeOneToOneTrue() : getSimilarityOneToOneFalse();
    }

    private Collection<SimilarityVO> getSimilarityOneToOneFalse() {
                
        Collections.sort(listaConceitos, new SimilarityNameComparator());

        Collection<SimilarityVO> al = new ArrayList<SimilarityVO>();
        for (SimilarityVO similarityVO : listaConceitos) 
            if (!this.contains(al, similarityVO) && similarityVO.getSimilarity()>0 )
                al.add(similarityVO);

        return al;
    }
}
