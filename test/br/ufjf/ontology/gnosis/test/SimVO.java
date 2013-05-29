/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufjf.ontology.gnosis.test;

/**
 *
 * @author jairo
 */
public class SimVO {

    Object elementA;
    Object elementB;
    float similarity;

    public SimVO(Object elementA, Object elementB, float similarity) {
        this.elementA = elementA;
        this.elementB = elementB;
        this.similarity = similarity;
    }

    public Object getElementA() {
        return elementA;
    }

    public Object getElementB() {
        return elementB;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public void setElementA(Object elementA) {
        this.elementA = elementA;
    }

    public void setElementB(Object elementB) {
        this.elementB = elementB;
    }

    public float getSimilarity() {
        return similarity;
    }

    @Override
    public String toString() {
        return getElementA()+","+getElementB()+"="+getSimilarity();
    }
    
    
    
}
