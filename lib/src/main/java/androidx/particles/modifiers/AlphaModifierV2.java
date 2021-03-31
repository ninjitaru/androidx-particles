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

public class AlphaModifierV2 implements ParticleModifier {

  private int mStartValue;
  private int mEndValue;
  private long mDuration;
  private float mValueIncrement;
  private Interpolator mInterpolator;

  public AlphaModifierV2(@IntRange(from=0, to=255) int startValue, @IntRange(from=0, to=255) int endValue,
                       long duration, @NonNull Interpolator interpolator) {
    mStartValue = startValue;
    mEndValue = endValue;
    mDuration = duration;
    mValueIncrement = mEndValue - mStartValue;
    mInterpolator = interpolator;
  }

  public AlphaModifierV2(@IntRange(from=0, to=255) int startValue, @IntRange(from=0, to=255) int endValue,
                       long duration) {
    this(startValue, endValue, duration, new LinearInterpolator());
  }

  public AlphaModifierV2(@FloatRange(from=0f, to=1f) float startValue, @FloatRange(from=0f, to=1f) float endValue,
                       long duration, @NonNull Interpolator interpolator) {
    this(floatCompToInt(startValue), floatCompToInt(endValue), duration, interpolator);
  }

  public AlphaModifierV2(@FloatRange(from=0f, to=1f) float startValue, @FloatRange(from=0f, to=1f) float endValue,
                       long duration) {
    this(floatCompToInt(startValue), floatCompToInt(endValue), duration);
  }

  // TODO Create tests for this function
  // Converts a float color component (0.0..1.0) to an int color component (0..255)
  private static int floatCompToInt(float c) {
    return (int) (255*c + 0.5f);
  }

  @Override
  public void apply(@NonNull Particle particle, long milliseconds) {
    long startTime = particle.timeToLive() - mDuration;
    long endTime = particle.timeToLive();
    if (milliseconds < startTime) {
      particle.mAlpha = mStartValue;
    }
    else if (milliseconds > endTime) {
      particle.mAlpha = mEndValue;
    }
    else {
      float interpolatedValue = mInterpolator.getInterpolation((milliseconds- startTime)*1f/mDuration);
      int newAlphaValue = (int) (mStartValue + mValueIncrement*interpolatedValue);
      particle.mAlpha = newAlphaValue;
    }
  }

}
