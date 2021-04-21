/**
 * 
 */
package unittests1;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * 
 * test for imageWrite function
 * 
 * @author
 *
 */
public class ImageWriterTest {

	@Test
	public void test() {
		// create 500 x 800 resolution and net of 10x16 on
		// this in different color
		Color background = new Color(150, 70, 0);
		ImageWriter im = new ImageWriter("NetpictureTest", 800, 500);
		for (int i = 0; i < 800; ++i) {
			for (int j = 0; j < 500; ++j) {
				if (i % 50 != 0 && j % 50 != 0)
					im.writePixel(i, j, background);
				else
					im.writePixel(i, j, new Color(0, 0, 180));
			}
		}
		im.writeToImage();
	}

}
