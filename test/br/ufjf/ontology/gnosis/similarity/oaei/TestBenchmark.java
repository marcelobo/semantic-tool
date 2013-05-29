package br.ufjf.ontology.gnosis.similarity.oaei;

import br.ufjf.ontology.gnosis.MatcherBenchmak2007;

/**
 *
 * @author jairo
 */
public class TestBenchmark {

        public static void main(String[] args) throws Exception {

            String CWD = "/home/jairo/Dropbox/documentos/Acadêmicos/Doutorado/Fase_final/Avaliacao/Teste/benchmarks";
            
//Verificar se os que estão QUASE BOM dão um bom resultado total mesmo assim
//OS REFAZER talvez tenha que gerar o teste de novo pra tentar encontrar melhor resultado          
            
            MatcherBenchmak2007 mb = new MatcherBenchmak2007(CWD);
            //mb.runTest(101, "xml/benchmark/bench101.xml", true);
            //mb.runTest(102, "xml/benchmark/bench102.xml", true);
            //mb.runTest(103, "xml/benchmark/bench103.xml", true);
            //mb.runTest(104, "xml/benchmark/bench104.xml", true);
            //mb.runTest(201, "xml/benchmark/bench201.xml", true);

            //mb.runTest(202, "xml/benchmark/bench202.xml", true);

            //mb.runTest(203, "xml/benchmark/bench203.xml", true);
            //mb.runTest(204, "xml/benchmark/bench204.xml", true);

//Quase bom //mb.runTest(205, "xml/benchmark/bench205.xml", true);
//Quase bom //mb.runTest(206, "xml/benchmark/bench206.xml", true);
//Quase bom //mb.runTest(207, "xml/benchmark/bench207.xml", true);

//Quase bom //mb.runTest(208, "xml/benchmark/bench208.xml", true);
            //mb.runTest(209, "xml/benchmark/bench209.xml", true);
//Quase bom //mb.runTest(210, "xml/benchmark/bench210.xml", true);

            //mb.runTest(221, "xml/benchmark/bench221.xml", true);
            //mb.runTest(222, "xml/benchmark/bench222.xml", true);
            //mb.runTest(223, "xml/benchmark/bench223.xml", true);
            //mb.runTest(224, "xml/benchmark/bench224.xml", true);
            //mb.runTest(225, "xml/benchmark/bench225.xml", true);
            //mb.runTest(228, "xml/benchmark/bench228.xml", true);
            //mb.runTest(230, "xml/benchmark/bench230.xml", true);
            //mb.runTest(232, "xml/benchmark/bench232.xml", true);
            //mb.runTest(233, "xml/benchmark/bench233.xml", true);
            //mb.runTest(236, "xml/benchmark/bench236.xml", true);
            //mb.runTest(237, "xml/benchmark/bench237.xml", true);
            //mb.runTest(238, "xml/benchmark/bench238.xml", true);
            //mb.runTest(239, "xml/benchmark/bench239.xml", true);
            //mb.runTest(240, "xml/benchmark/bench240.xml", true);
            //mb.runTest(241, "xml/benchmark/bench241.xml", true);
            //mb.runTest(246, "xml/benchmark/bench246.xml", true);
            //mb.runTest(247, "xml/benchmark/bench247.xml", true);
            //mb.runTest(301, "xml/benchmark/bench301.xml", true);
            
//COMPARAR //mb.runTest(302, "xml/benchmark/bench302.xml", true);

           mb.runTest(303, "xml/benchmark/bench303.xml", true);

//COMPARAR //mb.runTest(304, "xml/benchmark/bench304.xml", true);
        
        
        }

}
