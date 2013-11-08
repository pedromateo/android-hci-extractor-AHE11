package org.mmi.android.instrumentation.viewproxies.listeners;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Dialog;
import android.content.DialogInterface.OnShowListener;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ListenerExtractor {

	private final static String TAG = ListenerExtractor.class.getSimpleName();

	/**
	 * Returns the OnClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnClickListener getOnClickListener(View v){
		OnClickListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnClickListener");
				f.setAccessible(true);
				listener = (OnClickListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.e(TAG, "Could not extract mOnClickListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnTouchListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnTouchListener getOnTouchListener(View v){
		OnTouchListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnTouchListener");
				f.setAccessible(true);
				listener = (OnTouchListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnTouchListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnLongClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnLongClickListener getOnLongClickListener(View v) {
		OnLongClickListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnLongClickListener");
				f.setAccessible(true);
				listener = (OnLongClickListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnLongClickListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnItemClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnItemClickListener getOnItemClickListener(View v){
		OnItemClickListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnItemClickListener");
				f.setAccessible(true);
				listener = (OnItemClickListener) f.get(listenerInfo);
			} 
		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnItemClickListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnItemClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnItemClickListener getOnItemClickListenerAv(View v){
		OnItemClickListener listener = null;

		try {
			Class<? extends Object> clazz = AdapterView.class;
			Field f = clazz.getDeclaredField("mOnItemClickListener");
			f.setAccessible(true);
			listener = (OnItemClickListener) f.get(v);
		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnItemClickListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnItemLongClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnItemLongClickListener getOnItemLongClickListener(View v){
		OnItemLongClickListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnItemLongClickListener");
				f.setAccessible(true);
				listener = (OnItemLongClickListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnItemLongClickListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnItemLongClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	////@Override
	public static OnItemLongClickListener getOnItemLongClickListenerAv(AdapterView<?> av){
		OnItemLongClickListener listener = null;

		try {
			Class<? extends Object> clazz = AdapterView.class;
			Field f = clazz.getDeclaredField("mOnItemLongClickListener");
			f.setAccessible(true);
			listener = (OnItemLongClickListener) f.get(av);

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnItemLongClickListener from AdapterView", e);
		}

		return listener;
	}

	//@Override
	public static OnItemSelectedListener getOnItemSelectedListener(View v) {
		OnItemSelectedListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnItemSelectedListener");
				f.setAccessible(true);
				listener = (OnItemSelectedListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnItemSelectedListener", e);
		}

		return listener;
	}

	//@Override
	public static OnItemSelectedListener getOnItemSelectedListenerAv(View av) {
		OnItemSelectedListener listener = null;

		try {
			Class<? extends Object> clazz = AdapterView.class;
			Field f = clazz.getDeclaredField("mOnItemSelectedListener");
			f.setAccessible(true);
			listener = (OnItemSelectedListener) f.get(av);
		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnItemSelectedListener from AdapterView", e);
		}

		return listener;
	}

	/**
	 * Returns the OnKeyboardActionListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnKeyboardActionListener getOnKeyboardActionListener(View v){
		OnKeyboardActionListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnKeyboardActionListener");
				f.setAccessible(true);
				listener = (OnKeyboardActionListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnKeyboardActionListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnKeyListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnKeyListener getOnKeyListener(View v){
		OnKeyListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnKeyListener");
				f.setAccessible(true);
				listener = (OnKeyListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnKeyListener", e);
		}

		return listener;
	}

	/**
	 * Returns the OnKeyListener of a view.
	 * @param v the view object
	 * @return the listener
	 */
	//@Override
	public static OnCreateContextMenuListener getOnCreateContextMenuListener(View v) {
		OnCreateContextMenuListener listener = null;

		try {
			Object listenerInfo = getListenerInfo(v); 

			if(listenerInfo != null) {
				Class<? extends Object> clazz = listenerInfo.getClass();

				Field f = clazz.getDeclaredField("mOnCreateContextMenuListener");
				f.setAccessible(true);
				listener = (OnCreateContextMenuListener) f.get(listenerInfo);
			} 

		} catch (Exception e) {
			Log.i(TAG, "Could not extract mOnKeyListener", e);
		}

		return listener;
	}

	@SuppressLint("NewApi")
	private static Object getListenerInfo(View v) {
		Field f = null;

		try {

			f = View.class.getDeclaredField("mListenerInfo");
			f.setAccessible(true);

			return (Object) f.get(v);

		} catch (Exception e) {
			Log.i(TAG, "Could not extract field mListenerInfo", e);
			return null;
		}

	}

	//@Override
	@TargetApi(11)
	public static TabListener getTabListener(Tab tab) {
		TabListener tabListener = null;

		// Get TabListener for ActionBarImpl.TabImpl, support of other
		// implementations needs further research and implementation here.

		try {

			Class<? extends Object> clazz = Class.forName("com.android.internal.app.ActionBarImpl$TabImpl");
			Field f = clazz.getDeclaredField("mCallback");
			f.setAccessible(true);

			tabListener = (TabListener) f.get(tab);

		} catch (Exception e) {
			Log.i(TAG, "getTabListener: Could not extract mCallback", e);
		}

		return tabListener;
	}

	//@Override
	public static OnMenuItemClickListener getOnMenuItemClickListener(MenuItem item) {
		OnMenuItemClickListener listener = null;

		try {
			Class<? extends Object> clazz = Class.forName("com.android.internal.view.menu.MenuItemImpl");
			Field f = clazz.getDeclaredField("mClickListener");
			f.setAccessible(true);
			listener = (OnMenuItemClickListener) f.get(item);
		} catch (Exception e) {
			Log.e(TAG, "getOnMenuItemClickListener: " + e);
		}

		return listener;
	}

	//@Override
	public static OnShowListener getOnShowListener(Dialog dialog) {
		OnShowListener listener = null;

		try {
			// first get the Message from the dialog. Message holds the listener...
			Class<? extends Object> dialogClazz = Dialog.class;
			Field messageField = dialogClazz.getDeclaredField("mShowMessage");
			messageField.setAccessible(true);
			Message message = (Message) messageField.get(dialog);

			// Get the listener from the message (if message not null, or in 
			// other words a listener was set previously
			if ( message != null ) {
				Class<? extends Object> messageClass = Message.class;
				Field listenerField = messageClass.getDeclaredField("obj");
				listenerField.setAccessible(true);
				listener = (OnShowListener) listenerField.get(message);
			}

		} catch (Exception e) {
			Log.d(TAG, "getOnShowListener: Colud not extract OnShowListener: " + e);
		}

		return listener;
	}
}


