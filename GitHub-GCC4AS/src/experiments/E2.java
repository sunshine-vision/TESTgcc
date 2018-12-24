package experiments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.wit.rpt.goaltree.Node;
import org.wit.rpt.goaltree.TreeModel;
import org.wit.rpt.model.Capability;
import org.wit.rpt.model.CapabilityModel;
import org.wit.rpt.model.Commitment;
import org.wit.rpt.model.ContextStateModel;
import org.wit.rpt.model.Goal;
import org.wit.rpt.model.GoalModel;

/**
 * Test GoalTree.
 * 
 * @author Lie Wei
 */
public class E2 {
	static String fpcc = "file:D://GCCModel//CCmodel20.owl";
	static String fp1 = "D:\\GCCModel\\CCmodel201.owl";
	static String fpcc1 = "file:D://GCCModel//CCmodel201.owl";
	static String fpcs1 = "file:D://GCCModel//Currentcsmodel1.owl";
	static String fpcs2 = "file:D://GCCModel//Currentcsmodel2.owl";
	static String fpd = "file:D://GCCModel//Domain.owl";
	static String fpg = "file:D://GCCModel//goalmodel.owl";
	static String fpg16 = "file:D://GCCModel//goalmodel16.owl";
	static String fpg32 = "file:D://GCCModel//goalmodel32.owl";
	public static String csURI = "http://www.owl-ontologies.com/Ontology1535514050.owl#";
	public static String domainURI = "http://www.owl-ontologies.com/AGVs.owl#";

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		double gnum = 0;
		ContextStateModel csmi1 = new ContextStateModel(fpcs1);
		ContextStateModel csmi2 = new ContextStateModel(fpcs2);
		ContextStateModel csm = csmi2;
		GoalModel gm = new GoalModel(fpg);
		CapabilityModel cm = new CapabilityModel(fpcc);
		List<Node> suplt = new ArrayList<Node>();
		Node g0 = TreeModel.init();
		try {
			TreeModel.preOrder(suplt, g0, csmi1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Goal> sglist = new ArrayList<Goal>();
		String m_sentence = null;
		/*
		 * "csm" is assumed here to be a context state model in different
		 * environments.
		 */
		if (csm == csmi1) {
			m_sentence = "G6#G7;G8;G9;G10;G11;G12;G5";
		} else if (csm == csmi2) {
			m_sentence = "G6#G7;G8;G9;G10;G11;G13;G12;G5";
		}
		System.out.println();
		long start = System.currentTimeMillis();
		List<String> m_list = TreeModel.cutstring(m_sentence);
		for (String tmp : m_list) {
			Node n = TreeModel.getNode(tmp);
			System.out.println(n.gname);
			gnum = gnum + 1;
			Goal g = new Goal(gm, n.gname);
			Capability c = GoalModel.matchedByCapabilities1(g, cm);
			if (c != null) {
				m_sentence.replaceAll(tmp, c.getName());
				sglist.add(g);
			}
			// If "g" cannot be matched by capability, r is false.
			else {
				Commitment co = GoalModel.matchedByCommitments1(g, cm, fp1);
				if (co != null) {
					m_sentence.replaceAll(tmp, co.getName());
					sglist.add(g);
				} else {
					System.out.println("Goal: " + g.getName() + " can not be matched by any capability or commitment");
					System.out.println();
				}
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Time:" + (end - start) + "   (ms)");
		System.out.println(m_sentence);
		System.out.println((double) sglist.size() / gnum);
	}

}
