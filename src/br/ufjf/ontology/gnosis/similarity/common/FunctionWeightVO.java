/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.ontology.gnosis.similarity.common;

import br.ufjf.ontology.gnosis.similarity.ISimilarityFunction;

/**
 *
 * @author Isabella Pires Capobiango
 */
public class FunctionWeightVO {

    private ISimilarityFunction function;
    private WeightVO weight;

    public void setFunction(ISimilarityFunction function){
        this.function = function;
    }

    public void setWeight(WeightVO weight){
        this.weight = weight;
    }

    public ISimilarityFunction getFunction(){
        return function;
    }

    public WeightVO getWeight(){
        return weight;
    }




}
