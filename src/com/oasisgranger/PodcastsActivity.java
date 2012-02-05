package com.oasisgranger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oasisgranger.helpers.ViewHelper;

public class PodcastsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_podcasts);
		
		getSupportActionBar().setTitle("Podcasts");

		ListView listView = ViewHelper.findFor(this, R.id.podcast_list);

		ArrayList<Podcast> podcasts = loadPodcasts();
		PodcastAdapter adapter = new PodcastAdapter(getBaseContext(),
				R.layout.podcast_item, podcasts);

		listView.setAdapter(adapter);
	}

	private ArrayList<Podcast> loadPodcasts() {
		ArrayList<Podcast> podcasts = new ArrayList<Podcast>();

		String url = getString(R.string.podcast_json_url);

		try {
			HttpResponse response = new DefaultHttpClient()
					.execute(new HttpGet(url));

			BufferedReader reader = new BufferedReader(new InputStreamReader( response.getEntity().getContent()));

			StringBuilder json = new StringBuilder();
			String line;

			while (null != (line = reader.readLine())) {
				json.append(line);
			}
			
			Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
				.create();
			
			Podcasts full = gson.fromJson(json.toString(), Podcasts.class);
			for(Podcast podcast : full.items) {
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
