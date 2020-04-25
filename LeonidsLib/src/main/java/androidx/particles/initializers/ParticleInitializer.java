package androidx.particles.initializers;

import java.util.Random;

import androidx.particles.Particle;

public interface ParticleInitializer {

	void initParticle(Particle p, Random r);

}
