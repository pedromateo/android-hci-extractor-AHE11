package org.mmi.android.instrumentation.viewproxies;

import android.view.SurfaceView;
import android.view.View;

public class SurfaceViewProxy  extends BaseViewProxy {
	
	public SurfaceViewProxy() {
		super();

		adaptedWidgets.add(SurfaceView.class);
	}

	///
	/// SurfaceViewProxy abstract methods implementation
	///

	@Override
	public int getElements(View v) {
		int nelems = 1;
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
