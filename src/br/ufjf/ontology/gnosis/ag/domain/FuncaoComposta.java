/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.ontology.gnosis.ag.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author rooke
 */
public class FuncaoComposta implements IFuncao, Iterable<IFuncao>{

    private double peso;

    //Coleção de funções
    private ArrayList<IFuncao> funcoesFilhas = new ArrayList<IFuncao>();
    
    public BigDecimal calcula() {
        BigDecimal resultado = new BigDecimal(0);
        for (IFuncao funcao : funcoesFilhas) {
            resultado = resultado.add(funcao.calcula());
        }
        resultado = resultado.multiply(BigDecimal.valueOf(peso));
        return resultado;
    }

    public void setPeso(double pesoParam) {
        peso = pesoParam;
    }

    public double getPeso() {
        return peso;
    }

    //Adds uma função filha
    public void add(IFuncao funcao) {
        funcoesFilhas.add(funcao);
    }

    //Removes uma função filha
    public void remove(IFuncao funcao) {
        funcoesFilhas.remove(funcao);
    }

    public Iterator<IFuncao> iterator() {
        return funcoesFilhas.iterator();
    }

    public List<IFuncao> getFilhos() {
        return this.funcoesFilhas;
    }

}
