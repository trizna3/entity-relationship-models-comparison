package sk.trizna.erm_comparison.entity_relationship_model;

import sk.trizna.erm_comparison.transformations.Transformable;

public class ERModelElement extends Transformable {
	
	private ERModelElementName name;
	private Boolean processed = null;

	public ERModelElement(String name) {
		setNameText(name);
	}
	
	/**
	 * Either is processed, or we don't care about this object processing (if processed == null)
	 * @return
	 */
	public boolean isProcessed() {
		return processed == null || processed.booleanValue();
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	
	public String getNameText() {
		return name != null ? name.getName() : null;
	}
	
	public ERModelElementName getName() {
		return name;
	}

	public void setNameText(String name) {
		this.name = new ERModelElementName(name);
	}
	
	public void setName(ERModelElementName name) {
		this.name = name;
	}
}
