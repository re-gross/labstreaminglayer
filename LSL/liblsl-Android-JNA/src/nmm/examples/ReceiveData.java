package nmm.examples;

import edu.ucsd.sccn.LSL;
import android.app.Activity;
//import android.content.Context;
import android.util.Log;
import android.widget.TextView;
//import android.net.wifi.WifiManager;
import android.os.Bundle;

public class ReceiveData extends Activity {

	private TextView tv;
	private LSL.StreamInfo[] results;
	private LSL.StreamInfo sinfo;
	private LSL.StreamInlet sinlet;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onCreate()");

//		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//		final WifiManager.MulticastLock lock;
//
//		if (wifi != null) {
//			Log.i(Helper.TAG, "wifi service acquired");
//			lock = wifi.createMulticastLock("createMulticastLock");
//			lock.acquire();
//		} else {
//			lock = null;
//		}
//
//		if (lock != null) {
//			Log.i(Helper.TAG, "multicast lock acquired");
//		}

		tv = new TextView(this);
		tv.setText( "Attempting to receive LSL data: ");
		setContentView(tv);
		
		new Thread(new Runnable() {
			public void run() {
				results = LSL.resolve_stream("type", "EEG"); // liblsl.so crashes
				sinlet = new LSL.StreamInlet(results[0]);
				String text;
				float[] sample = new float[sinfo.channel_count()];
				while(true) {
					try {
						sinlet.pull_sample(sample);				
					} catch (Exception e) {}					
	        		text = "";
	        		for (int k = 0; k < sample.length; k++)
	        			text += Double.toString(sample[k]) + ",";							
					final String final_text = text;
					runOnUiThread(new Runnable(){
						@Override
						public void run(){
							tv.setText("Received: " + final_text);
						}
					});
				}

			}
		}).start();
	}
	
	protected void onStop() {
		
		super.onStop();
		Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onStop()");
		sinfo.destroy();
		sinlet.close();
	}
}
