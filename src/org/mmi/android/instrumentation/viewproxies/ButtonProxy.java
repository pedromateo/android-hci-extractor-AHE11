package org.mmi.android.instrumentation.viewproxies;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.view.View;
import android.widget.Button;

/**
 * Content-proxy class for Button objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class ButtonProxy extends BaseViewProxy {
	
	public ButtonProxy() {
		super();
		
		adaptedWidgets.add(Button.class);
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
		String text = (String) ((Button)v).getText();
		nelems += StringUtils.countWords(text);
		return nelems;
	}

	@Override
	public boolean isQuestion(View v) {
		return false;
	}

	@Override
	public int getConcepts(View v) {
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
