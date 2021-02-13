package sk.trizna.erm_comparison.transformations;

import java.util.List;

import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.key_config.AppConfigManager;
import sk.trizna.erm_comparison.common.utils.TransformationRoleUtils;
import sk.trizna.erm_comparison.common.utils.TransformationUtils;
import sk.trizna.erm_comparison.common.utils.Utils;

/**
 * Validates transformation input/output arguments in {@link Transformator} methods.
 * 
 * @author Adam Trizna
 *
 */
public class TransformatorValidator {
	
	private static final TransformatorValidator INSTANCE = new TransformatorValidator();
	private boolean validate = Boolean.valueOf(AppConfigManager.getInstance().getResource(EnumConstants.CONFIG_VALIDATE_ROLES).toString());
	
	public static TransformatorValidator getInstance() {
		return INSTANCE;
	}
	
	public void preValidate(Transformation transformation) {
		Utils.validateNotNull(transformation);
		if (!validate) return;
		
		List<EnumTransformationRole> roles = TransformationRoleUtils.getMandatoryInputRoles(String.valueOf(transformation.getCode()));
		
		roles.forEach(role -> {
			if (!TransformationUtils.containsRole(transformation, role)) {
				throw new IllegalStateException("Mandatory input role " + role + " is missing for transformation " + transformation.getCode());
			}
		});
	}
	
	public void postValidate(Transformation transformation) {
		Utils.validateNotNull(transformation);
		if (!validate) return;
		
		List<EnumTransformationRole> roles = TransformationRoleUtils.getMandatoryOutputRoles(String.valueOf(transformation.getCode()));
		
		roles.forEach(role -> {
			if (!TransformationUtils.containsRole(transformation, role)) {
				throw new IllegalStateException("Mandatory output role " + role + " is missing for transformation " + transformation.getCode());
			}
		});
	}
}
