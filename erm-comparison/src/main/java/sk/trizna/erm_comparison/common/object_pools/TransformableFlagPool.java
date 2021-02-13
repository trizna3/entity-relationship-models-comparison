package sk.trizna.erm_comparison.common.object_pools;

import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;

public class TransformableFlagPool extends AbstractObjectPool<TransformableFlag> {

	private static TransformableFlagPool INSTANCE;	
	
	public static TransformableFlagPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TransformableFlagPool(); 
		}
		return INSTANCE;
	}
	
	@Override
	protected TransformableFlag createObjectInternal() {
		return new TransformableFlag();
	}

	@Override
	protected void freeObjectInternal(TransformableFlag instance) {
	}
}
