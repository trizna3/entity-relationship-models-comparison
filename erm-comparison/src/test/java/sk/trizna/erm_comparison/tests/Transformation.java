package sk.trizna.erm_comparison.tests;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;

/**
 * Transformations shall be constructed only through Transformation pools. To support this fact, Transformation(code) constructor doesn't exist.
 * This class bypasses this rule for test purposes.
 * 
 * @author Adam Trizna
 *
 */
public class Transformation extends sk.trizna.erm_comparison.transformations.Transformation{

	public Transformation() {
		super();
	}
	
	public Transformation(EnumTransformation code) {
		super();
		setCode(code);
	}
}
