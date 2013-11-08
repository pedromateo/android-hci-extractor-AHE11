package org.mmi.android.instrumentation.sentinel.listeners;

import org.mmi.android.instrumentation.sentinel.SentinelView;

/**
 * This interface has to be implemented by window visibility
 * changes listeners.
 * 
 * @author Stefan Hillmann
 *
 */
public interface OnWindowFocusChangedListener {

	/**
	 * Notifies that the window visibility has changed.
	 * @param sv the sentinel view object
	 * @param hasWindowFocus a visibility-degree identifier
	 */
	public void onWindowFocusChange(SentinelView sv, boolean hasWindowFocus);

}
