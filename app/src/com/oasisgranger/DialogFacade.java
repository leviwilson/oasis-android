package com.oasisgranger;

import android.app.Activity;
import android.app.ProgressDialog;

public interface DialogFacade {

	ProgressDialog createProgress(Activity activity, String string);
	void show(Activity activity, int id);
	void dismiss(Activity activity, int id);

}
