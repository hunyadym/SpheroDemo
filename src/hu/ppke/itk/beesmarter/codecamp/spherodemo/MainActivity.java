package hu.ppke.itk.beesmarter.codecamp.spherodemo;

import orbotix.robot.base.Robot;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.Sphero;
import orbotix.view.connection.SpheroConnectionView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	private SpheroConnectionView mSpheroConnectionView;
	private Sphero mSphero;
	private boolean mStarted;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Find Sphero Connection View from layout file
		mSpheroConnectionView = (SpheroConnectionView) findViewById(R.id.sphero_connection_view);

		// This event listener will notify you when these events occur, it is up
		// to you what you want to do during them
		ConnectionListener mConnectionListener = new ConnectionListener() {

			@Override
			// The method to run when a Sphero is connected
			public void onConnected(Robot sphero) {
				// Hides the Sphero Connection View
				mSpheroConnectionView.setVisibility(View.INVISIBLE);
				// Cache the Sphero so we can send commands to it later
				mSphero = (Sphero) sphero;
				// You can add commands to set up the ball here, these are some
				// examples

				// Set the back LED brightness to full
				mSphero.setBackLEDBrightness(0.0f);
				// Set the main LED color to blue at full brightness
				mSphero.setColor(0, 0, 255);

				// End examples
			}

			// The method to run when a connection fails
			@Override
			public void onConnectionFailed(Robot sphero) {
				// let the SpheroConnectionView handle or hide it and do
				// something here...
			}

			// Ran when a Sphero connection drops, such as when the battery runs
			// out or Sphero sleeps
			@Override
			public void onDisconnected(Robot sphero) {
				// Starts looking for robots
				mSpheroConnectionView.startDiscovery();
			}
		};
		// Add the listener to the Sphero Connection View
		mSpheroConnectionView.addConnectionListener(mConnectionListener);

		final SeekBar bearingBar = (SeekBar) findViewById(R.id.bearing);
		bearingBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (mStarted) {
					mSphero.drive(progress, 1);
					;
				} else {
					mSphero.rotate(progress);
					;
				}
			}
		});

		findViewById(R.id.buttonGreen).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mSphero.setColor(0, 255, 0);
					}
				});
		findViewById(R.id.buttonRed).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSphero.setColor(255, 0, 0);
			}
		});
		findViewById(R.id.buttonWhite).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mSphero.setColor(255, 255, 255);
					}
				});
		findViewById(R.id.tailOn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSphero.setBackLEDBrightness(1);
			}
		});
		findViewById(R.id.tailOff).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSphero.setBackLEDBrightness(0);
			}
		});
		findViewById(R.id.go).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSphero.drive(bearingBar.getProgress(), 1);
				mStarted = true;
			}
		});
		findViewById(R.id.stop).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSphero.stop();
				mStarted = false;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
