package org.mmi.android.instrumentation.viewproxies;

import android.view.View;
import android.widget.SeekBar;

/**
 * Content-proxy class for SeekBar objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class SeekBarProxy extends BaseViewProxy {

	public SeekBarProxy() {
		super();

		adaptedWidgets.add(SeekBar.class);
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
		return true;
	}
	
	@Override
	public int getConcepts(View v) {
		// TODO
		return 1;
	}
	
	@Override
	public int getNoise(View v) {
		// TODO
		return 0;
	}

	@Override
	public boolean isFeedback(View v) {
		return false;
	}
	
}