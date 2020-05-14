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

import androidx.particles.Particle;

public interface ParticleModifier {

	/**
	 * Modifies the specific value of a particle given the current milliseconds.
	 *
	 * @param particle The particle to be modified.
	 * @param milliseconds The current number of milliseconds.
	 */
	void apply(Particle particle, long milliseconds);

}
