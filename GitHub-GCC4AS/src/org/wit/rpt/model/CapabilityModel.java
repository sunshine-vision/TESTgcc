package org.wit.rpt.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * GCC model file, please do not modify this file.
 * 
 * @author Liu Wei
 */
public class CapabilityModel {
	public static String ccURI = "http://www.owl-ontologies.com/Ontology1512699842.owl#";
	public static String csURI = "http://www.owl-ontologies.com/Ontology1535514050.owl#";
	public static String swrlURI = "http://www.w3.org/2017/11/swrl#";
	public static String domainURI = "http://www.owl-ontologies.com/AGVs.owl#";
	public static String CapabilityURI = ccURI + "Capability";
	public static String CommitmentURI = ccURI + "Commitment";
	static String fpcc = "file:D://GCCModel//CCmodel.owl";
	static String fpcs = "file:D://GCCModel//contextstates1.owl";
	static String fpd = "file:D://GCCModel//Domain.owl";
	static String fpg = "D:\\GCCModel\\goalmodel.owl";
	static OntModel ccm;
	static OntModel dm;
	static String fp1;

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String fp1 = "D:\\GCCModel\\CCmodel1.owl";
		CapabilityModel cmi = new CapabilityModel(fpcc);
		OntModel cm = cmi.getCCModel();
		ContextStateModel csmi = new ContextStateModel (fpcs);
		OntModel csm = csmi.getCSModel();
		GoalModel gmi = new GoalModel();
		Individual csi= csm.getIndividual(csURI+"cs1");
		Goal g = new Goal(gmi, "transport_cart_with_elevator");
		ArrayList<Commitment> ca = new ArrayList<Commitment>();
		ca = genterateCommitments(g, cmi, fp1);
		System.out.println(ca);
		System.out.println("ok");
	}

	/**
	 * This is a constructor.
	 * @param fpcc
	 */
	public CapabilityModel(String fpcc) {
		ccm = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		ccm.read(fpcc);
	}

	/**
	 * Get the OntModel of the Capability.
	 * @return
	 */
	public OntModel getCCModel() {
		return ccm;
	}

	/**
	 * Save Capacity file based on parameter, unsuccessful thrown exception.
	 * @param fp1
	 * @throws IOException
	 */
	public static void saveCCModel(String fp1) throws IOException {
		File f = new File(fp1);
		FileOutputStream file = new FileOutputStream(f);
		ccm.write(file, "RDF/XML-ABBREV");
		file.close();
	}
	
	/**
	 * Based on the Goal in the parameters, traversal capabilities to generate commitments, return Commitment set.
	 * @param g
	 * @param cmi
	 * @param fpscc
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static ArrayList<Commitment> genterateCommitments(Goal g, CapabilityModel cmi, String fpscc) {
		fp1 = fpscc;
		OntModel m = cmi.getCCModel();
		ArrayList<Commitment> newcos = new ArrayList<Commitment>();
		Capability c1 = null;
		Capability c2 = null;
		ArrayList<ContextState> tcoc1 = null;
		ArrayList<ContextState> cic12 = null;
		ArrayList<ContextState> coc12 = null;
		ArrayList<ContextState> gtc = g.getTrigConditions();
		ArrayList<ContextState> gfs = g.getFinStates();
		OntClass cap1 = m.getOntClass(cmi.CapabilityURI);
		OntClass cap2 = m.getOntClass(cmi.CapabilityURI);
		for (ExtendedIterator cl1 = cap1.listInstances(); cl1.hasNext();) {
			Individual i = (Individual) cl1.next();
			String c1n = i.getLocalName();
			c1 = new Capability(cmi, c1n);
			ArrayList<ContextState> cic1 = c1.getInConstraints();
			ArrayList<ContextState> coc1 = c1.getOutConstraints();
			for (ExtendedIterator cl2 = cap2.listInstances(); cl2.hasNext();) {
				Individual j = (Individual) cl2.next();
				String c2n = j.getLocalName();
				if (!c1n.equalsIgnoreCase(c2n)) {
					c2 = new Capability(cmi, c2n);
					ArrayList<ContextState> cic2 = c2.getInConstraints();
					ArrayList<ContextState> coc2 = c2.getOutConstraints();
					// InCtsi and InCtsj
					cic12 = new ArrayList<ContextState>();
					cic12.addAll(cic1);
					cic12.addAll(cic2);
					// OutCtsi and OutCtsj
					coc12 = new ArrayList<ContextState>();
					coc12.addAll(coc1);
					coc12.addAll(coc2);
					// TrigCd and OutCtsi
					tcoc1 = new ArrayList<ContextState>();
					tcoc1.addAll(gtc);
					tcoc1.addAll(coc1);
					// (1)TrigCd->InCtsi and InCtsj, OutCtsi and OutCtsj->FinSt
					if (ContextStateModel.semanticMatch(cic12, gtc) && ContextStateModel.semanticMatch(gfs, coc12)) {
						// 注意命名中间的连接符,如果使用其他符号可能导致getLocalName()的方法只取到符号后面的字符串
						String coname = c1.getName() + "_" + c2.getName();
						Commitment c = new Commitment(CapabilityModel.ccm, coname);
						writeCommitmentIndintoModel(coname, c1, c2, cic12, coc2);
						newcos.add(c);
						// (2)TrigCd->InCtsi, TrigCd and OutCtsi->InCtsj,
						// OutCtsi and OutCtsj->FinSt
					} else if (ContextStateModel.semanticMatch(cic1, gtc)
							&& ContextStateModel.semanticMatch(cic2, tcoc1)
							&& ContextStateModel.semanticMatch(gfs, coc12)) {
						String coname = c1.getName() + "_" + c2.getName();
						Commitment c = new Commitment(CapabilityModel.ccm, coname);
						writeCommitmentIndintoModel(coname, c1, c2, cic1, coc2);
						newcos.add(c);
					}
				}
			}

		}
		return newcos;
	}

	/**
	 * Write the Commitment into the model file based on the parameters.
	 * @param coname
	 * @param creditor
	 * @param debtor
	 * @param antecedent
	 * @param consequent
	 */
	@SuppressWarnings("unused")
	public static void writeCommitmentIndintoModel(String coname, Capability creditor, Capability debtor,
			ArrayList<ContextState> antecedent, ArrayList<ContextState> consequent) {
		OntClass oc = ccm.getOntClass(CommitmentURI);
    	Individual csi = oc.createIndividual(ccURI+coname);
		Commitment co = new Commitment(ccm, coname);
		co.setCreditor(creditor);
		co.setDebtor(debtor);
		co.setAntecedent(antecedent);
		co.setConsequent(consequent);
		co.setLabel("co");
		try {
			saveCCModel(fp1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
