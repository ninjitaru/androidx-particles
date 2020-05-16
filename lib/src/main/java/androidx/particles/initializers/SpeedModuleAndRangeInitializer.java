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
package androidx.particles.initializers;

import androidx.annotation.IntRange;
import androidx.particles.Particle;

import java.util.Random;

public class SpeedModuleAndRangeInitializer implements ParticleInitializer {

	private float mSpeedMin;
	private float mSpeedMax;
	private int mMinAngle;
	private int mMaxAngle;

	public SpeedModuleAndRangeInitializer(float speedMin, float speedMax,
										  @IntRange(from=0, to=360) int minAngle,
										  @IntRange(from=0, to=360) int maxAngle) {
		mSpeedMin = speedMin;
		mSpeedMax = speedMax;
		mMinAngle = minAngle;
		mMaxAngle = maxAngle;
		// Make sure the angles are in the [0-360) range
		while (mMinAngle < 0) {
			mMinAngle+=360;
		}
		while (mMaxAngle < 0) {
			mMaxAngle+=360;
		}
		// Also make sure that mMinAngle is the smaller
		if (mMinAngle > mMaxAngle) {
			int tmp = mMinAngle;
			mMinAngle = mMaxAngle;
			mMaxAngle = tmp;
		}
	}

	@Override
	public void initParticle(Particle p, Random r) {
		float speed = r.nextFloat()*(mSpeedMax-mSpeedMin) + mSpeedMin;
		int angle;
		if (mMaxAngle == mMinAngle) {
			angle = mMinAngle;
		}
		else {
			angle = r.nextInt(mMaxAngle - mMinAngle) + mMinAngle;
		}
		double angleInRads = Math.toRadians(angle);
		p.mSpeedX = (float) (speed * Math.cos(angleInRads));
		p.mSpeedY = (float) (speed * Math.sin(angleInRads));
		p.mInitialRotation = angle + 90;
	}

}
