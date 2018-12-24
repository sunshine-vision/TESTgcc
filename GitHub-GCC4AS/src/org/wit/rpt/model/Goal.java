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
public class Goal {
	String fpcs = "file:D://GCCModel//contextstates1.owl";
	String name;
	private Individual gi;
	OntModel gom;
	ContextStateModel csm;
	String state = null;
	Capability mc = null;
	Commitment mco = null;

	/**
	 * This is a constructor.
	 * 
	 * @param gm
	 * @param sname
	 */
	@SuppressWarnings("static-access")
	public Goal(GoalModel gm, String sname) {
		gom = gm.getModel();
		csm = new ContextStateModel(fpcs);
		name = sname;
		gi = gom.getIndividual(GoalModel.gURI + name);
	}

	/**
	 * Get the name of Goal, return String type.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the state of Goal, return String type.
	 * 
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set the state of Goal.
	 * 
	 * @param s
	 */
	@SuppressWarnings("unused")
	public void setState(String s) {
		state = s;
		ObjectProperty hasState = gom.getObjectProperty(GoalModel.gURI + "hasState");
		Individual sn = gom.getIndividual(s);
	}

	public void setMatchedCapability(Capability mc) {
		this.mc = mc;
	}

	public void setMatchedCommitment(Commitment mco) {
		this.mco = mco;
	}

	public Capability getMatchedCapability() {
		return mc;
	}

	public Commitment getMatchedCommitment() {
		return mco;
	}

	/**
	 * Get the TriggerConditions of Goal, return ContextState set.
	 * 
	 * @return
	 */
	public ArrayList<ContextState> getTrigConditions() {
		ArrayList<ContextState> trigConditions = new ArrayList<ContextState>();
		Property tcp = gom.getProperty(GoalModel.gURI + "hasTriggerConditions");
		for (NodeIterator pcs = gom.listObjectsOfProperty(gi, tcp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csm, r.getLocalName());
			trigConditions.add(cs);
		}
		return trigConditions;
	}

	/**
	 * Get the FinalStates of Goal, return ContextState set.
	 * 
	 * @return
	 */
	public ArrayList<ContextState> getFinStates() {
		ArrayList<ContextState> OutConstraints = new ArrayList<ContextState>();
		Property fsp = gom.getProperty(GoalModel.gURI + "hasFinalStates");
		for (NodeIterator pcs = gom.listObjectsOfProperty(gi, fsp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csm, r.getLocalName());
			OutConstraints.add(cs);
		}
		return OutConstraints;
	}

	/**
	 * Get the number of FinalStates for Goal, return the number.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public int getFinStatesNumber() {
		int count = 0;
		Property fsp = gom.getProperty(GoalModel.gURI + "hasFinalStates");
		for (NodeIterator pcs = gom.listObjectsOfProperty(gi, fsp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			ContextState cs = new ContextState(csm, r.getLocalName());
			count++;
		}
		return count;
	}

}
