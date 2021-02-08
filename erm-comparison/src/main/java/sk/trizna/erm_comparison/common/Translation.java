package sk.trizna.erm_comparison.common;

public class Translation {

	private String template;
	
	public Translation(Object code) {
		Utils.validateNotNull(code);
		
		this.template = TranslationUtils.getTemplate(code.toString());
		if (template == null) {
			throw new IllegalStateException("No translation template found for " + code);
		}
	}

	public void setArgument(String key, String value) {
		Utils.validateNotNull(key);
		Utils.validateNotNull(value);
		Utils.validateNotNull(template);
		
		template = template.replace(key, value);
	}

	@Override
	public String toString() {
		return template;
	}
	
	
}
