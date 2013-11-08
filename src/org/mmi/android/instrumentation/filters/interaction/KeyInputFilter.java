package org.mmi.android.instrumentation.filters.interaction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.mmi.android.instrumentation.filters.BaseFilter;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.viewproxies.BaseViewProxy;
import org.mmi.android.instrumentation.viewproxies.listeners.ListenerExtractor;
import org.mmi.android.instrumentation.MmiFacade;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.TextView;

/**
 * Instrumentation filter for text and keyboard input.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class KeyInputFilter 
extends BaseFilter
implements View.OnKeyListener,
OnKeyboardActionListener{

	private static final String TAG = KeyInputFilter.class.getName();

	/**
	 * Constructor
	 */
	public KeyInputFilter(InstrumentationContext ic, MmiFacade f){
		super(ic, f);

		//
		_initializeSets();
		//
		p_onKeyListeners_ = new HashMap<Integer, OnKeyListener>();
		p_onKeyboardActionListeners_ = new HashMap<Integer, OnKeyboardActionListener>();
	}

	///
	/// InteractionListener interface implementation
	///

	@Override
	public void onInteractionInitialize() {
		// do previous configuration here
	}

	@Override
	public void onInteractionStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInteractionEnd() {
		// TODO Auto-generated method stub

	}

	///
	/// BaseFilter abstract methods implementation
	///

	public void install(View v)
	{
		try{
			if (v != null)
			{
				///
				/// Only for keyboard view
				///
				if (v instanceof KeyboardView){

					Log.d(TAG,"KeyboardView :: Installing listeners on KeyboardView");

					if (ListenerExtractor.getOnKeyboardActionListener(v) != null){
						p_onKeyboardActionListeners_.put(v.getId(),
								ListenerExtractor.getOnKeyboardActionListener(v));
					}
					((KeyboardView)v).setOnKeyboardActionListener(this);
				}

				///
				/// Only for text view
				///
				if (v instanceof TextView){
					Log.d(TAG,"TextView :: Installing listeners on TextView");

					if (ListenerExtractor.getOnKeyListener(v) != null){
						p_onKeyListeners_.put(v.getId(),
								ListenerExtractor.getOnKeyListener(v));
					}
					v.setOnKeyListener(this);
				}
			}
			else{
				Log.w(TAG,"View is NULL");
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error while installing listeners", e);
		}
	}

	@Override
	@TargetApi(11)
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

	/// //////////////////////////////////////////////////////////
	/// //////////////////////////////////////////////////////////

	///
	/// key sets
	///

	private Set<Integer> exploringSet_;
	private Set<Integer> commandSet_;

	private void _initializeSets(){
		exploringSet_ = new HashSet<Integer>();
		commandSet_ = new HashSet<Integer>();

		//commands
		commandSet_.add(KeyEvent.KEYCODE_ENTER);
		commandSet_.add(KeyEvent.KEYCODE_ALT_LEFT);
		commandSet_.add(KeyEvent.KEYCODE_ALT_RIGHT);
		commandSet_.add(KeyEvent.KEYCODE_DEL);
		/* FIXME uncomment if API 11 or higher
		commandSet_.add(KeyEvent.KEYCODE_CTRL_LEFT);
		commandSet_.add(KeyEvent.KEYCODE_CTRL_RIGHT);
		commandSet_.add(KeyEvent.KEYCODE_ESCAPE);
		commandSet_.add(KeyEvent.KEYCODE_INSERT);
		commandSet_.add(KeyEvent.KEYCODE_F1);
		commandSet_.add(KeyEvent.KEYCODE_F2);
		commandSet_.add(KeyEvent.KEYCODE_F3);
		commandSet_.add(KeyEvent.KEYCODE_F4);
		commandSet_.add(KeyEvent.KEYCODE_F5);
		commandSet_.add(KeyEvent.KEYCODE_F6);
		commandSet_.add(KeyEvent.KEYCODE_F7);
		commandSet_.add(KeyEvent.KEYCODE_F8);
		commandSet_.add(KeyEvent.KEYCODE_F9);
		commandSet_.add(KeyEvent.KEYCODE_F10);
		commandSet_.add(KeyEvent.KEYCODE_F11);
		commandSet_.add(KeyEvent.KEYCODE_F12);
		 */

		//exploring
		exploringSet_.add(KeyEvent.ACTION_UP);
		exploringSet_.add(KeyEvent.ACTION_DOWN);
		exploringSet_.add(KeyEvent.KEYCODE_BACK);
		exploringSet_.add(KeyEvent.KEYCODE_FORWARD);
		exploringSet_.add(KeyEvent.KEYCODE_TAB);
		/* FIXME uncomment if API 11 or higher
		exploringSet_.add(KeyEvent.KEYCODE_PAGE_DOWN);
		exploringSet_.add(KeyEvent.KEYCODE_PAGE_UP);
		exploringSet_.add(KeyEvent.KEYCODE_FORWARD);
		 */

		//exploringSet_.add(KeyEvent.KEYCODE_);//start
		//exploringSet_.add(KeyEvent.KEYCODE_);//end

	}

	/// //////////////////////////////////////////////////////////
	/// //////////////////////////////////////////////////////////

	///
	/// proxied listeners
	///

	/**
	 * All the variables called "p_<listener_name>" are a set of
	 * event listeners suscribed to a concrete event. 
	 * As the filter is installed as a listener into the tracked
	 * view objects, the old existing listeners have to be also
	 * notified about the events. The old existing listeners are
	 * included in these sets. In that way, the filter acts as
	 * a proxy forwarding all the notifications to the corresponding
	 * listeners.
	 */
	HashMap<Integer,OnKeyListener> p_onKeyListeners_;
	HashMap<Integer,OnKeyboardActionListener> p_onKeyboardActionListeners_;

	/// 
	/// filtering methods
	///


	/**
	 * Handles a key event performed over a concrete view.
	 */
	public boolean onKey(View v, int keyCode, KeyEvent event) 
	{
		Log.d(TAG,"event :: " + "onKey :: " + keyCode + " - " + event.getAction());

		///
		/// do my work
		///

		//if the key is a command key
		if (commandSet_.contains(keyCode) && 
				event.getAction() == KeyEvent.ACTION_UP){
			Log.d(TAG,"event :: " + "onKey - command");
			_mmifacade.keycommand(0);
		}
		//if the key is an exploring key
		else if (exploringSet_.contains(keyCode) && 
				event.getAction() == KeyEvent.ACTION_UP){
			Log.d(TAG,"event :: " + "onKey - exploring");
			_mmifacade.keyexplore(0);
		}
		//if the key is a text character
		//(the rest of keys are considered text keys)
		else if (event.getAction() == KeyEvent.ACTION_UP){
			Log.d(TAG,"event :: " + "onKey - text");
			_mmifacade.keytext(0);
		}

		///
		/// do the proxy work
		///

		OnKeyListener l = p_onKeyListeners_.get(v.getId());
		if (l != null)
			return l.onKey(v, keyCode, event);
		else{
			Log.d(TAG,"p_onKeyListener_ :: no pre-listener ID:" + v.getId());
			return false;
		}
	}

	/*public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
		Log.d(TAG,"event :: " + "onKey - dialog interface");

		// If the event is a key-down event on the "enter" button
		if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
				(keyCode == KeyEvent.KEYCODE_ENTER)) {

		}
		return false;
	}*/

	/**
	 * Handles a key event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		Log.d(TAG,"event :: " + "onKey" + " code: " + primaryCode);
	}

	/**
	 * Handles a press event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void onPress(int primaryCode) {
		Log.d(TAG,"event :: " + "onPress" + " code: " + primaryCode);
	}

	/**
	 * Handles a release event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void onRelease(int primaryCode) {
		Log.d(TAG,"event :: " + "onRelease" + " code: " + primaryCode);
	}

	/**
	 * Handles a text event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void onText(CharSequence text) {
		Log.d(TAG,"event :: " + "onText" + " :: " + text);
	}

	/**
	 * Handles a swipeDown event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void swipeDown() {
		Log.d(TAG,"event :: " + "swipeDown");
	}

	/**
	 * Handles a swipeLeft event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void swipeLeft() {
		Log.d(TAG,"event :: " + "swipeLeft");
	}

	/**
	 * Handles a swipeRight event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void swipeRight() {
		Log.d(TAG,"event :: " + "swipeRight");
	}

	/**
	 * Handles a swipeUp event performed over a concrete view.
	 * FIXME this listener has not been suscribed yet
	 */
	@Override
	public void swipeUp() {
		Log.d(TAG,"event :: " + "swipeUp");
	}
}
