package org.mmi.android.instrumentation.filters.interaction;

import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.utils.UiTraverser;
import org.mmi.android.instrumentation.viewproxies.BaseViewProxy;
import org.mmi.android.instrumentation.viewproxies.ButtonProxy;
import org.mmi.android.instrumentation.viewproxies.CheckBoxProxy;
import org.mmi.android.instrumentation.viewproxies.EditTextProxy;
import org.mmi.android.instrumentation.viewproxies.ListViewProxy;
import org.mmi.android.instrumentation.viewproxies.ProgressBarProxy;
import org.mmi.android.instrumentation.viewproxies.RadioButtonProxy;
import org.mmi.android.instrumentation.viewproxies.RadioGroupProxy;
import org.mmi.android.instrumentation.viewproxies.RatingBarProxy;
import org.mmi.android.instrumentation.viewproxies.SeekBarProxy;
import org.mmi.android.instrumentation.viewproxies.SpinnerProxy;
import org.mmi.android.instrumentation.viewproxies.SurfaceViewProxy;
import org.mmi.android.instrumentation.viewproxies.TabProxy;
import org.mmi.android.instrumentation.viewproxies.TextViewProxy;
import org.mmi.android.instrumentation.viewproxies.ViewProxyManager;
import org.mmi.android.instrumentation.viewproxies.ViewProxyManager.ProxyConflictException;

import android.util.Log;
import android.view.View;

public class ViewProcessor {

	private final static String TAG = ViewProcessor.class.getSimpleName();
	private ViewProxyManager pmanager_;
	private final MmiFacade mmim_;

	public ViewProcessor(MmiFacade mmim_) {
		this.mmim_ = mmim_;

		//initialize view proxies
		_initializeViewProxyManager();
	}


	///
	/// view proxies management
	///
	private void _initializeViewProxyManager() {
		pmanager_ = new ViewProxyManager();

		//add different proxies
		try {
			pmanager_.addProxy(new ButtonProxy());
			pmanager_.addProxy(new CheckBoxProxy());
			pmanager_.addProxy(new EditTextProxy());
			pmanager_.addProxy(new ListViewProxy());
			pmanager_.addProxy(new SurfaceViewProxy());
			pmanager_.addProxy(new ProgressBarProxy());
			pmanager_.addProxy(new RadioButtonProxy());
			pmanager_.addProxy(new RadioGroupProxy());
			pmanager_.addProxy(new RatingBarProxy());
			pmanager_.addProxy(new SeekBarProxy());
			pmanager_.addProxy(new SpinnerProxy());
			pmanager_.addProxy(new TabProxy());
			pmanager_.addProxy(new TextViewProxy());
		} catch (ProxyConflictException e) {
			Log.e(TAG, "Error while initialize ViewProxyManager", e);
		}
	}

	public void processFromTopView(View v) {
		//get the top parent view of this GUI
		View topView = UiTraverser.getTopParent(v);
		if (topView == null) {
			Log.w(TAG,"processFromTopView :: topView is null");
		} else {
			processView(topView);
		}
	}

	public void processView(View view) {
		//traverse the GUI
		UiTraverser uit = new UiTraverser(view);
		View auxv = null;
		while ( uit.hasNext() ) {
			auxv = uit.next();
			if (auxv == null){
				Log.w(TAG,"processView :: auxv is null");
			}
			else{
				//Log.e(TAG,"@@@@@@@@@@@@@@@@@@ processing View " + auxv.toString());
				analyzeViewContent(auxv);
			}
		} // end while
	}

	
	
	public void analyzeViewContent(View view) {

		BaseViewProxy proxy = pmanager_.getProxy(view);
		if ( proxy != null ) {
			Log.v(TAG,"%%%% (View) " + view.getClass().getSimpleName());
			Log.v(TAG,"%%%% (elements) " + proxy.getElements(view));
			Log.v(TAG,"%%%% (concepts) " + proxy.getConcepts(view));
			Log.v(TAG,"%%%% (noise) " + proxy.getNoise(view));
			Log.v(TAG,"%%%% (is question) " + proxy.isQuestion(view));
			Log.v(TAG,"%%%% (is feedback) " + proxy.isFeedback(view));


			//set elements or feedback elements
			int nOfElements = proxy.getElements(view);
			if (nOfElements > 0){
				if ( proxy.isFeedback(view) ) {
					mmim_.newGuiFeedback(0, nOfElements);
				} else {
					mmim_.newGuiElements(0, nOfElements);
				}
			}

			//set concepts
			int nOfConcepts = proxy.getConcepts(view);
			if (nOfConcepts > 0) {
				mmim_.newGuiConcepts(0, nOfConcepts);
			}

			//set noise
			int nOfNoise = proxy.getNoise(view);
			if (nOfNoise > 0) {
				mmim_.newGuiNoise(0, nOfNoise);
			}

			//set question
			if (proxy.isQuestion(view)) {
				// add on new question
				mmim_.newGuiQuestions(0,1);
			} 
			// end if proxy != null
		} else {
			Log.w(TAG, "No Proxy found for: " + view.getClass().toString());
		}
	}

}
