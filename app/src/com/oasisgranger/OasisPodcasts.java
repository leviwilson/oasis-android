package com.oasisgranger;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.oasisgranger.di.OasisPodcastJsonUrl;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.models.PodcastsFeed;

public class OasisPodcasts {
	
	public static final String FEED_DATE_FORMAT = "EEE, dd MMM yyyy hh:mm:ss Z";

	private final String oasisPodcastJson;
	
	@Inject
	private final Requestor requestor;

	@Inject
	public OasisPodcasts(@OasisPodcastJsonUrl final String oasisPodcastJson, Requestor requestor) {
		this.oasisPodcastJson = oasisPodcastJson;
		this.requestor = requestor;
	}

	public ArrayList<Podcast> load() {
		ArrayList<Podcast> podcasts = new ArrayList<Podcast>();

		try {
			Gson gson = new GsonBuilder()
				.setDateFormat(FEED_DATE_FORMAT).create();
			
			String message = requestor.get(oasisPodcastJson).getMessage();

			PodcastsFeed full = gson.fromJson(message, PodcastsFeed.class);
			for (Podcast podcast : full.responseData.feed.entries) {
				podcasts.add(podcast);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return podcasts;
	}

}
