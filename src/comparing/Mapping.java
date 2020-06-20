package comparing;

import java.util.ArrayList;
import java.util.List;

import common.Utils;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;

/**
 * @author - Adam Trizna
 */

/**
 * Object representing mapping of entity sets between two entity relationship
 * model.
 */
public class Mapping {

	/**
	 * Exemplar ER model mapping is created upon.
	 */
	private ERModel model1;

	/**
	 * Students ER model mapping is created upon.
	 */
	private ERModel model2;

	private List<String> transformationCodes;

	public Mapping(ERModel model1, ERModel model2) {
		this.model1 = model1;
		this.model2 = model2;
		transformationCodes = new ArrayList<>();
	}

	/**
	 * Map given entitySets together. Convention: ES1 is from exemplar model, ES2 is
	 * from students model.
	 * 
	 * @param entitySet1
	 * @param entitySet2
	 */
	public void map(EntitySet entitySet1, EntitySet entitySet2) {
		Utils.validateNotNull(entitySet1);
		Utils.validateNotNull(entitySet2);

		Utils.validateContains(model1, entitySet1);
		Utils.validateContains(model2, entitySet2);

		Utils.validateNotMapped(entitySet1);
		Utils.validateNotMapped(entitySet2);

		entitySet1.setMappedTo(entitySet2);
		entitySet2.setMappedTo(entitySet1);
	}

	/**
	 * Unmap entitySet and it's image.
	 * 
	 * @param entitySet
	 */
	public void unmap(EntitySet entitySet1, EntitySet entitySet2) {
		Utils.validateNotNull(entitySet1);
		Utils.validateNotNull(entitySet2);

		Utils.validateContains(model1, entitySet1);
		Utils.validateContains(model2, entitySet2);

		Utils.validateMapped(entitySet1);
		Utils.validateMapped(entitySet2);

		entitySet1.setMappedTo(null);
		entitySet2.setMappedTo(null);
	}

	public List<String> getTransformationCodes() {
		return transformationCodes;
	}

	public void addTransformationCode(String code) {
		transformationCodes.add(code);
	}

	public void removeTransformationCode(String code) {
		transformationCodes.remove(code);
	}

	public ERModel getExemplarModel() {
		return model1;
	}

	public ERModel getStudentModel() {
		return model2;
	}
}