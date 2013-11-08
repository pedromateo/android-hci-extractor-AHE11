package org.mmi.android.instrumentation.viewproxies.listeners;

import java.lang.reflect.Field;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Dialog;
import android.content.DialogInterface.OnShowListener;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
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

public class ListenerExtractor_8 {// extends ListenerExtractor {
	
	private final static String TAG = ListenerExtractor_8.class.getName();
	
	/*//**
	 * Returns the OnClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 *//*
	@Override
	public OnClickListener getOnClickListener(View v){
		OnClickListener listener = null;
		
		try {
			Class<? extends Object> cls = View.class;
	        Field f = cls.getDeclaredField("mOnClickListener");
	        f.setAccessible(true);
	        
	        listener = (OnClickListener) f.get(v);
		} catch (Exception e) {
			Log.e(TAG, "Error while extracting mOnClickListener", e);
		}
		
		return listener;
	}

	*//**
	 * Returns the OnTouchListener of a view.
	 * @param v the view object
	 * @return the listener
	 *//*
	@Override
	public OnTouchListener getOnTouchListener(View v){
		try {
			Field f = View.class.getDeclaredField("mOnTouchListener");
			f.setAccessible(true);
			return (OnTouchListener)f.get(v);
		} catch (Exception e) {
			Log.e(TAG, "Error while getting touch listner", e);	
			return null;
		}
	}

	*//**
	 * Returns the OnLongClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 *//*
	@Override
	public OnLongClickListener getOnLongClickListener(View v) {
		try {
			Field f = View.class.getDeclaredField("mOnLongClickListener");
			f.setAccessible(true);
			return (OnLongClickListener)f.get(v);
		} catch (Exception e) {
			Log.e(TAG, "Error while getting long click listner", e);	
			return null;
		}
	}

	*//**
	 * Returns the OnItemClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 *//*
	@Override
	public OnItemClickListener getOnItemClickListener(View v){
		try {
			Field f = View.class.getDeclaredField("mOnItemClickListener");
			f.setAccessible(true);
			return (OnItemClickListener)f.get(v);
		} catch (Exception e) {
			Log.e(TAG, "Error while getting item click listner", e);	
			return null;
		}
	}
	
	@Override
	public OnItemClickListener getOnItemClickListenerAv(View v) {
		Log.w(TAG, "Extracting OnItemClickListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}

	*//**
	 * Returns the OnItemLongClickListener of a view.
	 * @param v the view object
	 * @return the listener
	 *//*
	@Override
	public OnItemLongClickListener getOnItemLongClickListener(View v){
		try {
			Field f = View.class.getDeclaredField("mOnItemLongClickListener");
			f.setAccessible(true);
			return (OnItemLongClickListener)f.get(v);
		} catch (Exception e) {
			Log.e(TAG, "Error while getting item long click listner", e);	
			return null;
		}
	}

	@Override
	public OnItemLongClickListener getOnItemLongClickListenerAv(AdapterView<?> av) {
		Log.w(TAG, "Extracting OnItemLongClickListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}

	@Override
	public OnItemSelectedListener getOnItemSelectedListener(View v) {
		Log.w(TAG, "Extracting OnItemSelectedListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}

	@Override
	public OnItemSelectedListener getOnItemSelectedListenerAv(View av) {
		Log.w(TAG, "Extracting OnItemSelectedListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}

	*//**
	 * Returns the OnKeyboardActionListener of a view.
	 * @param v the view object
	 * @return the listener
	 *//*
	@Override
	public OnKeyboardActionListener getOnKeyboardActionListener(View v){
		try {
			Field f = KeyboardView.class.getDeclaredField("mOnKeyboardActionListener");
			f.setAccessible(true);
			return (OnKeyboardActionListener) f.get((KeyboardView)v);
		} catch (Exception e) {
			Log.e(TAG, "Error while getting keyboard action listner", e);
			return null;	
		}
	}
	
	*//**
	 * Returns the OnKeyListener of a view.
	 * @param v the view object
	 * @return the listener
	 *//*
	@Override
	public OnKeyListener getOnKeyListener(View v){
		try {
	        Field f = View.class.getDeclaredField("mOnKeyListener");
	        f.setAccessible(true);
	        //System.out.println(f.toString());
	        return (OnKeyListener) f.get(v);
		} catch (Exception e) {
			Log.e(TAG, "Error while getting key listner", e);
			return null;
		}
	}

	@Override
	public TabListener getTabListener(Tab tab) {
		Log.w(TAG, "Extracting OnCreateConetxListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}

	@Override
	public OnCreateContextMenuListener getOnCreateContextMenuListener(View v) {
		Log.w(TAG, "Extracting OnCreateContextMenuListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}

	@Override
	public OnMenuItemClickListener getOnMenuItemClickListener(MenuItem item) {
		Log.w(TAG, "Extracting OnMenuItemClickListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}

	@Override
	public OnShowListener getOnShowListener(Dialog dialog) {
		Log.w(TAG, "Extracting OnShowListener is only implemented in ListenerExtractor_16 currently.");
		return null;
	}*/
}
