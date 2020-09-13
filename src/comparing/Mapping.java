package comparing;

import java.util.ArrayList;
import java.util.List;

import common.Utils;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import transformations.Transformation;

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
	private ERModel exemplarModel;

	/**
	 * Students ER model mapping is created upon.
	 */
	private ERModel studentModel;

	private List<Transformation> transformations;

	public Mapping(ERModel exemplarModel, ERModel studentModel) {
		this.exemplarModel = exemplarModel;
		this.studentModel = studentModel;
		transformations = new ArrayList<>();
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

		Utils.validateContains(exemplarModel, entitySet1);
		Utils.validateContains(studentModel, entitySet2);

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

		Utils.validateContains(exemplarModel, entitySet1);
		Utils.validateContains(studentModel, entitySet2);

		Utils.validateMapped(entitySet1);
		Utils.validateMapped(entitySet2);

		entitySet1.setMappedTo(null);
		entitySet2.setMappedTo(null);
	}

	public List<Transformation> getTransformations() {
		return transformations;
	}

	public void addTransformation(Transformation transformation) {
		Utils.validateNotNull(transformation);

		transformations.add(transformation);
	}

	public void removeTransformationCode(String code) {
		transformations.remove(code);
	}

	public ERModel getExemplarModel() {
		return exemplarModel;
	}

	public ERModel getStudentModel() {
		return studentModel;
	}
}