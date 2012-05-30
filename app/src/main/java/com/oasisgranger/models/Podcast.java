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
	private String title;
	private String contentSnippet;
	private String link;
	private String mediaUrl;
	private MediaGroup[] mediaGroups;
	
	public Podcast() {
		this.publishedDate = new Date();
	}
	
	public Podcast(String title, Date date) {
		this.title = title;
		this.publishedDate = date;
	}
	
	public Podcast(Parcel source) {
		title = source.readString();
		contentSnippet = source.readString();
		link = source.readString();
		mediaUrl = source.readString();
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(contentSnippet);
		dest.writeString(link);
		dest.writeString(getMediaUrl());
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		contentSnippet = description;
	}

	public String getDescription() {
		return contentSnippet;
	}
	
	public String getMediaUrl() {
		if( null == mediaUrl ) {
			mediaUrl = mediaGroups[0].contents[0].url;
		}
		
		return mediaUrl;
	}
}
