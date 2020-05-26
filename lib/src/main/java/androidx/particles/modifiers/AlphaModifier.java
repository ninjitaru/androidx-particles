/*
 * Copyright (c) 2013-2018 Raul Portales  (@plattysoft) and contributors,
 *               2020      Thomas Orlando (@thomorl) and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.particles.modifiers;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.particles.Particle;

public class AlphaModifier implements ParticleModifier {

	private int mStartValue;
	private int mEndValue;
	private long mStartTime;
	private long mEndTime;
	private float mDuration;
	private float mValueIncrement;
	private Interpolator mInterpolator;

	public AlphaModifier(@IntRange(from=0, to=255) int startValue, @IntRange(from=0, to=255) int endValue,
						 long startMillis, long endMillis, @NonNull Interpolator interpolator) {
		mStartValue = startValue;
		mEndValue = endValue;
		mStartTime = startMillis;
		mEndTime = endMillis;
		mDuration = mEndTime - mStartTime;
		mValueIncrement = mEndValue - mStartValue;
		mInterpolator = interpolator;
	}
	
	public AlphaModifier(@IntRange(from=0, to=255) int startValue, @IntRange(from=0, to=255) int endValue,
						 long startMillis, long endMillis) {
		this(startValue, endValue, startMillis, endMillis, new LinearInterpolator());
	}

	public AlphaModifier(@FloatRange(from=0f, to=1f) float startValue, @FloatRange(from=0f, to=1f) float endValue,
						 long startMillis, long endMillis, @NonNull Interpolator interpolator) {
		this(floatCompToInt(startValue), floatCompToInt(endValue), startMillis, endMillis, interpolator);
	}

	public AlphaModifier(@FloatRange(from=0f, to=1f) float startValue, @FloatRange(from=0f, to=1f) float endValue,
						 long startMillis, long endMillis) {
		this(floatCompToInt(startValue), floatCompToInt(endValue), startMillis, endMillis);
	}

	// TODO Create tests for this function
	// Converts a float color component (0.0..1.0) to an int color component (0..255)
	private static int floatCompToInt(float c) {
		return (int) (255*c + 0.5f);
	}

	@Override
	public void apply(@NonNull Particle particle, long milliseconds) {
		if (milliseconds < mStartTime) {
			particle.mAlpha = mStartValue;
		}
		else if (milliseconds > mEndTime) {
			particle.mAlpha = mEndValue;
		}
		else {	
			float interpolatedValue = mInterpolator.getInterpolation((milliseconds- mStartTime)*1f/mDuration);
			int newAlphaValue = (int) (mStartValue + mValueIncrement*interpolatedValue);
			particle.mAlpha = newAlphaValue;
		}		
	}

}
