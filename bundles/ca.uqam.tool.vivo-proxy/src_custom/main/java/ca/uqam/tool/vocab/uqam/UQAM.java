package ca.uqam.tool.vocab.uqam;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

public class UQAM {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static final Model M_MODEL = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
	static public String NS = "http://purl.org/vivo.uqam.ca/data/";

	
	static public String deptID_NS = NS+"dept#id";
    public static String getURI() {return NS;}
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = M_MODEL.createResource( NS );
    
    public static Resource createDept(String id){
        return M_MODEL.createResource( deptID_NS+id );
    }
}
