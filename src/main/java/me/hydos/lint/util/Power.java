package me.hydos.lint.util;

/**
 * Enum for the god-powers of this world.
 */
public enum Power {
	NONE,
	ALLOS, // light, creation, production, preservation
	MANOS; // darkness, destruction, consumption, corruption

	public boolean conflicts(Power other) {
		if (this == ALLOS) {
			return other == MANOS;
		} else if (this == MANOS) {
			return other == ALLOS;
		}

		return false;
	}
}
