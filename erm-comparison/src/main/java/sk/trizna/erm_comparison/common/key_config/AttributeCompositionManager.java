package sk.trizna.erm_comparison.common.key_config;

import java.util.List;

import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.enums.EnumConstants;

public class AttributeCompositionManager extends KeyListConfigManager<List<String>> {
	
	private static AttributeCompositionManager INSTANCE;
		
	public static AttributeCompositionManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AttributeCompositionManager();
		}
		return INSTANCE;
	}
	
	@Override
	protected String[] getDelimiters() {
		return new String[] {PrintUtils.DELIMITER_COMMA};
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.ATTRIBUTE_COMPOSITION_NAME;
	}
}
