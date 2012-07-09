package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.enable;
import static com.oasisgranger.helpers.ViewHelper.findFor;
import static com.oasisgranger.helpers.ViewHelper.setTextFor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;

import android.widget.SeekBar;
import com.google.inject.Inject;
import com.oasisgranger.R.id;
import com.oasisgranger.R.layout;
import com.oasisgranger.media.OnInitialPlaybackListener;
import com.oasisgranger.media.OnPlayerConnectedListener;
import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.media.PodcastServiceConnector;
import com.oasisgranger.models.Podcast;

public class PodcastPlayerActivity extends OasisActivity {

	@Inject private PodcastServiceConnector serviceConnection;
	
	private Button playPauseButton;

	private Chronometer chronometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_podcast_player);
		setTitle("");
		
		chronometer = findFor(this, id.elapsed_time);
        final SeekBar seekBar = findFor(this, id.elapsed_time_seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                chronometer.setBase(SystemClock.elapsedRealtime() - i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

		setupButtons();
	}
	
	@Override
	protected void onStart() {
		serviceConnection.connectWith(thePodcast());
		super.onStart();
	}

	@Override
	public void onStop() {
		serviceConnection.disconnect();
		super.onStop();
	}

	private Podcast thePodcast() {
		return getIntent().<Podcast> getParcelableExtra(Podcast.class.getName());
	}

	private PlayerBinding getPlayer() {
		return serviceConnection.getPlayer();
	}

	private void setupButtons() {
		playPauseButton = findFor(this, id.play_or_pause);
		serviceConnection.setOnPlayerConnected(new OnPlayerConnected());
		
		findFor(this, id.stop).setOnClickListener(new OnStopListener());
	}

	private void updatePlayState() {
        final PlayerBinding player = getPlayer();
		
		if (player.isPlaying()) {
			playPauseButton.setText("Pause");
			startTimer();
		} else {
			playPauseButton.setText("Play");
			stopTimer();
		}
		
		setTotalTime(player);
	}

	private void startTimer() {
		chronometer.setBase(getPlayer().getElapsedRealTime());
		chronometer.start();
	}

	private void stopTimer() {
		chronometer.stop();
	}

	private void setTotalTime(final PlayerBinding player) {
		setTextFor(this, id.total_time, player.formatTotalTime("HH:mm:ss"));
        final SeekBar seekBar = findFor(this, id.elapsed_time_seek);
        seekBar.setMax(player.getTotalTime());
	}

	private final class OnPlayerConnected extends OnPlayerConnectedListener {
		@Override
		public void onConnected(PlayerBinding player) {
			player.setOnInitialPlaybackListener(new OnPlaybackStarted());
			playPauseButton.setOnClickListener(new OnPausePlayListener());
		}
	}

	private final class OnStopListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			getPlayer().stop();
			finish();
		}
	}

	private final class OnPausePlayListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			getPlayer().toggleAudio();
			updatePlayState();
		}
	}
	
	private final class OnPlaybackStarted extends OnInitialPlaybackListener {
		@Override
		public void onInitialPlayback(PlayerBinding player) {
			enable(PodcastPlayerActivity.this, id.play_or_pause);
			enable(PodcastPlayerActivity.this, id.stop);
			updatePlayState();
		}
	}
}
