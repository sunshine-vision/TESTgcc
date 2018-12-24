package org.wit.rpt.testdrawtool;

import java.io.File;

/**
 * File for drawing with GraphViz, please do not modify this file.
 * 
 * @author Li Shuang
 */
public class Proba {

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Proba p = new Proba();
		p.start();
	}

	/**
	 * This method is used to test the drawing.
	 */
	private void start() {
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		gv.addln("rankdir = LR");
		gv.addln("encoding=\"UTF-8\"");
		gv.addln("size='8,35';");
		gv.addln("compound=true;");
		gv.addln("rank=same;");

		gv.addln("subgraph cluster1{");
		gv.addln("label='State           Action';");
		gv.addln("size='8,8';");
		gv.addln("s1;s2;s3;s4;");
		gv.addln("a1[shape = invtriange,color='blue'];");
		gv.addln("a2[shape = invtriange,color='blue'];");
		gv.addln("a3[shape = invtriange,color='blue'];");
		gv.addln("};");
		gv.addln("s1->a1;");
		gv.addln("s2->a1;s2->a3;s3->a1;s4->a2;s4->a3;");

		gv.addln("subgraph cluster2{");
		gv.addln("label='State           Action';");
		gv.addln("size='8,8';");
		gv.addln("a4[shape = invtriange,color='blue'];");
		gv.addln("s5;");
		gv.addln("s6;");
		gv.addln("s7;s8;s9;s10;");
		gv.addln("};");
		gv.addln("a1->s7;a1->s8;a2->s9;a3->s9;");
		gv.addln("s8->a4;s9->a4;s10->a4;");

		gv.addln("subgraph cluster3{");
		gv.addln("label='State           Action';");
		gv.addln("size='8,8';");
		gv.addln("s11;s12;s13;s14;");
		gv.addln("a4->s15;");
		gv.addln("s15->a5;");
		gv.addln("s16;s17;");
		gv.addln("a5[shape = invtriange,color='blue']");
		gv.addln("s17->a5;");
		gv.addln(" };");

		gv.addln("subgraph cluster4{");
		gv.addln("label='State';");
		gv.addln("size='8,8';");
		gv.addln("s18;");
		gv.addln("s19;s20;s21;");
		gv.addln("a5->s22;a5->s23;");
		gv.addln("s24;");
		gv.addln("s25;s26");
		gv.addln("};");
		gv.addln(gv.end_graph());
		System.out.println(gv.getDotSource());

		/*
		 * The image format has the following options:
		 * "gif"��"dot"��"fig"��"pdf"��"ps"��"svg"��"png"��"plain";
		 */
		String type = "gif";
		// Image path and file name.
		File out = new File("D:/GH-AGVsModel/temp/out." + type);
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}

	/**
	 * Method of drawing a graph.[Draw a graph of only Capability,distinguish
	 * file name]
	 */
	public void start2Ca() {
		String input = "D:/GH-AGVsModel/ca_testfile.gv";
		GraphViz gv = new GraphViz();
		gv.readSource(input);
		System.out.println(gv.getDotSource());

		/*
		 * The image format has the following options:
		 * "gif"��"dot"��"fig"��"pdf"��"ps"��"svg"��"png"��"plain";
		 */
		String type = "gif";
		// Image path and file name.
		File out = new File("D:/GH-AGVsModel/ca_out." + type);
		if (out.exists()) {
			out.delete();
		}
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}

	/**
	 * Method of drawing a graph.[Draw a graph of Capabilities and
	 * Commitments,distinguish file name]
	 */
	public void start2CaCo() {
		String input = "D:/GH-AGVsModel/co_testfile.gv";
		GraphViz gv = new GraphViz();
		gv.readSource(input);
		System.out.println(gv.getDotSource());

		/*
		 * The image format has the following options:
		 * "gif"��"dot"��"fig"��"pdf"��"ps"��"svg"��"png"��"plain";
		 */
		String type = "gif";
		// Image path and file name.
		File out = new File("D:/GH-AGVsModel/co_out." + type);
		if (out.exists()) {
			out.delete();
		}
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}
	
	/**
	 * Method of drawing a graph.[Draw a graph of Capabilities and
	 * Commitments,distinguish file name]
	 */
	public void start2CaCoSim() {
		String input = "D:/GH-AGVsModel/co_sim_testfile.gv";
		GraphViz gv = new GraphViz();
		gv.readSource(input);
		System.out.println(gv.getDotSource());

		/*
		 * The image format has the following options:
		 * "gif"��"dot"��"fig"��"pdf"��"ps"��"svg"��"png"��"plain";
		 */
		String type = "gif";
		// Image path and file name.
		File out = new File("D:/GH-AGVsModel/co_sim_out." + type);
		if (out.exists()) {
			out.delete();
		}
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}

}
