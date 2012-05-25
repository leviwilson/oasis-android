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

	public static void clickOn(Activity activity, int id) {
		activity.findViewById(id).performClick();
	}

	public static void clickOn(View view, int id) {
		view.findViewById(id).performClick();
	}

	public static ActivityClickHandler afterClicking(final Activity activity, final int id) {
		return new ActivityClickHandler(activity, id);
	}

}
