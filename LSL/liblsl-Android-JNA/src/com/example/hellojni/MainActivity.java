package com.example.hellojni;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static final String TAG = "LOG_TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		File f = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "lsl_api.cfg");
		if (!f.exists())
			copyFile("lsl_api.cfg", f.toString(), this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);

		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, SendData.class));
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ReceiveData.class));
			}
		});
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
			Log.i(TAG, "lsl_api.cfg does not exist");
			throw new RuntimeException(e);
		}
	}
}
