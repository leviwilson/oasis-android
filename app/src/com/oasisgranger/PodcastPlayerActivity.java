package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.findFor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.inject.Inject;
import com.oasisgranger.R.id;
import com.oasisgranger.R.layout;
import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.media.PodcastServiceConnector;
import com.oasisgranger.models.Podcast;

public class PodcastPlayerActivity extends OasisActivity {

	@Inject private PodcastServiceConnector serviceConnection;
	private Button playPauseButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_podcast_player);
		setTitle("");
		
		serviceConnection.connectWith(thePodcast());
		
		playPauseButton = findFor(this, id.play_or_pause);
		playPauseButton.setOnClickListener(new OnPausePlayListener());
		if( getPlayer().isPlaying() ) {
			playPauseButton.setText("Pause");
		} else {
			playPauseButton.setText("Play");
		}
		
		findFor(this, id.stop).setOnClickListener(new OnStopListener());
	}
	
	@Override
	public void onStop() {
		serviceConnection.disconnect();
		super.onStop();
	}

	private Podcast thePodcast() {
		return getIntent().<Podcast>getParcelableExtra(Podcast.class.getName());
	}
	
	private PlayerBinding getPlayer() {
		return serviceConnection.getPlayer();
	}

	private final class OnStopListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			getPlayer().stop();
		}
	}

	private final class OnPausePlayListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (getPlayer().isPlaying()) {
				getPlayer().pause();
			} else {
				getPlayer().play();
			}
		}
	}
}
