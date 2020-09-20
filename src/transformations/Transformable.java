package transformations;

public abstract class Transformable {

	private String transformationRole;
	private String transformationRoleBackup;

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
}
