package org.wit.rpt.semantic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import org.wit.rpt.model.ContextStateModel;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Calculate the Semantic Similarity between two concept vectors.
 * 
 * @author Guo Jingzhi
 */
public class RNCVMSimilarity {
	static OntModel m;
	static String fpcs = "file:D://GCCModel//contextstates1.owl";
	static String fpd = "file:D://GCCModel//Domain.owl";

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		double sim = 0.0;
		sim = getRNCVMSimilarity("pickupPosition", "cartSite");
		System.out.println(sim);
	}

	/**
	 * Calculate the Concept Vector Similarity between two concept vectors.
	 * [Regardless of the same layer at the concept]
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static double getRNCVMSimilarity(String c1, String c2) {
		ContextStateModel cm = new ContextStateModel(fpcs);
		m = cm.getDomainmodel(fpd);

		// Get the class in which the two concepts are located.
		Individual ind1 = m.getIndividual(ContextStateModel.domainURI + c1);
		OntClass s1 = ind1.getOntClass();
		Individual ind2 = m.getIndividual(ContextStateModel.domainURI + c2);
		OntClass s2 = ind2.getOntClass();

		Integer int1 = new Integer(0);
		Integer int2 = new Integer(1);
		OntClass root1 = getRoot(s1);
		OntClass root2 = getRoot(s2);

		Vector<Integer> v1 = new Vector<Integer>();
		Vector<Integer> v2 = new Vector<Integer>();

		// If: s1 and s2 have the same root class.
		if (root1.equals(root2)) {
			Collection<OntClass> c = new ArrayList<OntClass>();
			Collection<OntClass> sup = getSuperClasses(s1, c);
			// If: s1 is a subclass of s2, then swap.
			if (sup.contains(s2)) {
				OntClass t;
				t = s1;
				s1 = s2;
				s2 = t;
			}
			int n = getSubclassNumber(root1);
			int n1 = getSubclassNumber(s1);
			int n2 = getSubclassNumber(s2);
			/*
			 * If s1 and s2 are the same, or s1 and s2 are equivalence classes,
			 * the vector is [1], and the similarity is also 1.
			 */
			if (s1.equals(s2) || s1.hasEquivalentClass(s2)) {
				v1.add(int2);
				v2.add(int2);
			}
			// If: root is the parent class of s1, s1 is the parent class of s2.
			else if (s2.getSuperClass().equals(s1) && s1.getSuperClass().equals(root1)) {
				v1.add(int2);
				v1.add(n);
				for (int i = 1; i < n; i++) {
					v1.add(int1);
				}
				for (int j = 0; j < n1; j++) {
					v1.add(n1);
				}

				v2.add(int2);
				v2.add(n);
				for (int i = 1; i < n; i++) {
					v2.add(int1);
				}
				v2.add(n1);
				for (int j = 1; j < n1; j++) {
					v2.add(int1);
				}
			}
			// If:s1 is root and s2 is a subclass of s1.
			else if (s1.equals(getRoot(s1)) && s2.getSuperClass().equals(s1)) {
				v1.add(int2);
				for (int i = 0; i < n1; i++) {
					v1.add(n1);
				}
				for (int j1 = 0; j1 < n2; j1++) {
					v1.add(n2);
				}

				v2.add(int2);
				v2.add(n1);
				for (int i = 1; i < n1; i++) {
					v2.add(int1);
				}
				for (int j2 = 0; j2 < n2; j2++) {
					v2.add(n2);
				}
			}
			// If:s1 is root and s2 is a subclass of s1 subclass
			else if (s1.equals(getRoot(s1)) && s2.getSuperClass().getSuperClass().equals(s1)) {
				OntClass s = s2.getSuperClass();
				int n4 = getSubclassNumber(s);
				v1.add(int2);
				for (int i = 0; i < n1; i++) {
					v1.add(n1);
				}
				for (int j1 = 0; j1 < n4; j1++) {
					v1.add(n4);
				}

				v2.add(int2);
				v2.add(n1);
				for (int i = 1; i < n1; i++) {
					v2.add(int1);
				}
				v2.add(n4);
				for (int j2 = 1; j2 < n4; j2++) {
					v2.add(int1);
				}
			}
			return getSimilarity(v1, v2);
		} else {
			return 0.0;
		}
	}

	/**
	 * Get the number of subclasses of a class. [When two subclasses are
	 * equivalence classes, the number is 1]
	 * 
	 * @param oc
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getSubclassNumber(OntClass oc) {
		int count = 0;
		Collection<OntClass> c1 = new ArrayList<OntClass>();
		for (Iterator p = oc.listSubClasses(); p.hasNext();) {
			OntClass d = (OntClass) p.next();
			c1.add(d);
			count++;
		}
		return count;
	}

	/**
	 * Get the root class of a class.
	 * 
	 * @param oc
	 * @return
	 */
	public static OntClass getRoot(OntClass oc) {
		OntClass root = null;
		if (oc.getSuperClass().getLocalName().equals("DomainConcepts")) {
			root = (OntClass) oc;
			return root;
		} else {
			return getRoot(oc.getSuperClass());
		}

	}

	/**
	 * Get all the parent classes of a class.
	 * 
	 * @param oc
	 * @param l
	 * @return
	 */
	public static Collection<OntClass> getSuperClasses(OntClass oc, Collection<OntClass> l) {
		ExtendedIterator spc1 = oc.listSuperClasses();
		while (spc1.hasNext()) {
			OntClass ocs1 = (OntClass) spc1.next();
			if (!oc.getSuperClass().getLocalName().equals("DomainConcepts")) {
				l.add(ocs1);
				getSuperClasses(ocs1, l);
			}
		}
		return l;
	}

	/**
	 * Calculate the cosine of two vectors(Similarity value).
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static double getSimilarity(Vector v1, Vector v2) {
		double s1 = 0.0;
		double s2 = 0.0;
		double b1 = 0.0;
		double b2 = 0.0;
		for (int i = 0; i < v1.size(); i++) {
			double a1 = Double.parseDouble(v1.get(i).toString());
			double a2 = Double.parseDouble(v2.get(i).toString());
			s1 += a1 * a2;
		}
		for (int j = 0; j < v1.size(); j++) {
			b1 += Double.parseDouble(v1.get(j).toString()) * Double.parseDouble(v1.get(j).toString());
			b2 += Double.parseDouble(v2.get(j).toString()) * Double.parseDouble(v2.get(j).toString());
			s2 = Math.sqrt(b1) * Math.sqrt(b2);
		}
		return s1 / s2;
	}
}
