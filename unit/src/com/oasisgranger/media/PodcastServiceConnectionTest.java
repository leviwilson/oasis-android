package com.oasisgranger.media;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.oasisgranger.test.OasisTestRunner;

@RunWith(OasisTestRunner.class)
public class PodcastServiceConnectionTest {

	@Mock
	PlayerBinding player;
	PodcastServiceConnection serviceConnection;

	@Before
	public void setUp() {
		serviceConnection = new PodcastServiceConnection();
	}

	@Test
	public void itInitiallyHasANullPlayer() {
		assertThat(serviceConnection.getPlayer(), is(NullPlayerBinding.class));
	}

	@Test
	public void itHasAPlayerWhenConnected() {
		serviceConnection.onServiceConnected(null, player);

		assertThat(serviceConnection.getPlayer(), sameInstance(player));
	}

	@Test
	public void itWillNotifyWhenConnected() {
		final TestOnConnectedListener listener = new TestOnConnectedListener();
		serviceConnection.setOnPlayerConnected(listener);

		serviceConnection.onServiceConnected(null, player);

		assertThat(listener.connectedPlayer(), sameInstance(player));
	}

	private final class TestOnConnectedListener extends OnPlayerConnectedListener {

		private PlayerBinding player;

		@Override
		public void onConnected(PlayerBinding player) {
			this.player = player;
		}

		public PlayerBinding connectedPlayer() {
			return this.player;
		}

	}

}
