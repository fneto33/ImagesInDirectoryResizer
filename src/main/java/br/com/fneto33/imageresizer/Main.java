package br.com.fneto33.imageresizer;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) {
		
		/**
		 * The args below should be modified before running the Main method:
		 */
		double percentageOfSizeReduction = 0.35; //Ex: 0.3 means resizing the images to 30 percent of its original size.
		String pathOriginDirectory = "C:\\Temp\\large-images";
		String pathDestinationDirectory = "C:\\Temp\\resized-images";
		

		/**
		 * Code start here:
		 */
		File originDir = new File(pathOriginDirectory);
		File destinationDir = new File(pathDestinationDirectory);
		
		if (!destinationDir.exists()) {
			destinationDir.mkdir();
		} else {
			int numberOfFilesInDestinationDir = destinationDir.listFiles().length;
			if (numberOfFilesInDestinationDir > 0) {
				System.out.println(String.format("Destination directory must be empty. %d files found. Exiting...", numberOfFilesInDestinationDir));
				System.exit(1);
			}
		}
		if (originDir.isDirectory()) {
			for (File imagem : originDir.listFiles()) {
				if (!imagem.isDirectory()) {
					createResizedCopy(imagem, destinationDir, percentageOfSizeReduction, true);
				}
			}
		} else {
			System.out.println(String.format("%s is not a directory. Exiting...", pathOriginDirectory));
		}

	}
	
	static void createResizedCopy(File originalImageFile, File destinationDirectory, double resizePercentage, boolean preserveAlpha) {
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(originalImageFile);
			int scaledWidth = (int)(originalImage.getWidth()*resizePercentage);
			int scaledHeight = (int)(originalImage.getHeight()*resizePercentage);
			int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
			BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
			Graphics2D g = scaledBI.createGraphics();
			if (preserveAlpha) {
				g.setComposite(AlphaComposite.Src);
			}
			g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
			g.dispose();
			ImageIO.write(scaledBI, "jpg", new File(destinationDirectory, originalImageFile.getName()));
			System.out.println(String.format("File %s successfully resized.", originalImageFile.getName()));
		} catch (IOException e) {
			System.out.println(String.format("Error while trying to read image: %s", originalImageFile.getAbsolutePath()));
			e.printStackTrace();
		}
	}

}
