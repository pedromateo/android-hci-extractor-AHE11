package org.mmi.android.instrumentation.viewproxies;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


/**
 * Content-proxy class for Spinner objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class SpinnerProxy extends BaseViewProxy {

	public SpinnerProxy() {
		super();

		adaptedWidgets.add(Spinner.class);
	}

	///
	/// BaseViewProxy abstract methods implementation
	///
	
	@Override
	public int getElements(View v) {
		int nelems = 0;
		//one element for the widget
		nelems++;
		//find elements in the content of the spinner
		SpinnerAdapter spa = ((Spinner)v).getAdapter();
		int i = 0;
		//for each element
		while (i < spa.getCount()){
			//add one element for the widget
			nelems++;
			//add one element for each word
			nelems += StringUtils.countWords(spa.getItem(i).toString());
			i++;
		}
		
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
