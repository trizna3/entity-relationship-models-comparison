package transformations;

import java.util.HashSet;
import java.util.Set;

public abstract class Transformable {

	private String transformationRole;
	private String transformationRoleBackup;
	private Set<String> transformationFlags;

	public String getTransformationRole() {
		return transformationRole;
	}

	public void setTransformationRole(String transformationRole) {
		if (this.transformationRole != null) {
			this.transformationRoleBackup = this.transformationRole;
		}
		this.transformationRole = transformationRole;
	}

	public void resetTransformationRole() {
		if (this.transformationRoleBackup != null) {
			this.transformationRole = this.transformationRoleBackup;
			this.transformationRoleBackup = null;
		}
	}

	public Set<String> getTransformationFlags() {
		if (transformationFlags == null) {
			transformationFlags = new HashSet<>();
		}
		return transformationFlags;
	}

	public void setTransformationFlags(Set<String> transformationFlags) {
		this.transformationFlags = transformationFlags;
	}

	public void addTransformationFlag(String flag) {
		getTransformationFlags().add(flag);
	}

	public void removeTransformationFlag(String flag) {
		getTransformationFlags().remove(flag);
	}

	public boolean containsTransformationFlag(String flag) {
		return getTransformationFlags().contains(flag);
	}

}
