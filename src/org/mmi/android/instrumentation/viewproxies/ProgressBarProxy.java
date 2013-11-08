package org.mmi.android.instrumentation.viewproxies;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Content-proxy class for ProgressBar objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class ProgressBarProxy extends BaseViewProxy {

	public ProgressBarProxy() {
		super();

		adaptedWidgets.add(ProgressBar.class);
	}

	///
	/// BaseViewProxy abstract methods implementation
	///
	
	@Override
	public int getElements(View v) {
		int nelems = 0;
		//one element for the widget
		nelems++;
		return nelems;
	}
	
	@Override
	public boolean isQuestion(View v) {
		// TODO
		return false;
	}
	
	@Override
	public int getConcepts(View v) {
		// TODO
		return 0;
	}
	
	@Override
	public int getNoise(View v) {
		// TODO
		return 0;
	}

	@Override
	public boolean isFeedback(View v) {
		return true;
	}
}
