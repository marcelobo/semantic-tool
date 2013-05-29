package br.ufjf.ontology.gnosis.similarity.oaei;

import fr.inrialpes.exmo.align.impl.BasicAlignment;
import fr.inrialpes.exmo.align.impl.DistanceAlignment;
import fr.inrialpes.exmo.align.impl.URIAlignment;
import org.semanticweb.owl.align.Alignment;

// Alignment API implementation classes

import java.util.Properties;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.Parameters;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLProperty;

/**
 * The Skeleton of code for extending the alignment API
 */

public class NewMatcher extends DistanceAlignment implements AlignmentProcess {


    public NewMatcher() {
    }

    /**
     * The only method to implement is align.
     * All the resources for reading the ontologies and rendering the alignment are from ObjectAlignment and its superclasses:
     * - ontology1() and ontology2() returns objects LoadedOntology
     * - addAlignCell adds a new mapping in the alignment object
     */
    
    public void align( Alignment alignment, Parameters param ) throws AlignmentException {
	try {
	    // Match classes
	    for ( Object cl2: ((OWLOntology)this.getOntology2()).getClasses() ){
		for ( Object cl1: ((OWLOntology)this.getOntology1()).getClasses() ){
		    // add mapping into alignment object
		    if(matchClasses(cl1,cl2) == 1) addAlignCell(cl1,cl2,"=", 1);
		}
	    }
	    // Match dataProperties
	    for ( Object p2: ((OWLOntology)this.getOntology2()).getDataProperties() ){
		for ( Object p1: ((OWLOntology)this.getOntology1()).getDataProperties() ){
		    // add mapping into alignment object
		    if(matchProperty(p1,p2) == 1) addAlignCell(p1,p2,"=", 1);
		}
	    }
	    // Match objectProperties
	    for ( Object p2: ((OWLOntology)this.getOntology2()).getObjectProperties() ){
		for ( Object p1: ((OWLOntology)this.getOntology1()).getObjectProperties() ){
		    // add mapping into alignment object
		    if(matchProperty(p1,p2) == 1) addAlignCell(p1,p2,"=", 1);
		}
	    }
	} catch (Exception e) { e.printStackTrace(); }
    }

    /*    */
    public double matchClasses(Object o1, Object o2) throws AlignmentException, OWLException {
        OWLClass oc1 = (OWLClass) o1;
        OWLClass oc2 = (OWLClass) o2;
        String s1 = oc1.getURI().getFragment();
	String s2 = oc2.getURI().getFragment();
	   if (s1 == null || s2 == null) return 0.;
	   if (s1.toLowerCase().equals(s2.toLowerCase())) {
		return 1.0;
	    } else {
		return 0.;
	    }
    }

    public double matchProperty(Object o1, Object o2) throws AlignmentException, OWLException {
        OWLProperty oc1 = (OWLProperty) o1;
        OWLProperty oc2 = (OWLProperty) o2;
        String s1 = oc1.getURI().getFragment();
	String s2 = oc2.getURI().getFragment();
	   if (s1 == null || s2 == null) return 0.;
	   if (s1.toLowerCase().equals(s2.toLowerCase())) {
		return 1.0;
	    } else {
		return 0.;
	    }
    }




}