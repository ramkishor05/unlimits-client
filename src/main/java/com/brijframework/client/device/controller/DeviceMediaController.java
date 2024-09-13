package com.brijframework.client.device.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.media.MediaLocator;

import org.jcodec.codecs.h264.H264Encoder;
import org.jcodec.codecs.h264.encode.H264FixedRateControl;
import org.jcodec.codecs.h264.encode.RateControl;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.jcodec.scale.RgbToYuv420j;

public class DeviceMediaController {

	public static final File dir = new File("E:\\full_interview\\sapient_interview");
	public static final String[] extensions = new String[] { "webp" };

	public static final FilenameFilter imageFilter = new FilenameFilter() {
		@Override
		public boolean accept(final File dir, String name) {
			for (final String ext : extensions) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	// Main function
	public static void main(String[] args) throws IOException {
		File file = new File("D:\\a.avi");
		if (!file.exists()) {
			file.createNewFile();
		}
		Vector<String> imgLst = new Vector<>();
		if (dir.isDirectory()) {
			for (final File f : dir.listFiles()) {
				imgLst.add(f.getAbsolutePath());
			}
		}
		System.out.println(imgLst);
		//makeVideo("file:\\" + file.getAbsolutePath(), imgLst);
		create(imgLst, new FileOutputStream(file));
	}

	public static void makeVideo(String fileName, Vector<String> imgLst) throws MalformedURLException {
		JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
		MediaLocator oml=imageToMovie.createMediaLocator(fileName);
		if (oml == null) {
			System.err.println("Cannot build media locator from: " + fileName);
			System.exit(0);
		}
		int interval = 40;
		imageToMovie.doIt(720, 360, (1000 / interval), imgLst, oml);
	}
	
	public static void create(List<String> frames, OutputStream output) {
		RateControl control=new H264FixedRateControl(1);
        H264Encoder encoder=new H264Encoder(control);
        RgbToYuv420j transform=new RgbToYuv420j();
        try {
            for ( String s : frames ) {
                File f= new File(s);
                BufferedImage i= ImageIO.read(f);
                Picture yuv=Picture.create(i.getWidth(), i.getHeight(), ColorSpace.YUV420);
                transform.transform(AWTUtil.fromBufferedImageRGB(i), yuv);
                ByteBuffer buf= ByteBuffer.allocate(i.getWidth() * i.getHeight() * 3);
                ByteBuffer ff=encoder.encodePFrame( yuv, buf);
                WritableByteChannel channel = Channels.newChannel(output);
                channel.write(ff);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    

}
