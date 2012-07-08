package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.findFor;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;
import com.oasisgranger.R.id;
import com.oasisgranger.R.layout;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.task.AsyncWorkWithCallback;
import com.oasisgranger.task.AsyncWorkWithCallback.Callback;
import com.oasisgranger.task.TaskRunner;

public class PodcastsActivity extends OasisActivity {

	@Inject OasisPodcasts oasisPodcasts;
	@Inject DialogFacade dialogFacade;
	@Inject TaskRunner taskRunner;
	
	ListView listView;
	PodcastData podcastData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_podcasts);

		setTitle("Podcasts");

		listView = findFor(this, R.id.podcast_list);
		listView.setOnItemClickListener(new OnPodcastClick());

		podcastData = getLastPodcastData();
		loadPodcasts();
	}

	@SuppressWarnings("deprecation")
    @Override
	protected Dialog onCreateDialog(int id) {
		return dialogFacade.createProgress(this, "Loading...");
	}
	
	@SuppressWarnings("deprecation")
    @Override
	public Object onRetainNonConfigurationInstance() {
		podcastData.getPodcastsWork().detach();
		return podcastData;
	}
	
	@SuppressWarnings("deprecation")
	private PodcastData getLastPodcastData() {
		PodcastData lastPodcastData = (PodcastData) getLastNonConfigurationInstance();
		if( null == lastPodcastData ) {
			lastPodcastData = new PodcastData();
		}
		return lastPodcastData;
	}

	private void loadPodcasts() {
		if (noPodcastsLoaded()) {
			taskRunner.run(podcastData.getPodcastsWork());
		} else {
			updatePodcastsList(podcastData.getPodcasts());
		}
	}

	private boolean noPodcastsLoaded() {
		return null == podcastData.getPodcasts();
	}

	private void updatePodcastsList(ArrayList<Podcast> podcasts) {
		PodcastAdapter adapter = new PodcastAdapter(getApplicationContext(), layout.podcast_item, podcasts);
		listView.setAdapter(adapter);
	}

	private final class LoadPodcastsWork extends AsyncWorkWithCallback<Void, ArrayList<Podcast>> {
		private LoadPodcastsWork(Callback<ArrayList<Podcast>> callback) {
			super(callback);
		}

		@Override
		public ArrayList<Podcast> doInBackground(Void... parameters) {
			return PodcastsActivity.this.oasisPodcasts.load();
		}
	}

	private final class PodcastsLoadedCallback implements Callback<ArrayList<Podcast>> {
		
		@Override
		public void onPreExecute() {
			dialogFacade.show(PodcastsActivity.this, id.progress_loading);
		}

		@Override
		public void onPostExecute(ArrayList<Podcast> podcasts) {
			podcastData.setPodcasts(podcasts);
			updatePodcastsList(podcasts);
			dialogFacade.dismiss(PodcastsActivity.this, id.progress_loading);
		}
	}

	private final class OnPodcastClick implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getApplicationContext(), PodcastDetails.class);
			intent.putExtra(Podcast.class.getName(), podcastAt(position));
			startActivity(intent);
		}

		private Podcast podcastAt(int position) {
			return (Podcast) listView.getItemAtPosition(position);
		}
	}
	
	private final class PodcastData {
		private LoadPodcastsWork podcastsWork;
		private ArrayList<Podcast> podcasts;
		
		public LoadPodcastsWork getPodcastsWork() {
			if( null == podcastsWork ) {
				podcastsWork = new LoadPodcastsWork(new PodcastsLoadedCallback());
			}
			return podcastsWork;
		}
		
		public ArrayList<Podcast> getPodcasts() {
			return podcasts;
		}

		public void setPodcasts(ArrayList<Podcast> podcasts) {
			this.podcasts = podcasts;
		}
	}

}
