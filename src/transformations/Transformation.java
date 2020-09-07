package transformations;

import java.util.Set;

public class Transformation {

	private String code;
	private Set<Transformable> arguments;

	public Transformation(String code, Set<Transformable> arguments) {
		this.code = code;
		this.arguments = arguments;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Transformable> getArguments() {
		return arguments;
	}

	public void setArguments(Set<Transformable> arguments) {
		this.arguments = arguments;
	}
}
