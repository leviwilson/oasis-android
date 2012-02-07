package com.oasisgranger.models;

import java.util.Date;

public class Podcast {
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
}
