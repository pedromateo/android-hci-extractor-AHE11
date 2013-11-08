package org.mmi.android.instrumentation.viewproxies;

import java.util.LinkedList;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;


/**
 * Content-proxy class for EditText objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class EditTextProxy extends BaseViewProxy {

	public EditTextProxy() {
		super();
		
		adaptedWidgets.add(EditText.class);
		adaptedWidgets.add(AutoCompleteTextView.class);
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
		String text = ((EditText)v).getText().toString();
		nelems += StringUtils.countWords(text);
		return nelems;
	}
	
	@Override
	public boolean isQuestion(View v) {
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
