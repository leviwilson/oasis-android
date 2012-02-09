package com.oasisgranger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.oasisgranger.di.OasisPodcastJsonUrl;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.models.PodcastsFeed;

public class OasisPodcasts {
	
	private final String oasisPodcastJson;

	@Inject
	public OasisPodcasts(@OasisPodcastJsonUrl final String oasisPodcastJson) {
		this.oasisPodcastJson = oasisPodcastJson;
	}

	public ArrayList<Podcast> load() {
		ArrayList<Podcast> podcasts = new ArrayList<Podcast>();

		try {
			HttpResponse response = new DefaultHttpClient()
					.execute(new HttpGet(oasisPodcastJson));

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder json = new StringBuilder();
			String line;

			while (null != (line = reader.readLine())) {
				json.append(line);
			}

			Gson gson = new GsonBuilder()
				.setDateFormat( "EEE, dd MMM yyyy hh:mm:ss Z").create();

			PodcastsFeed full = gson.fromJson(json.toString(), PodcastsFeed.class);
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
