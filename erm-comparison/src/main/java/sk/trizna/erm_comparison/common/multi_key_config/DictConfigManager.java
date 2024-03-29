package sk.trizna.erm_comparison.common.multi_key_config;

import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.utils.PrintUtils;

public class DictConfigManager extends MultiKeyConfigManager {

	private static DictConfigManager INSTANCE;
	
	public static DictConfigManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DictConfigManager();
		}
		return INSTANCE;
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.DICT_CONFIG_NAME;
	}

	@Override
	protected String getDelimiter() {
		return PrintUtils.DELIMITER_COMMA;
	}

	@Override
	protected boolean isSplitSections() {
		return true;
	}
}
