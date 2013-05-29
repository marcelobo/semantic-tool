/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufjf.ontology.gnosis.similarity.oaei;

import fr.inrialpes.exmo.align.impl.BasicParameters;
import fr.inrialpes.exmo.align.impl.OntologyCache;
import fr.inrialpes.exmo.align.impl.eval.PRecEvaluator;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;
import org.semanticweb.owl.align.Evaluator;
import org.semanticweb.owl.align.Parameters;
import org.semanticweb.owl.io.owl_rdf.OWLRDFParser;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.util.OWLManager;

/**
 *
 * @author jairo
 */
public class TesteAlinhador {

    public static void main(String args[]) throws Exception {

        try {
            String CWD = "/home/jairo/Dropbox/documentos/Acadêmicos/Doutorado/Fase_final/Avaliacao/benchmarks";
            URI uri1 = new URI("file://" + CWD + "/onto.rdf");
            URI uri2 = new URI("file://" + CWD + "/101/onto.rdf");
            //URI uri1 = new URI("file://" + CWD + "/example/edu.umbc.ebiquity.publication.owl");
            //URI uri2 = new URI("file://" + CWD + "/example/edu.mit.visus.bibtex.owl");


            Parameters p = new BasicParameters();
            OntologyCache cache = new OntologyCache();
            OWLOntology O1 = loadOntology(uri1);
            OWLOntology O2 = loadOntology(uri2);

/*            AlignmentProcess A1 = new SubsDistNameAlignment();
            A1.init(uri1, uri2, cache);

            AlignmentProcess A2 = new SMOANameAlignment();
            A2.init(uri1, uri2, cache);

            AlignmentProcess A3 = new NameAndPropertyAlignment();
            A3.init(uri1, uri2, cache);

            A1.align((Alignment) null, p);
            A1.cut("prop", .5);

            A2.align((Alignment) null, p);
            A3.align(A2, p);

            Evaluator E = new PRecEvaluator(A1, A1);
            E.eval(p);
*/


            AlignmentProcess Anew = new NewMatcher();
            Anew.init(O1, O2);
            Anew.align(null, p);
     
            Evaluator E = new PRecEvaluator(Anew, Anew);
            E.eval(p);
            

            AlignmentVisitor V = new RDFRendererVisitor(
                    new PrintWriter(
                    new BufferedWriter(
                    new OutputStreamWriter(System.out, "UTF-8")),
                    true));

            if (((PRecEvaluator) E).getPrecision() > .6) {
                Anew.render(V);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static OWLOntology loadOntology(URI uri) throws Exception {
        OWLRDFParser parser = new OWLRDFParser();
        parser.setConnection(OWLManager.getOWLConnection());
        return parser.parseOntology(uri);
    }

}
