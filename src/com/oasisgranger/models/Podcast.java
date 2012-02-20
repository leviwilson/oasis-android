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
	
	public Podcast(Parcel source) {
		title = source.readString();
		content = source.readString();
		link = source.readString();
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(link);
	}

	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator<Podcast> CREATOR = new Creator<Podcast>() {
		public Podcast createFromParcel(Parcel source) {
			return new Podcast(source);
		}
		
		public Podcast[] newArray(int size) {
			return new Podcast[size];
		}
	};
}
