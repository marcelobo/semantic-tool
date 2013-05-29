
package br.ufjf.ontology.gnosis;

import java.util.Enumeration;
import java.io.PrintWriter;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentVisitor;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.Cell;
import org.semanticweb.owl.align.Relation;

public class TextRendererVisitor implements AlignmentVisitor
{

    PrintWriter writer = null;
    Alignment alignment = null;
    Cell cell = null;

    public TextRendererVisitor( PrintWriter writer ){
	this.writer = writer;
    }

    public void visit( Alignment align ) throws AlignmentException {
	alignment = align;
	writer.print("------------------\n");
	writer.print("Alinhamentos:\n");
	writer.print("------------------\n");
	writer.print("De...: "+align.getOntology1URI().toString()+"\n" );
	if ( align.getFile1() != null )
	    writer.print("De:...:"+align.getFile1().toString()+"\n" );
	writer.print("Para.: "+align.getOntology2URI().toString()+"\n" );
	if ( align.getFile2() != null )
	    writer.print("Para.:"+align.getFile2().toString()+"\n" );

	for( Enumeration e = align.getElements() ; e.hasMoreElements(); ){
	    Cell c = (Cell)e.nextElement();
	    c.accept( this );
	} //end for
    }

    public void visit( Cell cell ) throws AlignmentException {
	this.cell = cell;
	writer.print(cell.getObject1AsURI().toString());
	cell.getRelation().accept( this );
	writer.print(cell.getObject2AsURI().toString());
	//if ( !cell.getSemantics().equals("first-order") )
	//	writer.print("      <semantics>"+cell.getSemantics()+"</semantics>\n");
	writer.println("\n");
    }
    public void visit( Relation rel ) {
	rel.write( writer );
    };
}