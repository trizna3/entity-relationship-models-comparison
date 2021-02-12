package sk.trizna.erm_comparison.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.key_config.TransformationRoleManager;

public class TransformationRoleUtils extends Utils {

	private static final TransformationRoleManager TRANSFORMATION_ROLE_MANAGER = TransformationRoleManager.getInstance();
	
	private static final char NON_MANDATORY_SIGN = '?';
	
	static class RoleArgument {
		String role;
		boolean mandatory = true;
		
		public RoleArgument(String roleRaw) {
			Utils.validateNotNull(roleRaw);
			
			if (roleRaw.charAt(0) == NON_MANDATORY_SIGN) {
				this.role = roleRaw.substring(1, roleRaw.length());
				this.mandatory = false;
			} else {
				this.role = roleRaw;
				this.mandatory = true;
			}
		}
	}
	
	
	public static List<EnumTransformationRole> getMandatoryInputRoles(String transformationCode) {
		validateNotNull(transformationCode);
		
		List<List<RoleArgument>> roles = getRoles(transformationCode);
		assert roles.size() == 2;
		return transformResponse(roles.get(0));
	}
	
	public static List<EnumTransformationRole> getMandatoryOutputRoles(String transformationCode) {
		validateNotNull(transformationCode);
		
		List<List<RoleArgument>> roles = getRoles(transformationCode);
		assert roles.size() == 2;
		return transformResponse(roles.get(1));
	}
	
	private static List<List<RoleArgument>> getRoles(String transformationCode) {
		List<List<String>> rolesRaw = TRANSFORMATION_ROLE_MANAGER.getResource(transformationCode);
		
		List<List<RoleArgument>> roles = new ArrayList<>(rolesRaw.size());
		for (Object roleRaw : rolesRaw) {
			if (roleRaw instanceof String) {
				roles.add(Arrays.asList(new RoleArgument(roleRaw.toString())));
			} else if (roleRaw instanceof List<?>) {
				roles.add(((List<?>)roleRaw).stream().map(role -> new RoleArgument(role.toString())).collect(Collectors.toList()));
			}
		}
		
		return roles;
	}
	
	private static List<EnumTransformationRole> transformResponse(List<RoleArgument> response) {
		return response.stream().filter(role -> role.mandatory).map(role -> EnumTransformationRole.valueOf(role.role)).collect(Collectors.toList());
	}
}
