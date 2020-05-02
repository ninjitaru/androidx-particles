package androidx.particles.modifiers;

import androidx.particles.Particle;

public interface ParticleModifier {

	/**
	 * Modifies the specific value of a particle given the current milliseconds.
	 *
	 * @param particle
	 * @param miliseconds
	 */
	void apply(Particle particle, long miliseconds);

}
