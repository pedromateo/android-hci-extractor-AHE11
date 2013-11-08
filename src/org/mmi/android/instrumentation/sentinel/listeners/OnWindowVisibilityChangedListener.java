package org.mmi.android.instrumentation.sentinel.listeners;

import org.mmi.android.instrumentation.sentinel.SentinelView;

/**
 * This interface has to be implemented by window visibility
 * changes listeners.
 * 
 * @author mateo-navarro.pedro
 *
 */
public interface OnWindowVisibilityChangedListener{

	/**
	 * Notifies that the window visibility has changed.
	 * @param sv the sentinel view object
	 * @param visibility a visibility-degree identifier
	 */
	public void onWindowVisibilityChange(SentinelView sv, int visibility);

}
