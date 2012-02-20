package com.oasisgranger.models;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Podcast implements Parcelable {
	public static class MediaGroup {
		public static class Enclosure {
			public String url;
			public String type;
		}
		
		public Enclosure[] contents;
	}

	public Date publishedDate;
	public String title;
	public String content;
	public String link;
	public MediaGroup[] mediaGroups;
	
	public Podcast() {
		
	}
	
	public Podcast(String title, Date date) {
		this.title = title;
		this.publishedDate = date;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(link);
	}
}
