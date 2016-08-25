import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import javafx.scene.image.Image;
import sun.applet.Main;

public class ImageLoader {
	boolean loaded = false;
	private HashMap<String,Image> images = new HashMap<String,Image>();
	
	public void loadFolderImages(String folderPath){
		final File folder = new File("src\\images");
		loadImages(folder);
	}

	public void loadImages(final File folder){
		System.out.println(folder.exists());
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				loadImages(fileEntry);
			} else {
				String name = "";
				if (fileEntry.getName().endsWith("jpg")){
					name=fileEntry.getName();
					Image image = null;
					try {
						image = new Image(fileEntry.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					images.put(name,image);
				}
			}
		}
		loaded=true;
	}
	public HashMap<String,Image> getImages(String folderPath){
		if (!loaded)
		loadFolderImages(folderPath);
		return images;
	}
}
