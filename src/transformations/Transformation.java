package transformations;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import common.Utils;

public class Transformation {

	private String code;
	private Map<Transformable, String> argumentMap;

	public Transformation(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Transformable> getArguments() {
		return getArgumentMap().keySet();
	}

	public Map<Transformable, String> getArgumentMap() {
		if (argumentMap == null) {
			argumentMap = new HashMap<>();
		}
		return argumentMap;
	}

	public void setArguments(Map<Transformable, String> argumentMap) {
		this.argumentMap = argumentMap;
	}

	public void addArgument(Transformable transformable, String transformableRole) {
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
}
