package com.brijframework.client.device.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.media.MediaLocator;

public class DeviceMediaController {

	public static final File dir = new File("C:\\app_runs\\unlimits-resources\\resource\\sub_cat_images\\");
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
		File file = new File("D:\\a.mp4");
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
		makeVideo("file:\\" + file.getAbsolutePath(), imgLst);
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

}
