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
	
	private List<Transformation> forbiddenTransformations;

	public Mapping(ERModel exemplarModel, ERModel studentModel) {
		this.exemplarModel = exemplarModel;
		this.studentModel = studentModel;
		setTransformations(new ArrayList<>());
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
		if (!entitySet2.isEmpty()) {
			Utils.validateContains(studentModel, entitySet2);
		}

		Utils.validateNotMapped(entitySet1);
		if (!entitySet2.isEmpty()) {
			Utils.validateNotMapped(entitySet2);
		}

		entitySet1.setMappedTo(entitySet2);
		if (!entitySet2.isEmpty()) {
			entitySet2.setMappedTo(entitySet1);
		}
		
		exemplarModel.getNotMappedEntitySets().remove(entitySet1);
		if (!entitySet2.isEmpty()) {
			studentModel.getNotMappedEntitySets().remove(entitySet2);
		}
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
		if (!entitySet2.isEmpty()) {
			Utils.validateContains(studentModel, entitySet2);
		}

		Utils.validateMapped(entitySet1);
		if (!entitySet2.isEmpty()) {
			Utils.validateMapped(entitySet2);
		}

		entitySet1.setMappedTo(null);
		entitySet2.setMappedTo(null);
		
		exemplarModel.getNotMappedEntitySets().add(0, entitySet1);
		if (!entitySet2.isEmpty()) {
			studentModel.getNotMappedEntitySets().add(0, entitySet2);
		}
	}

	public List<Transformation> getTransformations() {
		return transformations;
	}

	public void addTransformation(Transformation transformation) {
		Utils.validateNotNull(transformation);

		getTransformations().add(transformation);
	}

	public void removeTransformation(Transformation transformation) {
		getTransformations().remove(transformation);
	}

	public ERModel getExemplarModel() {
		return exemplarModel;
	}

	public ERModel getStudentModel() {
		return studentModel;
	}

	public void setTransformations(List<Transformation> transformations) {
		this.transformations = transformations;
	}

	public List<Transformation> getForbiddenTransformations() {
		if (forbiddenTransformations == null) {
			forbiddenTransformations = new ArrayList<>();
		}
		return forbiddenTransformations;
	}
	
	public void addForbiddenTransformation(Transformation t) {
		Utils.validateNotNull(t);
		getForbiddenTransformations().add(t);
	}
	
	public void removeForbiddenTransformation(Transformation t) {
		Utils.validateNotNull(t);
		getForbiddenTransformations().remove(t);
	}
}