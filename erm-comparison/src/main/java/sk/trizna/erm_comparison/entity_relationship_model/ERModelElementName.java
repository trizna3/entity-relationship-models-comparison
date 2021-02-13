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
	public int hashCode() {
		return StringUtils.computeStringHash(name);
	}

	@Override
	public String getText() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ERText)) {
			return false;
		}
		return StringUtils.areEqual(this.getText(), ((ERText) obj).getText());
	}
	
	public boolean isComposite() {
		if (StringUtils.isBlank(getText())) {
			return false;
		}
		return getText().indexOf(PrintUtils.DELIMITER_SEMICOLON) >= 0;
	}
}
