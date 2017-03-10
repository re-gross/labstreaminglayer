package nmm.examples;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.os.Bundle;
import edu.ucsd.sccn.LSL;

public class ReceiveStringMarkers extends Activity
{
	private TextView tv;
	private LSL.StreamInfo[] results;
	private LSL.StreamInlet sinlet;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onCreate()");
        
		tv = new TextView(this);
		tv.setText( "Attempting to receive LSL markers: ");
		setContentView(tv);

		new Thread(new Runnable() {
			public void run() {

				results = LSL.resolve_stream("type", "Markers");
				sinlet = new LSL.StreamInlet(results[0]);

				String[] sample = new String[1];
				while(true) {
					try {
						sinlet.pull_sample(sample);
					} catch (Exception e) {
					}
					final String final_sample = sample[0];
					runOnUiThread(new Runnable(){
						@Override
						public void run(){
							tv.setText("Received: " + final_sample);
						}
					});
				}

			}
		}).start();
    }

	protected void onStop() {
		
		super.onStop();
		Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onStop()");
		results[0].destroy();
		sinlet.close();
	}
}


