package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.findFor;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
	LoadPodcastsWork podcastsWork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_podcasts);

		setTitle("Podcasts");

		listView = findFor(this, R.id.podcast_list);
		listView.setOnItemClickListener(new OnPodcastClick());

		podcastsWork = getPodcastsWorkItem();
		if (null == podcastsWork) {
			loadPodcasts();
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		return dialogFacade.createProgress(this, "Loading...");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.podcasts_menu, menu);
		return true;
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		podcastsWork.detach();
		return podcastsWork;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.podcasts_refresh:
			loadPodcasts();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("deprecation")
	private LoadPodcastsWork getPodcastsWorkItem() {
		LoadPodcastsWork workItem = (LoadPodcastsWork) getLastNonConfigurationInstance();
		if( null != podcastsWork ) {
			workItem.attach(new PodcastsLoadedCallback());
		}
		
		return workItem;
	}

	private void loadPodcasts() {
		dialogFacade.show(this, id.progress_loading);
		taskRunner.run(podcastsWork = new LoadPodcastsWork(new PodcastsLoadedCallback()));
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
		}

		@Override
		public void onPostExecute(ArrayList<Podcast> result) {
			PodcastAdapter adapter = new PodcastAdapter(getApplicationContext(), layout.podcast_item, result);
			listView.setAdapter(adapter);
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

}
