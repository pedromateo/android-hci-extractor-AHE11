package org.mmi.android.instrumentation.objectproxies;

import org.mmi.android.instrumentation.utils.StringUtils;
import org.mmi.android.instrumentation.utils.UiTraverser;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Proxy class for Toast messages.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class ToastProxy extends BaseObjectProxy {

	public ToastProxy() {
		adaptedClasses.add(Toast.class);
	}

	@Override
	public int getElements(Object o) {
		int nelems = 0;
		//one element for the widget
		nelems++;
		//one element for each word in the text of the toast
		TextView tv = null;
		View v = ((Toast)o).getView();
		View auxv = null;
		UiTraverser ut = new UiTraverser(v);
		while(ut.hasNext()){
			auxv = ut.next();
			if (auxv != null && auxv instanceof TextView){
				tv = (TextView)auxv;
				break;
			}
		}

		if (tv != null){
			String text = (String) tv.getText();
			nelems += StringUtils.countWords(text);
		}
		return nelems;
	}

	@Override
	public boolean isQuestion(Object o) {
		return false;
	}

	@Override
	public int getConcepts(Object o) {
		return 0;
	}

	@Override
	public int getNoise(Object o) {
		return 0;
	}

	@Override
	public boolean isFeedback(Object o) {
		return true;
	}

}
