package br.ufjf.ontology.gnosis.similarity;

import java.io.PrintWriter;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jairo
 */
public class AnalyserTest {

     public static void main(String[] args) throws Exception {
         Analyser analyser = new Analyser("xml/xml_benchmark.xml");
         analyser.process();
     }

}
