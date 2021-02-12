package sk.trizna.erm_comparison.common.key_config;

import java.util.List;

import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.enums.EnumConstants;

public class TransformationRoleManager extends KeyListConfigManager<List<List<String>>> {
	
	private static TransformationRoleManager INSTANCE;
		
	public static TransformationRoleManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TransformationRoleManager();
		}
		return INSTANCE;
	}
	
	@Override
	protected String[] getDelimiters() {
		return new String[] {PrintUtils.DELIMITER_DASH,PrintUtils.DELIMITER_COMMA};
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.TRANSFORMATION_ROLE_NAME;
	}
}
