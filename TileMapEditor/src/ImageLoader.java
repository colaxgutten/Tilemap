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
	private HashMap<String,Image> tiles = new HashMap<String,Image>();
	private HashMap<String,Image> decorations = new HashMap<String,Image>();
	
	
	public void loadFolderTiles(String folderPath){
		final File folder = new File("src\\tiles");
		loadTiles(folder);
	}

	public void loadTiles(final File folder){
		System.out.println(folder.exists());
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				loadTiles(fileEntry);
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
					tiles.put(name,image);
				}
			}
		}
		loaded=true;
	}
	
	public void loadDecorations(String folderPath){
		File folder = new File(folderPath);
		
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				loadTiles(fileEntry);
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
					decorations.put(name,image);
				}
			}
		}
	}
	
	public HashMap<String,Image> getDecorations() {
		return decorations;
	}
	
	
	public HashMap<String,Image> getTiles(String folderPath){
		if (!loaded)
		loadFolderTiles(folderPath);
		return tiles;
	}
}
