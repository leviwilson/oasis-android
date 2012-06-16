package com.oasisgranger.media;

public class NullPlayerBinding extends PlayerBinding {

	public NullPlayerBinding() {
		super();
	}
	
	@Override
	public void play() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void stop() {
	}
	
	@Override
	public boolean isPlaying() {
		return false;
	}
	
}
