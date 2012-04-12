package su.eqx.iggdrasil.halberd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Halberd extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        
//        final Context ctx = this;
        
        Button connectButton = (Button) findViewById(R.id.connect);
        connectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Context ctx = this;
//				Intent i = new Intent(ctx, PlayerActivity.class);
//				startActivity(i);
				
				startPlayer();
				
			}
		});
    }
    
    private void startPlayer() {
    	Intent intent = new Intent(this, PlayerActivity.class);
    	startActivity(intent);
    }
}