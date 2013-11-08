package org.mmi.android.instrumentation.viewproxies;

import org.mmi.android.instrumentation.utils.StringUtils;

import android.view.View;

public class DecorViewProxy extends BaseViewProxy {
	
	public DecorViewProxy() {
		super();
		// not applicable
		//adaptedWidgets.add(com.android.internal.ut android.policy.PhoneWindow  DecorView.class);
	}

	///
	/// BaseViewProxy abstract methods implementation
	///

	@Override
	public int getElements(View v) {
		int nelems = 0;
		return nelems;
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
		// TODO
		return 0;
	}

	@Override
	public boolean isFeedback(View v) {
		return false;
	}

}
