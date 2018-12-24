package org.wit.rpt.model;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * GCC model file, please do not modify this file.
 * 
 * @author Liu Wei.
 */
public class Commitment {
	String fpcs = "file:D://GCCModel//contextstates1.owl";
	String fpcc = "file:D://GCCModel1//CCmodel.owl";
	String coname;
	OntModel ccm;
	ContextStateModel csmi;
	OntModel csm;
	CapabilityModel cm;
	private Individual coi;

	/**
	 * This is a constructor.
	 * 
	 * @param cmi
	 * @param name
	 */
	public Commitment(OntModel cmi, String name) {
		ccm = cmi;
		csmi = new ContextStateModel(fpcs);
		csm = csmi.getCSModel();
		coname = name;
		coi = ccm.getIndividual(CapabilityModel.ccURI + name);
	}

	/**
	 * Get the name of Commitment, return String type.
	 * 
	 * @return
	 */
	public String getName() {
		return coname;
	}

	/**
	 * Get the label of Commitment, return String type.
	 * 
	 * @return
	 */
	public String getLabel() {
		String label = new String();
		Property ahp = ccm.getProperty(CapabilityModel.ccURI + "hasClabel");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(coi, ahp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			String s = r.getLocalName();
			label = s;
		}
		return label;
	}

	/**
	 * Get the creditor of Commitment, return Capability set.
	 * 
	 * @return
	 * @update updated by Guo Jingzhi.
	 */
	public ArrayList<Capability> getCreditor() {
		cm = new CapabilityModel(fpcc);
		ArrayList<Capability> cred = new ArrayList<Capability>();
		Property ahc = ccm.getProperty(CapabilityModel.ccURI + "hasCreditor");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(coi, ahc); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			Capability cr = new Capability(cm, r.getLocalName());
			cred.add(cr);
		}
		return cred;
	}

	/**
	 * Get the debtor of Commitment, return Capability set.
	 * 
	 * @return
	 * @update updated by Guo Jingzhi.
	 */
	public ArrayList<Capability> getDebtor() {
		cm = new CapabilityModel(fpcc);
		ArrayList<Capability> debt = new ArrayList<Capability>();
		Property ahc = ccm.getProperty(CapabilityModel.ccURI + "hasDebtor");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(coi, ahc); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			Capability cr = new Capability(cm, r.getLocalName());
			debt.add(cr);
		}
		return debt;
	}

	/**
	 * Get the antecedent of Commitment, return ContextState set.
	 * 
	 * @return
	 */
	public ArrayList<ContextState> getAntecedent() {
		ArrayList<ContextState> antecedent = new ArrayList<ContextState>();
		Property ahp = ccm.getProperty(CapabilityModel.ccURI + "hasAntecedent");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(coi, ahp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csmi, r.getLocalName());
			antecedent.add(cs);
		}
		return antecedent;
	}

	/**
	 * Get the consequence of Commitment, return ContextState set.
	 * 
	 * @return
	 */
	public ArrayList<ContextState> getConsequent() {
		ArrayList<ContextState> consequent = new ArrayList<ContextState>();
		Property ahe = ccm.getProperty(CapabilityModel.ccURI + "hasConsequence");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(coi, ahe); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csmi, r.getLocalName());
			consequent.add(cs);
		}
		return consequent;
	}
	
	/**
	 * Set the label of Commitment based on the parameter.
	 * @param label
	 */
	public void setLabel(String label){
		Property hasClabel = ccm.getProperty(CapabilityModel.ccURI + "hasClabel");
		Individual c = ccm.getIndividual(CapabilityModel.ccURI + label);
		coi.addProperty(hasClabel,c);
	}

	/**
	 * Set the creditor of Commitment based on the parameter.
	 * 
	 * @param creditor
	 */
	public void setCreditor(Capability creditor) {
		ObjectProperty hasCreditor = ccm.getObjectProperty(CapabilityModel.ccURI + "hasCreditor");
		Individual c = ccm.getIndividual(CapabilityModel.ccURI + creditor.getName());
		coi.addProperty(hasCreditor, c);
	}

	/**
	 * Set the debtor of Commitment based on the parameter.
	 * 
	 * @param debtor
	 */
	public void setDebtor(Capability debtor) {
		ObjectProperty hasDebtor = ccm.getObjectProperty(CapabilityModel.ccURI + "hasDebtor");
		Individual d = ccm.getIndividual(CapabilityModel.ccURI + debtor.getName());
		coi.addProperty(hasDebtor, d);
	}

	/**
	 * Set the antecedent of Commitment based on the parameter.
	 * 
	 * @param ant
	 */
	public void setAntecedent(ArrayList<ContextState> ant) {
		ObjectProperty hasAntecedent = ccm.getObjectProperty(CapabilityModel.ccURI + "hasAntecedent");
		for (int i = 0; i < ant.size(); i++) {
			ContextState cs = ant.get(i);
			Individual csi = csm.getIndividual(CapabilityModel.csURI + cs.getName());
			coi.addProperty(hasAntecedent, csi);
		}

	}

	/**
	 * Set the consequence of Commitment based on the parameter.
	 * 
	 * @param con
	 */
	public void setConsequent(ArrayList<ContextState> con) {
		ObjectProperty hasConsequent = ccm.getObjectProperty(CapabilityModel.ccURI + "hasConsequence");
		for (int i = 0; i < con.size(); i++) {
			ContextState cs = con.get(i);
			Individual csj = csm.getIndividual(CapabilityModel.csURI + cs.getName());
			coi.addProperty(hasConsequent, csj);
		}
	}

	/**
	 * Get the number of antecedent for Commitment, return the number.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public int getAntecedentNumber() {
		int count = 0;
		Property ahp = ccm.getProperty(CapabilityModel.ccURI + "hasAntecedent");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(coi, ahp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
  			ContextState cs = new ContextState(csmi, r.getLocalName());
			count++;
		}
		return count;
	}

	// 判断状态和commitment的Antecedent是否完全一致---是否可以使用commitment
	/**
	 * Determine if there is commitment.
	 * 
	 * @param initialstates
	 * @param anList
	 * @return
	 */
	public int isCommitment(ArrayList<ContextState> initialstates, ArrayList<ContextState> anList) {
		int t = 0;
		for (int i = 0; i < initialstates.size(); i++) {
			if (t == anList.size()) {
				break;
			} else {
				String ininame = initialstates.get(i).getName();
				for (int j = 0; j < anList.size(); j++) {
					String anname = anList.get(j).getName();
					if (anname.equalsIgnoreCase(ininame)) {
						t = t + 1;
						if (t == anList.size()) {
							break;
						}
					}
				}
			}
		}
		return t;
	}
}
