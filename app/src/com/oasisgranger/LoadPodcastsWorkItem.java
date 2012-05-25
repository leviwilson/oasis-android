package com.oasisgranger;

import java.util.ArrayList;

import android.content.DialogInterface;

import com.oasisgranger.models.Podcast;
import com.oasisgranger.task.WorkItem;

final class LoadPodcastsWorkItem implements WorkItem<Void, ArrayList<Podcast>> {
	/**
	 * 
	 */
	private final PodcastsActivity podcastsActivity;
	private final DialogInterface progressDialog;

	LoadPodcastsWorkItem(PodcastsActivity podcastsActivity, DialogInterface progressDialog) {
		this.podcastsActivity = podcastsActivity;
		this.progressDialog = progressDialog;
	}

	public ArrayList<Podcast> doInBackground(Void... params) {
		return this.podcastsActivity.oasisPodcasts.load();
	}

	public void onPostExecute(ArrayList<Podcast> podcasts) {
		PodcastAdapter adapter = new PodcastAdapter(this.podcastsActivity.getBaseContext(),
				R.layout.podcast_item, podcasts);
		this.podcastsActivity.listView.setAdapter(adapter);
		progressDialog.dismiss();
	}
}