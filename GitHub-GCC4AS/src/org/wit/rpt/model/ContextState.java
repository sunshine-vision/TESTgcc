package org.wit.rpt.model;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * GCC model file, please do not modify this file.
 * 
 * @author Liu Wei
 */
public class ContextState {
	public static String csURI = "http://www.owl-ontologies.com/Ontology1535514050.owl#";
	public static String domainURI = "http://www.owl-ontologies.com/AGVs.owl#";
	public static String swrlURI = "http://www.w3.org/2017/11/swrl#";
	static OntModel csm;
	Individual csi;
	String csname;
	String cstype;
	String predicate;
	String argu1;
	String argu2;
	String classpredicate;
	String iargu;

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String fpcs = "file:D://GCCModel//contextstates1.owl";
		ContextStateModel csmi = new ContextStateModel(fpcs);
		ContextState cs1 = new ContextState(csmi, "cs7");
		System.out.println(cs1.getName() + "'s Test result:");
		System.out.println(cs1.getType());
		System.out.println(cs1.getClassPredicate());
		System.out.println(cs1.getIArgu());
//		System.out.println(cs1.getArgu2());
		ContextState cs2 = new ContextState(csmi, "cs7");
		System.out.println(cs2.getName() + "'s Test result:");
		System.out.println(cs2.getType());
		System.out.println(cs2.getClassPredicate());
		System.out.println(cs2.getIArgu());
	}

	/**
	 * This is a constructor.
	 * 
	 * @param csmi
	 * @param name
	 */
	public ContextState(ContextStateModel csmi, String name) {
		csm = csmi.getCSModel();
		csname = name;
		csi = csm.getIndividual(csURI + name);
	}

	/**
	 * Get all statements of the ContextState.
	 * 
	 * @param csm
	 */
	static void getAllStatements(ContextStateModel csm) {
		StmtIterator ss = csm.getCSModel().listStatements();
		while (ss.hasNext()) {
			Statement s = ss.nextStatement();
			Resource sub = s.getSubject();
			Property pre = s.getPredicate();
			RDFNode obj = s.getObject();
			System.out.print(sub.getLocalName() + ",");
			System.out.print(pre.getLocalName() + ",");
			System.out.println(obj.toString());
		}
	}

	/**
	 * Get the name of ContextState, return String type.
	 * 
	 * @return
	 */
	public String getName() {
		return csname;
	}

	/**
	 * Get the type of ContextState, return String type.
	 * 
	 * @return
	 */
	public String getType() {
		Property t = csm.getProperty("http://www.owl-ontologies.com/Ontology1535514050.owl#type");
		NodeIterator oi = csm.listObjectsOfProperty(csi, t);
		RDFNode o = null;
		String type = null;
		while (oi.hasNext()) {
			o = oi.nextNode();
			break;
		}
		if (o != null) {
			int leng = o.toString().indexOf("^");
			type = o.toString().substring(0, leng);

		} else {
			System.out.println("can not identify the cs type!");
		}
		return type;
	}

	/**
	 * Get the predicate of the ContextState of type
	 * IndividualPropertyAtom,return String type.
	 * 
	 * @return
	 */
	public String getPredicate() {
		Property pre = csm.getProperty(swrlURI + "propertyPredicate");
		NodeIterator oi = csm.listObjectsOfProperty(csi, pre);
		RDFNode o = null;
		while (oi.hasNext()) {
			o = oi.nextNode();
		}
		int leng = o.toString().indexOf("#");
		String prename = o.toString().substring(leng + 1, o.toString().length());
		return prename;
	}

	/**
	 * Get the argument1 of the ContextState of type
	 * IndividualPropertyAtom,return String type..
	 * 
	 * @return
	 */
	public String getArgu1() {
		Property arg1 = csm.getProperty(swrlURI + "argument1");
		NodeIterator oi = csm.listObjectsOfProperty(csi, arg1);
		RDFNode o = null;
		while (oi.hasNext()) {
			o = oi.nextNode();
		}
		int leng = o.toString().indexOf("#");
		String agu1name = o.toString().substring(leng + 1, o.toString().length());
		return agu1name;
	}

	/**
	 * Get the argument2 of the ContextState of type
	 * IndividualPropertyAtom,return String type.
	 * 
	 * @return
	 */
	public String getArgu2() {
		Property arg2 = csm.getProperty(swrlURI + "argument2");
		NodeIterator oi = csm.listObjectsOfProperty(csi, arg2);
		RDFNode o = null;
		while (oi.hasNext()) {
			o = oi.nextNode();
		}
		int leng = o.toString().indexOf("#");
		String agu2name = o.toString().substring(leng + 1, o.toString().length());
		return agu2name;
	}

	/**
	 * Get the predicate of the ContextState of type ClassAtom,return String
	 * type.
	 * 
	 * @return
	 */
	public String getClassPredicate() {
		Property cp = csm.getProperty(swrlURI + "classPredicate");
		NodeIterator oi = csm.listObjectsOfProperty(csi, cp);
		RDFNode o = null;
		while (oi.hasNext()) {
			o = oi.nextNode();
		}
		int leng = o.toString().indexOf("#");
		String classpredicate = o.toString().substring(leng + 1, o.toString().length());
		// String classpredicate = o.toString().substring(39);
		return classpredicate;
	}

	/**
	 * Get the argument of the ContextState of type ClassAtom,return String
	 * type.
	 * 
	 * @return
	 */
	public String getIArgu() {
		Property arg1 = csm.getProperty(swrlURI + "argument1");
		NodeIterator oi = csm.listObjectsOfProperty(csi, arg1);
		RDFNode o = null;
		while (oi.hasNext()) {
			o = oi.nextNode();
		}
		// String iaguname = o.toString().substring(39);
		int leng = o.toString().indexOf("#");
		String iaguname = o.toString().substring(leng + 1, o.toString().length());
		return iaguname;
	}

	/**
	 * Rewrite the equals method,return boolean type.
	 * 
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContextState) {
			ContextState t = (ContextState) obj;
			return this.csname.equals(t.csname);
		}
		return super.equals(obj);
	}

}
