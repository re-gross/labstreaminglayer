package examples;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;
import edu.ucsd.sccn.LSL;

public class ReceiveStringMarkers extends Activity
{
	TextView tv;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		tv = new TextView(this);
		tv.setText( "Attempting to receive LSL markers: ");
		setContentView(tv);

		new Thread(new Runnable() {
			public void run() {

				LSL.StreamInfo[] results = LSL.resolve_stream("type", "Markers");
				LSL.StreamInlet in = new LSL.StreamInlet(results[0]);

				String[] sample = new String[1];
				while(true) {
					try {
						in.pull_sample(sample);
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
}


