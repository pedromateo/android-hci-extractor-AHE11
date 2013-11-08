package org.mmi.android.instrumentation.viewproxies;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.view.View;
import android.widget.Button;

// Tabs are supported since API 11
@SuppressLint("NewApi")
public class TabProxy extends BaseViewProxy {
	
	public TabProxy() {
		super();
		
		adaptedWidgets.add(Tab.class);
	}
	

	@Override
	public int getElements(View v) {
		int nElems = 0;
		//one element for the widget
		nElems++;
		//one element for each word in the text
		String text = (String) ((Button)v).getText();
		nElems += StringUtils.countWords(text);
		return nElems;
	}

	@Override
	public boolean isQuestion(View v) {
		return false;
	}

	@Override
	public int getConcepts(View v) {
		return 0;
	}

	@Override
	public int getNoise(View v) {
		return 0;
	}

	@Override
	public boolean isFeedback(View v) {
		return false;
	}

}
