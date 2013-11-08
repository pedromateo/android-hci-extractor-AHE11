package org.mmi.android.instrumentation.viewproxies;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * Content-proxy class for ListView objects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class ListViewProxy extends BaseViewProxy {
	
	private final static String TAG = ListViewProxy.class.getSimpleName();

	public ListViewProxy() {
		super();

		adaptedWidgets.add(ListView.class);
	}

	///
	/// BaseViewProxy abstract methods implementation
	///
	
	@Override
	public int getElements(View v) {
		int nelems = 0;
		//one element for the widget
		nelems++;
		//find elements in the content of the list
		ListAdapter la = ((ListView)v).getAdapter();
		int i = 0;
		
		if (la != null) {
			//for each element
			while ( i < la.getCount() ) {
				//add one element for the widget
				nelems++;
				
				//add one element for each word
				Object item = la.getItem(i);
				if ( item != null ) {
					nelems += StringUtils.countWords( item.toString() );
				}
					
				i++;
			}
		} else {
			Log.d(TAG, "ListView's ListAdapter was null. No additional elements were counted.");
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
