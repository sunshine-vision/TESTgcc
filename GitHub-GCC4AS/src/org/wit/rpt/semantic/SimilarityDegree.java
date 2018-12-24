package org.wit.rpt.semantic;

import java.util.Iterator;
import org.wit.rpt.model.Capability;
import org.wit.rpt.model.CapabilityModel;
import org.wit.rpt.model.Commitment;
import org.wit.rpt.model.ContextState;
import org.wit.rpt.model.Goal;
import org.wit.rpt.model.GoalModel;
import com.hp.hpl.jena.ontology.OntModel;

/**
 * Calculate the degree of similarity between Goal-Capability and
 * Goal-Commitment
 * 
 * @author Guo Jingzhi
 */
public class SimilarityDegree {
	static String fpcc = "file:D://GCCModel//CCmodel.owl";
	static String fpcc1 = "file:D://GCCModel//CCmodel1.owl";	
	static GoalModel gm = new GoalModel();
	static CapabilityModel cm = new CapabilityModel(fpcc);
	static OntModel com;

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Case:Goal-Capability
		String goalName = "select_nearest_AGV";
		String capabilityName = "select_nearest_agv";
		// Case:Goal-Commitment
		String goalNameCo = "get_cart_destination3";
		String commitmentName = "read_cart_tag_detect_cart";
		String commitmentName1 = "detect_cart_read_cart_ta";
		Goal g = new Goal(gm, goalName);
		Capability cap = new Capability(cm, capabilityName);
		CapabilityModel cm = new CapabilityModel(fpcc1);
		Commitment co = new Commitment(cm.getCCModel(), commitmentName);
		Commitment co1 = new Commitment(cm.getCCModel(), commitmentName1);
		Goal gco = new Goal(gm, goalNameCo);

