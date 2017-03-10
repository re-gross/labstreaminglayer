package nmm.examples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nmm.examples.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onCreate()");
		
		// Look for optional LSL config file on mobile device, e.g. /mnt/sdcard/lsl_api.cfg
		// If it doesn't exist, look for one packaged in the app resources (res directory) and copy it
		// to the above location, i.e. /mnt/sdcard/lsl_api.cfg
		File f = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Helper.LSL_CONFIG_FILENAME);
		if (!f.exists())
			copyFile(Helper.LSL_CONFIG_FILENAME, f.toString(), this);
		else
			Log.i(Helper.TAG, f.toString() + " exists");
		
		setContentView(R.layout.activity_my);

		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//startActivity(new Intent(MainActivity.this, SendData.class));
				startActivity(new Intent(MainActivity.this, SendStringMarkers.class));
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//startActivity(new Intent(MainActivity.this, ReceiveData.class));
				startActivity(new Intent(MainActivity.this, ReceiveStringMarkers.class));
			}
		});
	}

	protected void onStop() {
		
		super.onStop();
		Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onStop()");
	}

	protected void onDestroy() {
		
		super.onDestroy();
		Log.i(Helper.TAG, this.getClass().getSimpleName() + ".onDestroy()");
	}
	
	private static void copyFile(String assetPath, String localPath,
			Context context) {
		try {
			InputStream in = context.getAssets().open(assetPath);
			FileOutputStream out = new FileOutputStream(localPath);
			int read;
			byte[] buffer = new byte[4096];
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}
			out.close();
			in.close();

		} catch (IOException e) {
			Log.i(Helper.TAG, Helper.LSL_CONFIG_FILENAME + " does not exist");
			throw new RuntimeException(e);
		}
	}
}
