package org.mmi.android.instrumentation.filters.interaction;

import org.mmi.android.instrumentation.filters.BaseFilter;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.utils.StringUtils;
import org.mmi.android.instrumentation.MmiFacade;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/**
 * Instrumentation filter for speech input.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class SpeechInputFilter extends BaseFilter {

	private static final String TAG = "SpeechInputFilter";

	/**
	 * 
	 * @param f
	 */
	public SpeechInputFilter(InstrumentationContext ic, MmiFacade f){
		super(ic, f);
	}

	@Override
	public void onInteractionInitialize() {
		// do previous configuration here
	}
	
	@Override
	public void onInteractionStart() {
	}

	@Override
	public void onInteractionEnd() {
	}

	@Override
	public void install(View v) {
		//no installation needed in this prototype
	}
	
	/// //////////////////////////////////////////////////////////
	/// //////////////////////////////////////////////////////////

	/// 
	/// filtering (redirection) methods
	///

	
	///
	///speech input 
	
	/**
	 * Handles a new utterance.
	 * @param u the utterance text
	 */
	public void onUtterance(String u){
		Log.d(TAG,"OnUtterance: " + u);
		_mmifacade.overallWords(0, StringUtils.countWords(u));
		_mmifacade.overallConcepts(0, 1);
	}
	
	/**
	 * Handles a correctly parsed utterance result.
	 */
	public void onCorrectlyParsedUtterance(){
		Log.d(TAG,"onCorrectlyParsedUtterance");
		_mmifacade.correctlyParsedUtterance(0);
	}
	
	/**
	 * Handles a partially correct parsed utterance result.
	 */
	public void onPartiallyParsedUtterance(){
		Log.d(TAG,"onPartiallyParsedUtterance");
		_mmifacade.partiallyParsedUtterance(0);
	}
	
	/**
	 * Handles an incorrect parsed utterance result.
	 */
	public void onIncorrectlyParsedUtterance(){
		Log.d(TAG,"onIncorrectlyParsedUtterance");
		_mmifacade.incorrectlyParsedUtterance(0);
	}
	
	///
	///speech output
	
	/**
	 * Handles an ASR reject.
	 */
	public void onASRReject(){
		Log.d(TAG,"OnASRReject");
		_mmifacade.isASRRejection(0);
	}

	@Override
	public void install(ActionBar actionBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void install(MenuItem item, View parentView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void install(AlertDialog dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void install(OptionsMenuObserver observer) {
		// TODO Auto-generated method stub
		
	}
}
