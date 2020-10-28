package transformations;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import common.Utils;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;

public class Transformation {

	private EnumTransformation code;
	private Map<Transformable, EnumTransformationRole> argumentMap;
	/**
	 * Used in transformation decomposition. 
	 */
	private boolean processed = false;

	public Transformation(EnumTransformation code) {
		this.code = code;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Transformation)) {
			return false;
		}
		Transformation other = (Transformation) obj;
		
		if (getCode() != other.getCode()) {
			return false;
		}
		return getArgumentMap().equals(other.getArgumentMap());
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
}
