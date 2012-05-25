package com.oasisgranger.helpers;

import android.app.Activity;
import android.view.View;

public class ViewHelper {

	@SuppressWarnings("unchecked")
	public static <ViewType extends View> ViewType findFor(Activity activity, int id) {
		return (ViewType) activity.findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public static <ViewType extends View> ViewType findFor(View view, int id) {
		return (ViewType) view.findViewById(id);
	}

}
