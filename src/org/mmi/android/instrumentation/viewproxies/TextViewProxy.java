package org.mmi.android.instrumentation.viewproxies;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.view.View;
import android.widget.TextView;

/**
 * Content-proxy class for TextView objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class TextViewProxy extends BaseViewProxy {
	

	public TextViewProxy() {
		super();

		adaptedWidgets.add(TextView.class);
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
		CharSequence text = ((TextView)v).getText();
		nelems += StringUtils.countWords(text.toString());

		return nelems;
	}
	
	@Override
	public boolean isQuestion(View v) {
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