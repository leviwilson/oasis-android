package com.oasisgranger;

import android.os.Bundle;
import android.widget.ListView;

import com.google.inject.Inject;
import com.oasisgranger.helpers.ViewHelper;

public class PodcastsActivity extends OasisFragmentActivity {
	
	@Inject
	OasisPodcasts oasisPodcasts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_podcasts);

		getSupportActionBar().setTitle("Podcasts");

		ListView listView = ViewHelper.findFor(this, R.id.podcast_list);

		PodcastAdapter adapter = new PodcastAdapter(getBaseContext(),
				R.layout.podcast_item, oasisPodcasts.load());

		listView.setAdapter(adapter);
	}

}
