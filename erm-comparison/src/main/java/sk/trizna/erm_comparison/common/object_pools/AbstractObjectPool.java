package sk.trizna.erm_comparison.common.object_pools;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObjectPool<O> {
	
	private List<O> freeInstances;
	private List<O> usedInstances;
	protected static final int INITIAL_CAPACITY = 20;
	
	/**
	 * Creates new instance.
	 */
	protected abstract O createObjectInternal();
	/**
	 * Clears existing instance fields. 
	 */
	protected abstract void freeObjectInternal(O instance);
	
	public O getObject() {
		O newInstance;
		if (!freeInstances.isEmpty()) {
			newInstance = freeInstances.remove(0);
		} else {
			newInstance = createObjectInternal();
		}
		usedInstances.add(newInstance);
		return newInstance;
	}
	
	public void freeObject(O instance) {
		freeObjectInternal(instance);
		
		usedInstances.remove(instance);
		freeInstances.add(instance);
	}
	
	public AbstractObjectPool() {
		freeInstances = new ArrayList<O>(INITIAL_CAPACITY);
		usedInstances = new ArrayList<O>(INITIAL_CAPACITY);
	}
}
