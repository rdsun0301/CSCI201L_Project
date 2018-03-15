package utility;

import java.security.SecureRandom;
import java.math.BigInteger;

public final class RandomStringGenerator {
	private SecureRandom random = new SecureRandom();

	public String generate() {
		return new BigInteger(130, random).toString(32);
	}
}