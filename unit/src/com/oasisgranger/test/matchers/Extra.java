package com.oasisgranger.test.matchers;

import android.os.Parcelable;

public class Extra {
	private final Parcelable extraValue;
	private final String extraName;

	public Extra(final String extraName, final Parcelable extra) {
		this.extraName = extraName;
		this.extraValue = extra;
	}

	public Extra(final Parcelable extra) {
		this(extra.getClass().getName(), extra);
	}

	public Parcelable getExtraValue() {
		return extraValue;
	}

	public String getExtraName() {
		return extraName;
	}

	@Override
	public String toString() {
		return String.format("%s = %s", extraName, extraValue);
	}
}