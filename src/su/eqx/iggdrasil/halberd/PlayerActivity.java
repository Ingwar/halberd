package su.eqx.iggdrasil.halberd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import su.eqx.iggdrasil.halberd.PlayerService.PlayerBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayerActivity extends Activity {
	

//	private boolean isPaused;
	private SeekBar mplaybackSeekBar;
	private PlayerBinder mBinder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_layout);
		
		final ToggleButton playToggleButton = (ToggleButton) findViewById(R.id.playing_toggle);
		playToggleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (playToggleButton.isChecked()) {
					mBinder.play();
				} else {
					mBinder.pause();
				}
				
			}
		});
		
		ImageButton nextSongButton = (ImageButton) findViewById(R.id.next_song);
		nextSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mBinder.playNext();
			}
		});
		
		ImageButton prevSongButton = (ImageButton) findViewById(R.id.prev_song);
		prevSongButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mBinder.playPrevious();
			}
		});
		
		mplaybackSeekBar = (SeekBar) findViewById(R.id.playback_progressbar);
		mplaybackSeekBar.setMax(100);
		mplaybackSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mBinder.seek(progress);
				
			}
		});
		
		ListView playlist = (ListView) findViewById(R.id.playlist);
		
		List<Map<String,String>> test_list = new ArrayList<Map<String, String>>();
		String[] artists = {"art1", "art2", "art3", "art4", "art5"};
		String[] albums = {"alb1", "alb2", "alb3", "alb4", "alb5"};
		String[] songs = {"song1", "song2", "song3", "song4", "song5"};
		
		
		
		for (int i = 0; i < 5; i++) {
			test_list.add(new HashMap<String, String>());
			
			test_list.get(i).put("ALB", albums[i]);
			test_list.get(i).put("ART", artists[i]);
			test_list.get(i).put("SONG", songs[i]);
		}
	
		
		ListAdapter adapter = new SimpleAdapter(this, test_list, R.layout.playlist_item, new String[] {"ALB", "ART", "SONG"}, new int[] {R.id.playlist_album, R.id.playlist_artist, R.id.playlist_song_title});
		playlist.setAdapter(adapter);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(this, PlayerService.class);
		Bundle extras = new Bundle();
		extras.putString(PlayerService.MPD_HOST, "10.0.2.2");
		extras.putInt(PlayerService.MPD_PORT, 6600);
		intent.putExtras(extras);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		IntentFilter broadcastFilter = new IntentFilter();
		broadcastFilter.addAction(PlayerService.CONTROLS_CHANGED);
		broadcastFilter.addAction(PlayerService.PLAYLIST_CHANGED);
		broadcastFilter.addAction(PlayerService.SONG_CHANGED);
		
		registerReceiver(mReceiver, broadcastFilter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		unregisterReceiver(mReceiver);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unbindService(mConnection);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinder = (PlayerBinder) service;
		}
	};
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
	};

	
}
