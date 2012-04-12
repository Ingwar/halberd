package su.eqx.iggdrasil.halberd;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class PlayerService extends Service {
	
	public static final String MPD_HOST = "MPD_HOST";
	public static final String MPD_PORT = "MPD_PORT";
	private static final String PACK_PREFIX = "su.eqx.iggdrasil.halberd";
	public static final String PLAYLIST_CHANGED = PACK_PREFIX + ".PLAYLIST_CHANGED";
	public static final String CONTROLS_CHANGED = PACK_PREFIX + ".CONTROLS_CHANGED";
	public static final String SONG_CHANGED = PACK_PREFIX + ".SONG_CHANGED";
	public static final String ALL_CHANGED = PACK_PREFIX + ".ALL_CHANGED";
	private IBinder mBinder;
	private MPDHelper mMPD;
	
	
	public class PlayerBinder extends Binder {
		
		private Service mService;
		
		
		public void play() {
			
			try {
				mMPD.play();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void pause() {
			
			try {
				mMPD.pause();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void playNext() {
			mMPD.playNextSong();
		}
		
		public void playPrevious() {
			mMPD.playPreviuosSong();
			
		}
		
		public void seek(int time) {
			int id = 15;
			mMPD.seek(id, time);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Bundle extras = arg0.getExtras();
		String hostname = extras.getString(PlayerService.MPD_HOST);
		int port = extras.getInt(PlayerService.MPD_PORT);
		mBinder = new PlayerBinder();
		try {
			mMPD = new MPDHelper(hostname, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = new Intent(ALL_CHANGED);
		sendBroadcast(intent);
		return mBinder;
	}

}
