package sk.trizna.erm_comparison.entity_relationship_model;

import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.StringUtils;

public class ERModelElementName implements ERText {

	private String name;
	
	public ERModelElementName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getText() {
		return name;
	}

	public boolean isComposite() {
		if (StringUtils.isBlank(getText())) {
			return false;
		}
		return getText().indexOf(PrintUtils.DELIMITER_SEMICOLON) >= 0;
	}
}
