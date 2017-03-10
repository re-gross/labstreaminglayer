package nmm.examples;

import edu.ucsd.sccn.LSL;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.net.wifi.WifiManager;
import android.os.Bundle;

public class SendData extends Activity {

	private TextView tv;
	private LSL.StreamInfo sinfo;
	private LSL.StreamOutlet soutlet;

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

		String text = "Platform: " + com.sun.jna.Platform.getOSType() + "\n";

		double mytime = LSL.local_clock();
		text += "The LSL clock reads: " + Double.toString(mytime) + "\n";

		sinfo = new LSL.StreamInfo("BioSemi", "EEG", 8, 100, LSL.ChannelFormat.float32, "myuid324457");

		text += "Creating an outlet..." + "\n";
		soutlet = new LSL.StreamOutlet(sinfo);

		sinfo = soutlet.info();
		text += "Unique ID: " + sinfo.uid() + "\n";
		text += "sessionID: " + sinfo.session_id() + "\n";

		text += "Sending data..." + "\n";

		tv.setText(text);
		setContentView(tv);

	    new Thread(new Runnable() {
	        public void run() {
	    		float[] sample = new float[8];
	    		for (int t = 0; t < 6000; t++) {
	    			for (int k = 0; k < 8; k++)
	    				sample[k] = (float) Math.random() * 50 - 25;
	    			soutlet.push_sample(sample);
	    			try {
	    				Thread.sleep(10);
	    			} catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		
	    		soutlet.close();
	    		
//	    		if (lock != null) {
//	    			lock.release();
//	    		}
	    		
	            tv.post(new Runnable() {
	                public void run() {
	    	    		tv.setText("Finished");
	    	    		tv.invalidate();	                    
	                }
	            });
	        }
	    }).start();
	}
	
	protected void onStop() {
		
		super.onStop();
		Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onStop()");
		sinfo.destroy();
		soutlet.close();
	}
}
