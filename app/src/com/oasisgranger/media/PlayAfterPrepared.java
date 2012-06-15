package com.oasisgranger.media;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

public class PlayAfterPrepared implements OnPreparedListener {

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		mediaPlayer.start();
	}

}
