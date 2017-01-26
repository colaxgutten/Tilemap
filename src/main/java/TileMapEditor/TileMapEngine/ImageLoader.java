package TileMapEditor.TileMapEngine;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

import javafx.scene.image.Image;

public class ImageLoader {

	public static HashMap<String,Image> loadTiles(final String folderPath){
		final HashMap<String,Image> tiles = new HashMap<String,Image>();
		final File folder = new File(folderPath);
		
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				return loadTiles(fileEntry.getPath());
			} else {
				String name = "";
				if (fileEntry.getName().endsWith("jpg") || fileEntry.getName().endsWith("png")){ 
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
		
		return tiles;
	}
	
	public static HashMap<String,Image> loadDecorations(final String folderPath){
		final HashMap<String,Image> decorations = new HashMap<String,Image>();
		final File folder = new File(folderPath);
		
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				return loadDecorations(fileEntry.getPath());
			} else {
				String name = "";
				if (fileEntry.getName().endsWith("jpg") || fileEntry.getName().endsWith("png")){
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
		
		return decorations;
	}
}
