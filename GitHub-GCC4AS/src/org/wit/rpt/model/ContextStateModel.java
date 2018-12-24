package org.wit.rpt.model;

import java.util.ArrayList;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * GCC model file, please do not modify this file.
 * 
 * @author Liu Wei.
 */
public class ContextStateModel {
	static String fpcc = "file:D://GCCModel//CCmodel.owl";
	static String fpcs = "file:D://GCCModel//contextstates1.owl";
	static String fpd = "file:D://GCCModel//Domain.owl";
	public static String csURI = "http://www.owl-ontologies.com/Ontology1535514050.owl#";
	public static String domainURI = "http://www.owl-ontologies.com/AGVs.owl#";
	static OntModel gm;
	static OntModel dm;
	static OntModel csm;
	static OntModel cm;

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ContextStateModel csmi = new ContextStateModel(fpcs);
		StmtIterator ss = csmi.getCSModel().listStatements();
		while (ss.hasNext()) {
			Statement s = ss.nextStatement();
			Resource sub = s.getSubject();
			ContextState cs = new ContextState(csmi, sub.getLocalName());
			System.out.print(sub.getLocalName() + ",");
			System.out.println(cs.getType());

		}
		ContextState cs = new ContextState(csmi, "cs2");
		ContextState csed = new ContextState(csmi, "cs8");
		completeMatchwith(cs, csed);

	}

	/**
	 * This is a constructor.
	 * 
	 * @param fpcs
	 */
	public ContextStateModel(String fpcs) {
		csm = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		csm.read(fpcs);
		dm = getDomainmodel(fpd);
	}

	/**
	 * Get the OntModel of the ContextState.
	 * 
	 * @return
	 */
	public OntModel getCSModel() {
		return csm;
	}

	/**
	 * Get the OntModel based on the parameters (OWL file).
	 * 
	 * @param fpd
	 * @return
	 */
	public OntModel getDomainmodel(String fpd) {
		dm = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		dm.read(fpd);
		return dm;
	}

	/**
	 * The semantic match for ContextState ArrayList, return boolean type.
	 * 
	 * @param csi
	 * @param csied
	 * @return
	 * @update updated by Li Shuang.
	 */
	public static boolean semanticMatch(ArrayList<ContextState> csi, ArrayList<ContextState> csied) {
		int i = 0;
		int temp = 0;
		if (csi.size() < 1 || csied.size() < 1) {
			return false;
		} else {
			i = csi.size();
			temp = 0;
			for (int j = 0; j < i; j++) {
				ContextState cs = csi.get(j);
				for (int k = 0; k < csied.size(); k++) {
					ContextState csed = csied.get(k);
					if (completeMatchwith(cs, csed) || partMatchwith(cs, csed)) {
						temp++;
					}
				}
			}
		}
		if (temp == i)
			return true;
		else
			return false;
	}

	/**
	 * The complete semantic match for ContextState, return boolean type.
	 * 
	 * @param cs
	 * @param csed
	 * @return
	 */
	public static boolean completeMatchwith(ContextState cs, ContextState csed) {
		boolean result = false;
		String cst = cs.getType();
		String csedt = csed.getType();
		if (cst.equals(csedt)) {
			if (cs.getType().equals("IndividualPropertyAtom")) {
				String property = cs.getPredicate();
				String propertyed = csed.getPredicate();
				String argu1 = cs.getArgu1();
				Individual arg1i = dm.getIndividual(domainURI + argu1);
				OntClass oc1 = (OntClass) arg1i.getOntClass();
				String argu1ed = csed.getArgu1();
				Individual arg1edi = dm.getIndividual(domainURI + argu1ed);
				OntClass oc1ed = arg1edi.getOntClass();
				String argu2 = cs.getArgu2();
				Individual arg2i = dm.getIndividual(domainURI + argu2);
				OntClass oc2 = arg2i.getOntClass();
				String argu2ed = csed.getArgu2();
				Individual arg2edi = dm.getIndividual(domainURI + argu2ed);
				OntClass oc2ed = arg2edi.getOntClass();
				if (property.matches(propertyed) && compareOntClasses(oc1, oc1ed) && compareOntClasses(oc2, oc2ed)) {
					result = true;
				}
			} else if (cs.getType().equals("ClassAtom")) {
				String predicate = cs.getClassPredicate();
				String predicated = csed.getClassPredicate();
				String argu = cs.getIArgu();
				Individual argi = dm.getIndividual(domainURI + argu);
				OntClass oc1 = argi.getOntClass();
				String argued = csed.getIArgu();
				Individual argied = dm.getIndividual(domainURI + argued);
				OntClass oc1ed = argied.getOntClass();
				if (predicate.matches(predicated) && compareOntClasses(oc1, oc1ed)) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * The part semantic match for ContextState, return boolean type.
	 * 
	 * @param cs
	 * @param csed
	 * @return
	 */
	public static boolean partMatchwith(ContextState cs, ContextState csed) {
		boolean result = false;
		if (cs.getType().equals(csed.getType())) {
			if (cs.getType().equals("IndividualPropertyAtom")) {
				String property = cs.getPredicate();
				String propertyed = csed.getPredicate();
				String argu1 = cs.getArgu1();
				Individual arg1i = dm.getIndividual(domainURI + argu1);
				OntClass oc1 = arg1i.getOntClass();
				String argu1ed = csed.getArgu1();
				Individual arg1edi = dm.getIndividual(domainURI + argu1ed);
				OntClass oc1ed = arg1edi.getOntClass();
				String argu2 = cs.getArgu2();
				Individual arg2i = dm.getIndividual(domainURI + argu2);
				OntClass oc2 = arg2i.getOntClass();
				String argu2ed = csed.getArgu2();
				Individual arg2edi = dm.getIndividual(domainURI + argu2ed);
				OntClass oc2ed = arg2edi.getOntClass();
				if (property.matches(propertyed) && compareOntClasses(oc2, oc2ed) && isSuperClass(oc1, oc1ed)) {
					result = true;
				} else if (property.matches(propertyed) && compareOntClasses(oc1, oc1ed) && isSuperClass(oc2, oc2ed)) {
					result = true;
				}
			} else if (cs.getType().equals("ClassAtom")) {
				String predicate = cs.getClassPredicate();
				String predicated = csed.getClassPredicate();
				String argu = cs.getIArgu();
				Individual argi = dm.getIndividual(domainURI + argu);
				OntClass oc1 = argi.getOntClass();
				String argued = csed.getIArgu();
				Individual argied = dm.getIndividual(domainURI + argued);
				OntClass oc1ed = argied.getOntClass();
				if (predicate.matches(predicated) && isSuperClass(oc1, oc1ed)) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * Compare two OntClass equals based on the parameters,return boolean type.
	 * 
	 * @param oc1
	 * @param oc2
	 * @return
	 */
	static boolean compareOntClasses(OntClass oc1, OntClass oc2) {
		boolean r = false;
		if (oc1.hasEquivalentClass(oc2) || oc1.getLocalName().matches(oc2.getLocalName())) {
			r = true;
		}
		return r;
	}

	/**
	 * Judge whether parameter2(oc2) is a parent class of parameter1(oc1)(allow
	 * 3 layers),return boolean type.
	 * 
	 * @param oc1
	 * @param oc2
	 * @return
	 */
	static boolean isSuperClass(OntClass oc1, OntClass oc2) {
		boolean r = false;
		ExtendedIterator spc1 = oc1.listSuperClasses(true);
		while (spc1.hasNext()) {
			OntClass ocs = (OntClass) spc1.next();
			String ocsn = ocs.getLocalName();
			if (ocsn != null) {
				if (oc2.getLocalName().equals(ocsn)) {
					r = true;
				} else {
					ExtendedIterator spc2 = ocs.listSuperClasses(true);
					while (spc2.hasNext()) {
						OntClass ocs1 = (OntClass) spc2.next();
						String ocsn1 = ocs1.getLocalName();
						if (ocsn1 != null) {
							if (oc2.getLocalName().equals(ocsn1)) {
								r = true;
							} else {
								ExtendedIterator spc3 = ocs1.listSuperClasses(true);
								while (spc3.hasNext()) {
									OntClass ocs2 = (OntClass) spc3.next();
									String ocsn2 = ocs2.getLocalName();
									if (ocsn2 != null) {
										if (oc2.getLocalName().equals(ocsn2)) {
											r = true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return r;
	}

}
