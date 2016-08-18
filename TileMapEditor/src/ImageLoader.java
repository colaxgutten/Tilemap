import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javafx.scene.image.Image;
import sun.applet.Main;

public class ImageLoader {
	boolean loaded = false;
	private ArrayList<Image> images = new ArrayList<Image>();
	
	public void loadFolderImages(String folderPath){
		final File folder = new File("src\\images");
		loadImages(folder);
	}

	public void loadImages(final File folder){
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				loadImages(fileEntry);
			} else {
				if (fileEntry.getName().endsWith("jpg")){
					String path = fileEntry.getAbsolutePath();
					Image image = null;
					try {
						image = new Image(fileEntry.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(image.getHeight());
					images.add(image);
				}
			}
		}
		loaded=true;
	}
	public List<Image> getImages(String folderPath){
		if (!loaded)
		loadFolderImages(folderPath);
		return images;
	}
}
