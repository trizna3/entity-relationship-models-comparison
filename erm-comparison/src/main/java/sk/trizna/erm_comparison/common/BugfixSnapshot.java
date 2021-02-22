package sk.trizna.erm_comparison.common;

import sk.trizna.erm_comparison.entity_relationship_model.ERModel;

/**
 * Class encapsulates several objects, representing program state snapshot. Serves for validation purposes only.
 * 
 * @author Adam Trizna
 *
 */
public class BugfixSnapshot {
	
	String nestingId;
	ERModel exemplarModelClone;
	ERModel studentModelClone;
	
	public BugfixSnapshot(String nestingId, ERModel exemplarModelClone, ERModel studentModelClone) {
		super();
		this.nestingId = nestingId;
		this.exemplarModelClone = exemplarModelClone;
		this.studentModelClone = studentModelClone;
	}

	public String getNestingId() {
		return nestingId;
	}

	public ERModel getExemplarModelClone() {
		return exemplarModelClone;
	}

	public ERModel getStudentModelClone() {
		return studentModelClone;
	}
}