/*import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Dialog;
import android.content.DialogInterface.OnShowListener;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Build;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public abstract class ListenerExtractor {
	
	public static ListenerExtractor createListenerExtractor() {
		int sdkVersion = Build.VERSION.SDK_INT;
		ListenerExtractor extractor;
		if (sdkVersion < 16) {
			extractor = new ListenerExtractor_8();
		} else {
			extractor = new ListenerExtractor_16();
		}
		return extractor;
	}
	
	/// abstract methods

	public abstract OnClickListener getOnClickListener(View v);

	public abstract OnTouchListener getOnTouchListener(View v);

	public abstract OnLongClickListener getOnLongClickListener(View v);

	public abstract OnItemClickListener getOnItemClickListener(View v);

	public abstract OnItemLongClickListener getOnItemLongClickListener(View v);

	public abstract OnKeyboardActionListener getOnKeyboardActionListener(View v);

	public abstract OnKeyListener getOnKeyListener(View v);

	public abstract TabListener getTabListener(Tab actionBarTab);

	public abstract OnCreateContextMenuListener getOnCreateContextMenuListener(View v);

	public abstract OnItemSelectedListener getOnItemSelectedListener(View v);

	public abstract OnItemClickListener getOnItemClickListenerAv(View v) ;

	public abstract OnItemSelectedListener getOnItemSelectedListenerAv(View av);

	public abstract OnItemLongClickListener getOnItemLongClickListenerAv(AdapterView<?> av);

	public abstract OnMenuItemClickListener getOnMenuItemClickListener(MenuItem item);

	public abstract OnShowListener getOnShowListener(Dialog dialog);
}*/