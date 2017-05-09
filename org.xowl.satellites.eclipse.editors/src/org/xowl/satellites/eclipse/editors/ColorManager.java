/*******************************************************************************
 * Copyright (c) 2017 Association Cénotélie (cenotelie.fr)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package org.xowl.satellites.eclipse.editors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Manages the translation of RGB color into Eclipse color
 * 
 * @author Laurent Wouters
 */
public class ColorManager {
	/**
	 * The singleton instance
	 */
	private static final ColorManager INSTANCE = new ColorManager();

	/**
	 * Gets the color manager
	 * 
	 * @return The color manager
	 */
	public static ColorManager get() {
		return INSTANCE;
	}

	/**
	 * Initializes the color manager
	 */
	private ColorManager() {
		this.colorTableRGB = new HashMap<>();
		this.colorTableInt = new HashMap<>();
	}

	/**
	 * The map of known colors
	 */
	private final Map<RGB, Color> colorTableRGB;
	/**
	 * The map of known colors
	 */
	private final Map<Integer, Color> colorTableInt;

	/**
	 * Disposes of this resource
	 */
	public void dispose() {
		Iterator<Color> e = colorTableRGB.values().iterator();
		while (e.hasNext())
			((Color) e.next()).dispose();
		e = colorTableInt.values().iterator();
		while (e.hasNext())
			((Color) e.next()).dispose();
	}

	/**
	 * Gets a color for the specified rgb definition
	 * 
	 * @param rgb
	 *            An RGB definition
	 * @return The corresponding color
	 */
	public Color getColor(RGB rgb) {
		Color color = (Color) colorTableRGB.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTableRGB.put(rgb, color);
		}
		return color;
	}

	/**
	 * Gets a color for the specified rgb definition
	 * 
	 * @param rgb
	 *            An RGB definition
	 * @return The corresponding color
	 */
	public Color getColor(int rgb) {
		Color color = (Color) colorTableInt.get(rgb);
		if (color == null) {
			int red = ((rgb & 0x00FF0000) >> 16);
			int green = ((rgb & 0x0000FF00) >> 8);
			int blue = (rgb & 0x000000FF);
			color = new Color(Display.getCurrent(), red, green, blue);
			colorTableInt.put(rgb, color);
		}
		return color;
	}
}
