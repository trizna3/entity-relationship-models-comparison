package sk.trizna.erm_comparison.entity_relationship_model;

import sk.trizna.erm_comparison.transformations.Transformable;

public class ERModelElement extends Transformable {
	
	private Boolean processed = null;

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
}
