package androidx.particles.modifiers;

import androidx.particles.Particle;

public interface ParticleModifier {

	/**
	 * Modifies the specific value of a particle given the current milliseconds.
	 *
	 * @param particle The particle to be modified.
	 * @param miliseconds The current number of milliseconds.
	 */
	void apply(Particle particle, long miliseconds);

}
