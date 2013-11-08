package org.mmi.android.instrumentation.viewproxies;

import android.view.View;
import android.widget.RadioGroup;

/**
 * Content-proxy class for RadioGroup objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class RadioGroupProxy extends BaseViewProxy {

	public RadioGroupProxy() {
		super();

		adaptedWidgets.add(RadioGroup.class);
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