		// Calculate the degree of similarity between Goal-Capability.
		double simca = getSimilarityDegree(g, cap);
		// Calculate the degree of similarity between Goal-Commitment.
		double simco = getSimilarityDegree(gco, co);
		double simco1 = getSimilarityDegree(gco, co1);
		System.out.println(simca);
		System.out.println(simco);
		System.out.println(simco1);
	}

	/**
	 * Calculate the degree of similarity between Goal-Capability.
	 * 
	 * @param g
	 * @param cap
	 * @return
	 */
	public static double getSimilarityDegree(Goal g, Capability cap) {
		double sim = 0.0;
		double sim1 = getInCTrigCSimilarity(cap, g);
//		System.out.println("这是sim1:"+sim1);
		double sim2 = getFinSOutCSimilarity(g, cap);
//		System.out.println("这是sim2:"+sim2);
		int a = cap.getInConstraintsNumber();
		int b = g.getFinStatesNumber();
		sim = (sim1 + sim2) / (a + b);
		return sim;
	}

	/**
	 * Calculate the value of sim1. [Goal-Capability]
	 * 
	 * @param cap
	 * @param g
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static double getInCTrigCSimilarity(Capability cap, Goal g) {
		double sim = 0.0;
		for (Iterator p = cap.getInConstraints().listIterator(); p.hasNext();) {
			ContextState s1 = (ContextState) p.next();
			for (Iterator p1 = g.getTrigConditions().listIterator(); p1.hasNext();) {
				ContextState s2 = (ContextState) p1.next();
				sim += getContextStateSimilarity(s1, s2);
			}
		}
		return sim;
	}

	/**
	 * Calculate the value of sim2. [Goal-Capability]
	 * 
	 * @param g
	 * @param cap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static double getFinSOutCSimilarity(Goal g, Capability cap) {
		double sim = 0.0;
		for (Iterator p = g.getFinStates().listIterator(); p.hasNext();) {
			ContextState s1 = (ContextState) p.next();
			for (Iterator p1 = cap.getOutConstraints().listIterator(); p1.hasNext();) {
				ContextState s2 = (ContextState) p1.next();
				sim += getContextStateSimilarity(s1, s2);
			}
		}
		return sim;
	}

	/**
	 * Calculate the degree of similarity between Goal-Commitment.
	 * 
	 * @param g
	 * @param co
	 * @return
	 */
	public static double getSimilarityDegree(Goal g, Commitment co) {
		double sim = 0.0;
		double sim1 = getAntTrigCSimilarity(co, g);
		double sim2 = getFinSConseSimilarity(g, co);
		int m = co.getAntecedentNumber();
		int n = g.getFinStatesNumber();
		sim = (sim1 + sim2) / (m + n);// 目标和能力的相似度计算公式
		return sim;
	}

	/**
	 * Calculate the value of sim1. [Goal-Commitment]
	 * 
	 * @param co
	 * @param g
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static double getAntTrigCSimilarity(Commitment co, Goal g) {
		Commitment comm = new Commitment(cm.getCCModel(), co.getName());
		Goal goal = new Goal(gm, g.getName());
		double sim = 0.0;
		for (Iterator p = comm.getAntecedent().listIterator(); p.hasNext();) {
			ContextState s1 = (ContextState) p.next();
			for (Iterator p1 = goal.getTrigConditions().listIterator(); p1.hasNext();) {
				ContextState s2 = (ContextState) p1.next();
				// System.out.println("AT: " + s1.getName() + " 和 " +
				// s2.getName() + " 的相似度为 ");
				sim += getContextStateSimilarity(s1, s2);
			}
		}
		return sim;
	}

	/**
	 * Calculate the value of sim2.[Goal-Commitment]
	 * 
	 * @param g
	 * @param co
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static double getFinSConseSimilarity(Goal g, Commitment co) {
		Commitment comm = new Commitment(cm.getCCModel(), co.getName());
		GoalModel gm = new GoalModel();
		Goal goal = new Goal(gm, g.getName());
		double sim = 0.0;
		for (Iterator p = goal.getFinStates().listIterator(); p.hasNext();) {
			ContextState s1 = (ContextState) p.next();
			for (Iterator p1 = comm.getConsequent().listIterator(); p1.hasNext();) {
				ContextState s2 = (ContextState) p1.next();
//				String sname2 = (String) p1.next();
//				ContextStateModel csmi = new ContextStateModel(fpcs);
//				ContextState s2 = new ContextState(csmi, sname2);
				sim += getContextStateSimilarity(s1, s2);
			}
		}
		return sim;
	}

	/**
	 * Calculate the similarity of two ContextStates.
	 * 
	 * @param cs1
	 * @param cs2
	 * @return
	 */
	public static double getContextStateSimilarity(ContextState cs1, ContextState cs2) {
		double n = 0.0;
		String cs1Type = cs1.getType();
		String cs2Type = cs2.getType();
		if (cs1Type.equals(cs2Type) && cs1Type.equals("ClassAtom")) {
			String argu11 = cs1.getIArgu();
			String argu12 = cs2.getIArgu();
			String pred1 = cs1.getClassPredicate();
			String pred2 = cs2.getClassPredicate();
			if (pred1.equals(pred2)) {
				double sim;
				sim = RNCVMSimilarity.getRNCVMSimilarity(argu11, argu12);
				if (sim == 1) {
					n = 1.0;
				} else {
					n = sim;
				}
			} else {
				n = 0.0;
			}
		}
		else if (cs1Type.equalsIgnoreCase(cs2Type) && cs1Type.equalsIgnoreCase("IndividualPropertyAtom")) {
			String argu11 = cs1.getArgu1();
			String argu21 = cs2.getArgu1();
			String pred1 = cs1.getPredicate();
			String pred2 = cs2.getPredicate();
			String argu12 = cs1.getArgu2();
			String argu22 = cs2.getArgu2();
			if (pred1.equals(pred2)) {
				double sim1, sim2;
				sim1 = RNCVMSimilarity.getRNCVMSimilarity(argu11, argu21);
				sim2 = RNCVMSimilarity.getRNCVMSimilarity(argu12, argu22);
				if (sim1 == 1 && sim2 == 1) {
					n = 1.0;
				} else if (sim1 == 1 && sim2 != 1) {
					n = sim2;
				} else if (sim2 == 1 && sim1 != 1) {
					n = sim1;
				} else {
					n = 0.0;
				}
			} else {
				n = 0.0;
			}
		}
		else {
			n = 0.0;
		}
		return n;
	}

}
