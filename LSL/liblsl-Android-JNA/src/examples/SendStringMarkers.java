package examples;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;
import edu.ucsd.sccn.LSL;

public class SendStringMarkers extends Activity
{
	TextView tv;

	String markertypes[] = { "Test", "Blah", "Marker", "XXX", "Testtest", "Test-1-2-3" };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		tv = new TextView(this);
		tv.setText( "Attempting to send LSL markers: ");
		setContentView(tv);

		new Thread(new Runnable() {
			public void run() {
				java.util.Random rand = new java.util.Random();
				String[] sample = new String[1];
				LSL.StreamInfo info = new LSL.StreamInfo("MyEventStream", "Markers", 1, LSL.IRREGULAR_RATE, LSL.ChannelFormat.string , "myuniquesourceid23443");
				LSL.StreamOutlet outlet = new LSL.StreamOutlet(info);

				// send random marker strings
				while (true) {
					// wait for a random period of time
					double endtime = ((double)LSL.local_clock()) + (Math.abs(rand.nextInt()) % 1000) / 1000.0;
					while (((double)LSL.local_clock()) < endtime);
					// and choose the marker to send
					final String mrk = markertypes[Math.abs(rand.nextInt()) % markertypes.length];

					runOnUiThread(new Runnable(){
						@Override
						public void run(){
							tv.setText("Now sending: " + mrk);
						}
					});

					sample[0] = mrk;
					try{
					Thread.sleep(100);
					} catch (Exception ex) {}
					outlet.push_sample(sample);
				}
			}
		}).start();
    }
}

