package sk.trizna.erm_comparison.common.object_pools;

import sk.trizna.erm_comparison.common.exceptions.PreconditionsNotMetException;

public class PreconditionsNotMetExceptionPool extends AbstractObjectPool<PreconditionsNotMetException> {

	private static PreconditionsNotMetExceptionPool INSTANCE;	
	
	public static PreconditionsNotMetExceptionPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PreconditionsNotMetExceptionPool(); 
		}
		return INSTANCE;
	}
	
	@Override
	protected PreconditionsNotMetException createObjectInternal() {
		return new PreconditionsNotMetException();
	}

	@Override
	protected void freeObjectInternal(PreconditionsNotMetException instance) {
	}
}
