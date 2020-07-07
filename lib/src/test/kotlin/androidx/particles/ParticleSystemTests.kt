package androidx.particles

import android.app.Activity
import android.graphics.Bitmap
import android.view.ViewGroup
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk

class ParticleSystemTests : StringSpec({
    "empty Bitmap array constructor parameter exception" {
        // Create a mock ViewGroup
        val viewGroup: ViewGroup = mockk {
            every { getLocationInWindow(any()) } just Runs
            every { context } returns mockk {
                every { resources } returns mockk {
                    every { displayMetrics } returns mockk {
                        // Google Pixel as reference (411 × 731 dp)
                        // https://material.io/resources/devices/
                        xdpi = 411.0f
                    }
                }
            }
        }
        // Create a mock activity
        val activity: Activity = mockk {
            every { findViewById<ViewGroup>(any()) } returns viewGroup
        }

        // Test all three constructors
        shouldThrowExactly<IllegalArgumentException> {
            ParticleSystem(viewGroup, 100, arrayOf<Bitmap>(), 1000)
        } shouldHaveMessage EMPTY_BITMAP_ARRAY_MESSAGE
        shouldThrowExactly<IllegalArgumentException> {
            ParticleSystem(activity, 100, arrayOf<Bitmap>(), 1000)
        } shouldHaveMessage EMPTY_BITMAP_ARRAY_MESSAGE
        shouldThrowExactly<IllegalArgumentException> {
            ParticleSystem(activity, 100, arrayOf<Bitmap>(), 1000, android.R.id.content)
        } shouldHaveMessage EMPTY_BITMAP_ARRAY_MESSAGE
    }
}) {
    companion object {
        const val EMPTY_BITMAP_ARRAY_MESSAGE = "Bitmap array can not be empty"
    }
}
