package org.mmi.android.instrumentation.viewproxies;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.view.View;
import android.widget.RadioButton;


/**
 * Content-proxy class for RadioButton objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class RadioButtonProxy extends BaseViewProxy {

	public RadioButtonProxy() {
		super();

		adaptedWidgets.add(RadioButton.class);
	}

	///
	/// BaseViewProxy abstract methods implementation
	///
	
	@Override
	public int getElements(View v) {
		int nelems = 0;
		//one element for the widget
		nelems++;
		//one element for each word in the text
		String text = (String) ((RadioButton)v).getText();
		nelems += StringUtils.countWords(text);
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
		return false;
	}
}
