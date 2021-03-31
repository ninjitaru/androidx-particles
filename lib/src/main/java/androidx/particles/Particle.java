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
package androidx.particles;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.particles.modifiers.ParticleModifier;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * A single 2D particle.
 */
public class Particle {

	protected Bitmap mImage;
	
	@Px
	public float mCurrentX;
	@Px
	public float mCurrentY;
	
	public float mScale = 1f;
	public int mAlpha = 255;
	
	public float mInitialRotation = 0f;
	
	public float mRotationSpeed = 0f;
	
	public float mSpeedX = 0f;
	public float mSpeedY = 0f;

	public float mAccelerationX;
	public float mAccelerationY;

	private Matrix mMatrix;
	private Paint mPaint;

	@Px
	private float mInitialX;
	@Px
	private float mInitialY;

	private float mRotation;

	private long mTimeToLive;

	protected long mStartingMillisecond;

	@Px
	private int mBitmapHalfWidth;
	@Px
	private int mBitmapHalfHeight;

	private boolean mConfigurated = false;

	private List<ParticleModifier> mModifiers;

	public boolean isConfigurated() { return mConfigurated; }
	public long timeToLive() { return mTimeToLive; }

	protected Particle() {		
		mMatrix = new Matrix();
		mPaint = new Paint();
	}
	
	public Particle(Bitmap bitmap) {
		this();
		mImage = bitmap;
	}

	public void init() {
		mScale = 1;
		mAlpha = 255;
		mConfigurated = false;
	}
	
	public void configure(long timeToLive, @Px float emitterX, @Px float emitterY) {
		if (mConfigurated) {
			return;
		}
		mBitmapHalfWidth = mImage.getWidth()/2;
		mBitmapHalfHeight = mImage.getHeight()/2;
		
		mInitialX = emitterX - mBitmapHalfWidth;
		mInitialY = emitterY - mBitmapHalfHeight;
		mCurrentX = mInitialX;
		mCurrentY = mInitialY;
		
		mTimeToLive = timeToLive;
		mConfigurated = true;
	}

	public boolean update(long milliseconds) {
		long realMilliseconds = milliseconds - mStartingMillisecond;
		if (realMilliseconds > mTimeToLive) {
			return false;
		}
		mCurrentX = mInitialX+mSpeedX*realMilliseconds+mAccelerationX*realMilliseconds*realMilliseconds;
		mCurrentY = mInitialY+mSpeedY*realMilliseconds+mAccelerationY*realMilliseconds*realMilliseconds;
		mRotation = mInitialRotation + mRotationSpeed*realMilliseconds/1000;
		for (int i=0; i<mModifiers.size(); i++) {
			mModifiers.get(i).apply(this, realMilliseconds);
		}
		return true;
	}
	
	public void draw(@NonNull Canvas c) {
		mMatrix.reset();
		mMatrix.postRotate(mRotation, mBitmapHalfWidth, mBitmapHalfHeight);
		mMatrix.postScale(mScale, mScale, mBitmapHalfWidth, mBitmapHalfHeight);
		mMatrix.postTranslate(mCurrentX, mCurrentY);
		mPaint.setAlpha(mAlpha);		
		c.drawBitmap(mImage, mMatrix, mPaint);
	}

	public Particle activate(long startingMillisecond, @NonNull List<ParticleModifier> modifiers) {
		mStartingMillisecond = startingMillisecond;
		// We do store a reference to the list, there is no need to copy, since the modifiers do not care about states
		mModifiers = modifiers;
		return this;
	}
}
