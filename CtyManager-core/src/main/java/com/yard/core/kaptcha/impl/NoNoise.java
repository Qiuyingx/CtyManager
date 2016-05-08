package com.yard.core.kaptcha.impl;

import java.awt.image.BufferedImage;

import com.yard.core.kaptcha.NoiseProducer;
import com.yard.core.kaptcha.util.Configurable;

/**
 * Imlemention of NoiseProducer that does nothing.
 * 
 * @author Yuxing Wang
 */
public class NoNoise extends Configurable implements NoiseProducer {
	/**
	 */
	public void makeNoise(BufferedImage image, float factorOne, float factorTwo, float factorThree, float factorFour) {
		// Do nothing.
	}
}
