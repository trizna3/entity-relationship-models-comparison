package sk.trizna.erm_comparison.comparing;

import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.transformations.Transformation;

public class ERModelDiff {
	
	private ERModel exemplarModel;
	private ERModel studentModel;
	private Map<EntitySet,EntitySet> entitySetMap;
	private Map<Relationship,Relationship> relationshipMap;
	private List<EntitySet> notMappedExemplarEntitySets;
	private List<EntitySet> notMappedStudentEntitySets;
	private List<Transformation> transformationsMade;

	public ERModelDiff(ERModel exemplarModel, ERModel studentModel, Map<EntitySet,EntitySet> entitySetMap, Map<Relationship,Relationship> relationshipMap, List<EntitySet> notMappedExemplarEntitySets, List<EntitySet> notMappedStudentEntitySets, List<Transformation> transformationsMade) {
		this.exemplarModel = exemplarModel;
		this.studentModel = studentModel;
		this.entitySetMap = entitySetMap;
		this.relationshipMap = relationshipMap;
		this.notMappedExemplarEntitySets = notMappedExemplarEntitySets;
		this.notMappedStudentEntitySets = notMappedStudentEntitySets;
		this.transformationsMade = transformationsMade;
	}

	public ERModel getExemplarModel() {
		return exemplarModel;
	}

	public ERModel getStudentModel() {
		return studentModel;
	}
	
	public List<EntitySet> getNotMappedExemplarEntitySets() {
		return notMappedExemplarEntitySets;
	}
	
	public List<EntitySet> getNotMappedStudentEntitySets() {
		return notMappedStudentEntitySets;
	}
	
	public List<Transformation> getTransformationsMade() {
		return transformationsMade;
	}

	public Map<EntitySet,EntitySet> getEntitySetMap() {
		return entitySetMap;
	}

	public Map<Relationship,Relationship> getRelationshipMap() {
		return relationshipMap;
	}
}
