package org.rogatio.circlead.util;

import java.awt.Color;
import java.util.Random;

/**
 * The Class ColorUtil.
 */
public class ColorUtil {

	/**
	 * Creates the random color.
	 *
	 * @return the color
	 */
	public static Color createRandomColor() {
		Random randomGenerator = new Random();
		int red = randomGenerator.nextInt(256);
		int green = randomGenerator.nextInt(256);
		int blue = randomGenerator.nextInt(256);
		return new Color(red, green, blue);
	}

}
