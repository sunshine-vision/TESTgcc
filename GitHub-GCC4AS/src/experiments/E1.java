package experiments;
import org.wit.rpt.model.CapabilityModel;
import org.wit.rpt.model.GoalModel;

/**
 * Test Capability and Goal matching.
 * 
 * @author Liu Wei
 */
public class E1 {
	static String fpcc = "file:D://GCCModel//CCmodel40.owl";
	static String fpscc = "D:\\GCCModel\\CCmodel401.owl";
	static String fpcc1 = "file:D://GCCModel//CCmodel401.owl";
	static String fpcs = "file:D://GCCModel//contextstates1.owl";
	static String fpd = "file:D://GCCModel//Domain.owl";
	static String fpg = "file:D://GCCModel//goalmodel.owl";
	static String fpg18 = "file:D://GCCModel//goalmodel18.owl";
	static String fpg36 = "file:D://GCCModel//goalmodel36.owl";
	public static String csURI = "http://www.owl-ontologies.com/Ontology1535514050.owl#";
	public static String domainURI = "http://www.owl-ontologies.com/AGVs.owl#";

	/**
	 * Test this Class.
	 * @param args
	 */
	public static void main(String[] args) {
//		matchingbyWOCC();
		matchingbyWCC();
//		matchingbyWCC1();
	}

	/**
	 * Match all Capabilities to all Goals.[Don't join the Commitment]
	 */
	static void matchingbyWOCC() {
		GoalModel gmi = new GoalModel(fpg);
		CapabilityModel cmi = new CapabilityModel(fpcc);
		GoalModel.currentMatchingbyWOCC(gmi, cmi);
	}

	/**
	 * Match all Capabilities to all Goals.[Join the Commitments of online
	 * generation]
	 */
	static void matchingbyWCC() {
		GoalModel gmi = new GoalModel(fpg);
		CapabilityModel cmi = new CapabilityModel(fpcc);
		// Here "fpscc" is the file save format.
		GoalModel.currentMatchingbyWCC(gmi, cmi, fpscc);
	}

	/**
	 * Match all Capabilities to all Goals.[Join the Commitments of
	 * pre-generated]
	 */
	static void matchingbyWCC1() {
		GoalModel gmi = new GoalModel(fpg);
		// Here "fpscc1" is the file invocation format.
		CapabilityModel cmi1 = new CapabilityModel(fpcc1);
		GoalModel.currentMatchingbyWCC1(gmi, cmi1);
	}
}
