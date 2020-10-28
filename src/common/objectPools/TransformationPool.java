package common.objectPools;

import java.util.ArrayList;
import java.util.List;

import common.enums.EnumTransformation;
import transformations.Transformation;

public class TransformationPool {
	
	private static TransformationPool INSTANCE;
	
	private List<Transformation> freeTransformations;
	private List<Transformation> usedTransformations;
	private int INITIAL_CAPACITY = 20;
	
	public static TransformationPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TransformationPool(); 
		}
		return INSTANCE;
	}
	
	public Transformation getTransformation() {
		if (!freeTransformations.isEmpty()) {
			Transformation newTransformation = freeTransformations.remove(0);
			usedTransformations.add(newTransformation);
			return newTransformation;
		}
		
		Transformation newTransformation = new Transformation(EnumTransformation.EMPTY);
		usedTransformations.add(newTransformation);
		return newTransformation;
	}
	
	public void freeTransformation(Transformation transformation) {
		transformation.setCode(null);
		transformation.setProcessed(false);
		transformation.getArgumentMap().clear();
		
		usedTransformations.remove(transformation);
		freeTransformations.add(transformation);
	}
	
	private TransformationPool() {
		freeTransformations = new ArrayList<Transformation>(INITIAL_CAPACITY);
		usedTransformations = new ArrayList<Transformation>(INITIAL_CAPACITY);
	}
}