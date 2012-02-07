package com.oasisgranger;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.widget.ListView;

import com.google.inject.Inject;
import com.oasisgranger.helpers.ViewHelper;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.task.TaskRunner;
import com.oasisgranger.task.WorkItem;

public class PodcastsActivity extends OasisFragmentActivity {

	@Inject
	private OasisPodcasts oasisPodcasts;

	@Inject
	private DialogFacade dialogFacade;

	@Inject
	TaskRunner taskRunner;

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_podcasts);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Podcasts");
		actionBar.setDisplayHomeAsUpEnabled(true);

		listView = ViewHelper.findFor(this, R.id.podcast_list);
		loadPodcasts();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.podcasts_menu, menu);
		return true;
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

	private void loadPodcasts() {
		final DialogInterface progressDialog = dialogFacade.showProgressFor(
				this, "Loading...");
		taskRunner.run(new LoadPodcastsWorkItem(progressDialog));
	}

	private final class LoadPodcastsWorkItem implements
			WorkItem<Void, ArrayList<Podcast>> {
		private final DialogInterface progressDialog;

		private LoadPodcastsWorkItem(DialogInterface progressDialog) {
			this.progressDialog = progressDialog;
		}

		public ArrayList<Podcast> doInBackground(Void... params) {
			return oasisPodcasts.load();
		}

		public void onPostExecute(ArrayList<Podcast> podcasts) {
			PodcastAdapter adapter = new PodcastAdapter(getBaseContext(),
					R.layout.podcast_item, podcasts);
			listView.setAdapter(adapter);
			progressDialog.dismiss();
		}
	}

}
