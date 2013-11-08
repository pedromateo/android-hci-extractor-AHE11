package org.mmi.android.instrumentation.sentinel.listeners;


import org.mmi.android.instrumentation.sentinel.SentinelView;

/**
 * This interface has to be implemented by window visibility
 * changes listeners.
 * 
 * @author Pedro Mateo
 *
 */
public interface OnAttachedToWindowListener {

	/**
	 * Notifies that the window visibility has changed.
	 * @param sv the sentinel view object
	 * @param hasWindowFocus a visibility-degree identifier
	 */
	public void onAttachedToWindow(SentinelView sv, boolean attached);

}
