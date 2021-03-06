package sk.trizna.erm_comparison.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.Utils;

public class Transformation {

	private EnumTransformation code;
	private Map<Transformable, EnumTransformationRole> argumentMap;
	private List<Transformation> preconditions;
	/**
	 * Used in transformation decomposition. 
	 */
	private boolean processed = false;

	public Transformation() {
	}
	
	public Transformation(Transformation other) {
		this.code = other.getCode();
		this.argumentMap = new HashMap<>(other.getArgumentMap());
	}

	public EnumTransformation getCode() {
		return code;
	}

	public void setCode(EnumTransformation code) {
		this.code = code;
	}

	public Set<Transformable> getArguments() {
		return getArgumentMap().keySet();
	}

	public Map<Transformable, EnumTransformationRole> getArgumentMap() {
		if (argumentMap == null) {
			argumentMap = new HashMap<>();
		}
		return argumentMap;
	}

	public void setArguments(Map<Transformable, EnumTransformationRole> argumentMap) {
		this.argumentMap = argumentMap;
	}

	public void addArgument(Transformable transformable, EnumTransformationRole transformableRole) {
		Utils.validateNotNull(transformable);
		Utils.validateNotNull(transformableRole);

		getArgumentMap().put(transformable, transformableRole);
	}

	public void removeArgument(Transformable transformable) {
		Utils.validateNotNull(transformable);
		assert getArgumentMap().containsKey(transformable);

		getArgumentMap().remove(transformable);
	}

	public void clearArguments() {
		getArgumentMap().clear();
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public List<Transformation> getPreconditions() {
		if (preconditions == null) {
			preconditions = new ArrayList<Transformation>();
		}
		return preconditions;
	}
	
	public void setPreconditions(List<Transformation> preconditions) {
		this.preconditions = preconditions;
	}

	public void addPrecondition(Transformation precondition) {
		getPreconditions().add(precondition);
	}

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}
}
