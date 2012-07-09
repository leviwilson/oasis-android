package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.clickOn;
import static com.oasisgranger.helpers.ViewHelper.findFor;
import static com.oasisgranger.helpers.ViewHelper.isEnabled;
import static com.oasisgranger.helpers.ViewHelper.textOf;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static com.xtremelabs.robolectric.Robolectric.shadowOf_;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import android.widget.SeekBar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;

import android.content.Intent;
import android.widget.Chronometer;

import com.oasisgranger.R.id;
import com.oasisgranger.media.OnInitialPlaybackListener;
import com.oasisgranger.media.OnPlayerConnectedListener;
import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.media.PodcastServiceConnector;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.oasisgranger.test.shadows.ShadowChronometer;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class PodcastPlayerActivityTest {

    @Mock
    PodcastServiceConnector serviceConnector;
    @Spy
    PlayerBindingStub player = new PlayerBindingStub();

    private PodcastPlayerActivity activity;
    private Podcast podcast;

    @Before
    public void setUp() {
        podcast = new Podcast();
        setupMocks();
    }

    @Test
    public void itClearsTheTitle() {
        startActivity();
        assertThat(activity.getTitle().toString(), is(""));
    }

    @Test
    public void controlsAreDisabledByDefault() {
        startActivity();
        assertThat(isEnabled(activity, id.play_or_pause), is(false));
        assertThat(isEnabled(activity, id.stop), is(false));
    }

    @Test
    public void itCaresAboutPlaybackAfterConnecting() {
        startActivity();
        verify(player).setOnInitialPlaybackListener((OnInitialPlaybackListener) anyObject());
    }

    @Test
    public void controlsAreEnabledAfterPlaybackStarts() {
        startActivity();
        playbackHasStarted();

        assertThat(isEnabled(activity, id.play_or_pause), is(true));
        assertThat(isEnabled(activity, id.stop), is(true));
    }

    @Test
    public void itInitiallyPlaysThePodcast() {
        startActivity();
        verify(serviceConnector).connectWith(podcast);
    }

    @Test
    public void itDisconnectsWhenItDoesNotCareAnymore() {
        startActivity();

        activity.onStop();
        verify(serviceConnector).disconnect();
    }

    @Test
    public void itCanStopThePodcast() {
        startActivity();
        clickOn(activity, id.stop);
        verify(player).stop();
    }

    @Test
    public void thereIsNoReasonToStickAroundIfStopped() {
        startActivity();
        clickOn(activity, id.stop);
        assertThat(activity.isFinishing(), is(true));
    }

    @Test
    public void pauseIsDisplayedIfPlaying() {
        appearAsPlaying();
        startActivity();
        playbackHasStarted();

        assertThat(textOf(activity, id.play_or_pause), is("Pause"));
    }

    @Test
    public void playIsDisplayedIfNotPlaying() {
        appearAsPaused();
        startActivity();
        playbackHasStarted();

        assertThat(textOf(activity, id.play_or_pause), is("Play"));
    }

    @Test
    public void weCanToggleTheAudioState() {
        appearAsPlaying();
        startActivity();

        clickOn(activity, id.play_or_pause);

        verify(player).toggleAudio();
    }

    @Test
    public void audioStateIsCorrectWhenToggling() {
        startActivity();

        clickOn(activity, id.play_or_pause);
        assertThat(textOf(activity, id.play_or_pause), is("Pause"));

        clickOn(activity, id.play_or_pause);
        assertThat(textOf(activity, id.play_or_pause), is("Play"));
    }

    @Test
    public void itDisplaysTheTotalPodcastTime() {
        player.setFormattedTotalTime("01:03:23");
        startActivity();
        playbackHasStarted();

        assertThat(textOf(activity, id.total_time), is("01:03:23"));
    }

    @Test
    public void theSeekBarCanAtMaxBeSetToTheTotalTime() {
        int totalTimeMillis = 123456;
        player.setTotalTimeMillis(totalTimeMillis);
        startActivity();
        playbackHasStarted();

        final SeekBar seekBar = findFor(activity, id.elapsed_time_seek);
        assertThat(seekBar.getMax(), is(totalTimeMillis));
    }

    @Test
    public void itUpdatesTheElapsedTimeWhileSeeking() {
        startActivity();
        playbackHasStarted();

        seekTo(112233);

        assertThat(elapsedChronometer().initialElapsed(), is(elapsed(112233L)));
    }

    @Test
    public void itFormatsTheTotalTimeInHoursMinutesAndSeconds() {
        startActivity();
        playbackHasStarted();
        verify(player).formatTotalTime("HH:mm:ss");
    }

    @Test
    public void theElapsedTimerDoesNotStartAutomatically() {
        startActivity();
        assertThat(elapsedChronometer().wasStarted(), is(not(true)));
    }

    @Test
    public void itElapsesTimeAsThePodcastStartsPlaying() {
        startActivity();
        appearAsPlaying();
        playbackHasStarted();
        assertThat(elapsedChronometer().wasStarted(), is(true));
    }

    @Test
    public void theElapsedTimeOfThePodcastDrivesTheBaseTimer() {
        player.setElapsedRealTime(TimeUnit.SECONDS.toMillis(3));
        startActivity();
        appearAsPlaying();
        playbackHasStarted();

        assertThat(elapsedChronometer().initialElapsed(), is(3000L));
    }

    @Test
    public void itStopsElapsingWhenPaused() {
        startActivity();
        playbackHasStarted();

        clickOn(activity, id.play_or_pause);

        assertThat(elapsedChronometer().wasStopped(), is(true));
    }

    @Test
    public void itStartsElapsingAgainWhenResuming() {
        startActivity();
        playbackHasStarted();

        clickOn(activity, id.play_or_pause); // play
        clickOn(activity, id.play_or_pause); // pause
        clickOn(activity, id.play_or_pause); // play again

        assertThat(elapsedChronometer().wasRestarted(), is(true));

    }

    private void startActivity() {
        activity = new PodcastPlayerActivity();

        final Intent intent = new Intent();
        intent.putExtra(Podcast.class.getName(), podcast);
        activity.setIntent(intent);

        shadowOf(activity).create();
        beConnected();
    }

    private void setupMocks() {
        OasisGrangerApp application = (OasisGrangerApp) Robolectric.application;
        application.configure()
                .withBinding(PlayerBinding.class, player)
                .withBinding(PodcastServiceConnector.class, serviceConnector);

        when(serviceConnector.getPlayer()).thenReturn(player);
    }

    private void appearAsPlaying() {
        player.isPlaying = true;
    }

    private void appearAsPaused() {
        player.isPlaying = false;
    }

    private void beConnected() {
        ArgumentCaptor<OnPlayerConnectedListener> listenerArg = ArgumentCaptor.forClass(OnPlayerConnectedListener.class);
        verify(serviceConnector).setOnPlayerConnected(listenerArg.capture());
        listenerArg.getValue().onConnected(player);
    }

    private void playbackHasStarted() {
        ArgumentCaptor<OnInitialPlaybackListener> listenerArg = ArgumentCaptor.forClass(OnInitialPlaybackListener.class);
        verify(player).setOnInitialPlaybackListener(listenerArg.capture());

        listenerArg.getValue().onInitialPlayback(player);
    }

    private ShadowChronometer elapsedChronometer() {
        final Chronometer elapsedTimer = findFor(activity, id.elapsed_time);
        return shadowOf_(elapsedTimer);
    }

    private void seekTo(int position) {
        final SeekBar seekBar = findFor(activity, id.elapsed_time_seek);
        final SeekBar.OnSeekBarChangeListener listener = shadowOf(seekBar).getOnSeekBarChangeListener();
        listener.onProgressChanged(seekBar, position, true);
    }

    private long elapsed(long elapsedTime) {
        return -1 * elapsedTime;
    }

    private class PlayerBindingStub extends PlayerBinding {
        private boolean isPlaying;
        private long elapsedTimeMillis;
        private String formattedTotalTime;
        private int totalTimeMillis;

        @Override
        public boolean isPlaying() {
            return isPlaying;
        }

        @Override
        public void play() {
            isPlaying = true;
        }

        @Override
        public void stop() {
            isPlaying = false;
        }

        @Override
        public void pause() {
            isPlaying = false;
        }

        @Override
        public long getElapsedRealTime() {
            return elapsedTimeMillis;
        }

        @Override
        public int getTotalTime() {
            return totalTimeMillis;
        }

        @Override
        public String formatTotalTime(final String formatString) {
            return formattedTotalTime;
        }

        @Override
        public void seekTo(int millis) {
        }

        public void setFormattedTotalTime(String totalTime) {
            this.formattedTotalTime = totalTime;
        }

        public void setTotalTimeMillis(int totalTimeMillis) {
            this.totalTimeMillis = totalTimeMillis;
        }

        public void setElapsedRealTime(long elapsedTimeMillis) {
            this.elapsedTimeMillis = elapsedTimeMillis;
        }
    }

}
