package nmm.examples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Dummy extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TextView tv = new TextView(this);

		String text = "Some Text.";
		tv.setText(text);
		setContentView(tv);
		tv.invalidate();
	}
}
