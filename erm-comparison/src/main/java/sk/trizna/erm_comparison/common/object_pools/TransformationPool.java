package sk.trizna.erm_comparison.common.object_pools;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.utils.TransformationUtils;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.transformations.Transformation;

public class TransformationPool extends AbstractObjectPool<Transformation> {
	
	private static TransformationPool INSTANCE;	
	
	public static TransformationPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TransformationPool(); 
		}
		return INSTANCE;
	}
	
	@Override
	protected Transformation createObjectInternal() {
		Transformation transformation = new Transformation();
		transformation.setCode(EnumTransformation.EMPTY);
		return transformation;
	}

	@Override
	protected void freeObjectInternal(Transformation instance) {
		freeExemplarFlag(instance);
		freePreconditions(instance);
		instance.setCode(null);
		instance.setProcessed(false);
		instance.getArgumentMap().clear();
		instance.getPreconditions().clear();
	}
	
	/**
	 * If transformation contains exemplarModelFlag, return it to it's Object Pool.
	 * @param instance
	 */
	private void freeExemplarFlag(Transformation instance) {
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(instance, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		
		if (flag != null) {
			TransformableFlagPool.getInstance().freeObject(flag);
		}
	}
	
	private void freePreconditions(Transformation instance) {
		instance.getPreconditions().forEach(precondition -> {
			freeObject(precondition);
		});
	} 
}