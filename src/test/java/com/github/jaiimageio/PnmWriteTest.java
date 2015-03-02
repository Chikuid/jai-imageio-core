package com.github.jaiimageio;

import com.github.jaiimageio.plugins.pnm.PNMImageWriteParam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.junit.Test;

/**
 * Test PPM writing, ASCII and RAW.
 * @author peterhull90@gmail.com
 */
public class PnmWriteTest {

    private final BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);

    @Test
    public void writeRaw() throws Exception {
        File f = File.createTempFile("test-raw", ".ppm");
        write(f, true, image);
    }

    @Test
    public void writeAscii() throws Exception {
        File f = File.createTempFile("test-ascii", ".ppm");
        write(f, false, image);

    }

    private static void write(File f, boolean raw, BufferedImage image) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("ppm");
        assert (writers.hasNext());
        ImageWriter writer = writers.next();
        PNMImageWriteParam defaultWriteParam = (PNMImageWriteParam) writer.getDefaultWriteParam();
        defaultWriteParam.setRaw(raw);
        ImageOutputStream ios = ImageIO.createImageOutputStream(f);
        writer.setOutput(ios);
        writer.write(null, new IIOImage(image, null, null), defaultWriteParam);
        writer.dispose();
        ios.close();
    }
}