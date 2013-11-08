package org.mmi.android.instrumentation;

/**
 * Interface implemented by the classes that need to be
 * notified when the interaction starts and ends.
 * 
 * @author mateo-navarro.pedro
 *
 */
public interface InteractionListener {



	/**
	 * Notifies when interaction is ready to start
	 */
	public abstract void onInteractionInitialize();
	
	/**
	 * Notifies when interaction starts
	 */
	public abstract void onInteractionStart();
	
	/**
	 * Notifies when interaction ends
	 */
	public abstract void onInteractionEnd();
}
