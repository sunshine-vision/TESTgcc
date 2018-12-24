package org.wit.rpt.model;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * GCC model file, please do not modify this file.
 * 
 * @author Liu Wei
 */

public class Capability {
	String name;
	OntModel ccm;
	String fpcs = "file:D://GCCModel//contextstates1.owl";
	ContextStateModel csm = new ContextStateModel(fpcs);
	private Individual csi;

	/**
	 * Test this Class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String fpcc = "file:D://GCCModel//CCmodel.owl";
		CapabilityModel cm = new CapabilityModel(fpcc);
		Capability c1 = new Capability(cm, "deliver_agv_call");
		ArrayList<ContextState> ic = c1.getInConstraints();
		System.out.println("InConstraints of " + c1.getName() + " is:");
		for (int i = 0; i < ic.size(); i++) {
			ContextState cs = ic.get(i);
			System.out.println(cs.getName());
		}
		ArrayList<ContextState> oc = c1.getOutConstraints();
		System.out.println("OutConstraints of " + c1.getName() + " is:");
		for (int i = 0; i < oc.size(); i++) {
			ContextState cs = oc.get(i);
			System.out.println(cs.getName());
		}
	}

	/**
	 * This is a constructor.
	 * 
	 * @param cmi
	 * @param sname
	 */
	public Capability(CapabilityModel cmi, String sname) {
		ccm = cmi.getCCModel();
		name = sname;
		csi = ccm.getIndividual(CapabilityModel.ccURI + name);
	}

	/**
	 * Get the name of Capability, return String type.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the actor of Capability, return OntResource type.
	 * 
	 * @return
	 */
	public OntResource getAgents() {
		Property hasActors = ccm.getProperty(CapabilityModel.ccURI + "hasActors");
		NodeIterator ni = csi.listPropertyValues(hasActors);
		OntResource agent = null;
		while (ni.hasNext()) {
			agent = (OntResource) ni.next();
		}
		return agent;
	}

	/**
	 * Get the label of Capability, return String type.
	 * @return
	 */
	public String getLabel() {
		String label = new String();
		Property ahp = ccm.getProperty(CapabilityModel.ccURI + "hasClabel");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(csi, ahp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			String s = r.getLocalName();
			label = s;
		}
		return label;
	}

	/**
	 * Get the input constraints of Capability, return ContextState set.
	 * 
	 * @return
	 */
	public ArrayList<ContextState> getInConstraints() {
		ArrayList<ContextState> inConstraints = new ArrayList<ContextState>();
		Property ahp = ccm.getProperty(CapabilityModel.ccURI + "hasInConstraints");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(csi, ahp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csm, r.getLocalName());
			inConstraints.add(cs);
		}
		return inConstraints;
	}

	/**
	 * Get the output constraints of Capability, return ContextState set.
	 * 
	 * @return
	 */
	public ArrayList<ContextState> getOutConstraints() {
		ArrayList<ContextState> outConstraints = new ArrayList<ContextState>();
		Property ahe = ccm.getProperty(CapabilityModel.ccURI + "hasOutConstraints");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(csi, ahe); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csm, r.getLocalName());
			outConstraints.add(cs);
		}
		return outConstraints;
	}

	/**
	 * Get the number of input constraints for Capability, return the number.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public int getInConstraintsNumber() {
		int count = 0;
		Property ahp = ccm.getProperty(CapabilityModel.ccURI + "hasInConstraints");
		for (NodeIterator pcs = ccm.listObjectsOfProperty(csi, ahp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csm, r.getLocalName());
			count++;
		}
		return count;
	}

	/**
	 * Rewrite the equals method,return boolean type.
	 * 
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capability) {
			Capability t = (Capability) obj;
			return this.name.equals(t.name);
		}
		return super.equals(obj);
	}

}
