package sk.trizna.erm_comparison.common.key_config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sk.trizna.erm_comparison.common.StringUtils;
import sk.trizna.erm_comparison.common.Utils;

/**
 * Extension of {@link KeyConfigManager}, for managing configs of structure key = list of elements
 * 
 * Class is parametrised for arbitrary delimiter splitting depth (eg. whether return type are List<String> or List<List<String>> and so on).
 * 
 * Number of delimiters in {@link #getDelimiters()} must correspond to Type parameter embedding depth!
 * 
 * @author Adam Trizna
 *
 */
public abstract class KeyListConfigManager<T extends Object> extends KeyConfigManager {
	
	protected abstract String[] getDelimiters();
	private Map<String,T> valueMap = new HashMap<String, T>();
	
	@SuppressWarnings("unchecked")
	public T getResource(String resource) {
		preGetResource(resource);
		
		T value = valueMap.get(resource);
		if (value != null) {
			return value;
		}
		throw new IllegalArgumentException("Unknown resource!");
	}
	
	@Override
	protected void loadValues(Properties prop) {
		Utils.validateNotNull(prop);
		
		for (Object key : prop.keySet()) {
			valueMap.put(key.toString(), parseValue(prop.get(key).toString()));
		}
	}
	
	@Override
	protected Set<String> keySetInternal() {
		return valueMap.keySet();
	}
	
	@SuppressWarnings("unchecked")
	private T parseValue(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		
		Object values = new ArrayList<Object>(Arrays.asList(value));
		
		for (String delimiter : getDelimiters()) {
			values = split(values,delimiter);
		}
		
		try {
			return (T) values;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Object split(Object value, String delimiter) {
		Utils.validateNotNull(value);
		Utils.validateNotNull(delimiter);
		
		if (value instanceof List<?>) {
			List<Object> valueList = (List<Object>) value;
			for (int i = 0; i < valueList.size(); i++) {
				Object element = valueList.get(i);
				valueList.set(i, split(element,delimiter));
			}
			return valueList;
		} else {
			String[] valSplit = value.toString().split(delimiter);
			if (valSplit.length == 1) {
				return value.toString().trim();
			} else {
				return Utils.trimAll(valSplit);
			}
		}
	}
}
