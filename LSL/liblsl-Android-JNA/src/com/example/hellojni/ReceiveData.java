package com.example.hellojni;

import edu.ucsd.sccn.LSL;
import edu.ucsd.sccn.LSL.StreamInfo;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.net.wifi.WifiManager;
import android.os.Bundle;

public class ReceiveData extends Activity {

	private static final String TAG = "LOG_TAG";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG, "LSLTest.onCreate()");

//		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//		final WifiManager.MulticastLock lock;
//
//		if (wifi != null) {
//			Log.i(TAG, "wifi service acquired");
//			lock = wifi.createMulticastLock("createMulticastLock");
//			lock.acquire();
//		} else {
//			lock = null;
//		}
//
//		if (lock != null) {
//			Log.i(TAG, "multicast lock acquired");
//		}

		final TextView tv = new TextView(this);

		String text = "Platform: " + com.sun.jna.Platform.getOSType() + "\n";

		double mytime = LSL.local_clock();
		text += "The LSL clock reads: " + Double.toString(mytime) + "\n";

		text += "Resolving an EEG stream..." + "\n";
		LSL.StreamInfo[] results = LSL.resolve_stream("type", "EEG"); // liblsl.so crashes
//		
//		text += "Opening an inlet..." + "\n";
//		final LSL.StreamInlet inlet = new LSL.StreamInlet(results[0]);
//
//		try {
//			LSL.StreamInfo sinfo = inlet.info();
//			text += "Unique ID: " + sinfo.uid() + "\n";
//			text += "sessionID: " + sinfo.session_id() + "\n";
//		} catch (Exception e) {
//			text += "inlet info exception"  + "\n";
//		}
		tv.setText(text);
		setContentView(tv);
		tv.invalidate();
		
//		try {
//			LSL.StreamInfo sinfo = inlet.info();
//			text += "Unique ID: " + sinfo.uid() + "\n";
//			text += "sessionID: " + sinfo.session_id() + "\n";
//			float[] sample = new float[sinfo.channel_count()];
//        	while (true) {
//        		inlet.pull_sample(sample);
//        		text = "";
//        		for (int k = 0; k < sample.length; k++)
//        			text += Double.toString(sample[k]);
//	    		tv.setText(text);
//	    		tv.invalidate();
//        	}	
//		} catch (Exception e) {
//			inlet.close();
//			if (lock != null) {
//				lock.release();
//			}
//    		tv.setText("Exception");
//    		tv.invalidate();	      
//		}
	}	
}
