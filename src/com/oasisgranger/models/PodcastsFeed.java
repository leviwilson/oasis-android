package com.oasisgranger.models;


public class PodcastsFeed {
	public static class ResponseData {
		public static class Feed {
			public String feedUrl;
			public String title;
			public String link;
			public Podcast[] entries;
		}
		
		public Feed feed;
	}
	
	public ResponseData responseData;
}
