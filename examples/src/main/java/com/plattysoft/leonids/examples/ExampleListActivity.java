package com.plattysoft.leonids.examples;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExampleListActivity extends ListActivity {

	private static class Sample {
		public String name;
		public Class<? extends Activity> activityClass;

		public Sample(String name, Class<? extends Activity> activityClass) {
			this.name = name;
			this.activityClass = activityClass;
		}
	}

	/**
	 * List of samples to show.
	 * Make sure to add the activity to the manifest, too.
	 */
	private Sample[] samples = {
			new Sample("One Shot Simple", OneShotSimpleExampleActivity.class),
			new Sample("One Shot Advanced", OneShotAdvancedExampleActivity.class),

			new Sample("Emitter Simple", EmitterSimpleExampleActivity.class),
			new Sample("Emitting on Background", EmitterBackgroundSimpleExampleActivity.class),
			new Sample("Emitter Intermediate", EmitterIntermediateExampleActivity.class),
			new Sample("Emitter Time Limited", EmitterTimeLimitedExampleActivity.class),
			new Sample("Emit with Gravity", EmitterWithGravityExampleActivity.class),

			new Sample("Follow Touch", FollowCursorExampleActivity.class),
			new Sample("Animated Particles", AnimatedParticlesExampleActivity.class),
			new Sample("Fireworks", FireworksExampleActivity.class),
			new Sample("Confetti [Rabbit and Eggs]", ConfettiExampleActivity.class),
			new Sample("Dust [Rabbit and Eggs]", DustExampleActivity.class),
			new Sample("Stars [Rabbit and Eggs]", StarsExampleActivity.class)
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] sampleList = new String[samples.length];
		int n = 0;
		for (Sample sample : samples) {
			sampleList[n++] = sample.name;
		}
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sampleList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (position >=0 && position < samples.length) {
			startActivity(new Intent(this, samples[position].activityClass));
		}
	}

}
