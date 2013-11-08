package org.mmi.android.instrumentation;

import java.util.ArrayList;
import java.util.List;

import org.mmi.android.instrumentation.optionsmenu.listeners.OnOptionsMenuPreparedListener;

import android.view.Menu;
import android.view.View;

public class OptionsMenuObserver {
	private List<OnOptionsMenuPreparedListener> listeners = new ArrayList<OnOptionsMenuPreparedListener>();
	
	public void addListener(OnOptionsMenuPreparedListener listener) {
		if ( ! listeners.contains(listener) ) {
			listeners.add(listener);
		}
	}
	
	public void menuPrepared(Menu menu, View originView) {
		for ( OnOptionsMenuPreparedListener listener : listeners ) {
			listener.onMenuPrepared(menu, originView);
		}
	}
	
}
