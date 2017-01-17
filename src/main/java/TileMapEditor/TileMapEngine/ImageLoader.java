package TileMapEditor.TileMapEngine;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

import javafx.scene.image.Image;

public class ImageLoader {
	boolean loaded = false;
	private HashMap<String,Image> tiles = new HashMap<String,Image>();
	private HashMap<String,Image> decorations = new HashMap<String,Image>();
	/*
	private static ImageLoader instance;
	
	public static ImageLoader getInstance () {
		if(instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}
	
	private ImageLoader() {}
	*/
	public void loadFolderTiles(String folderPath){
		//final File folder = new File("D:\\Users\\legen\\Documents\\git\\Tilemap\\TileMapEditor\\images\\tileImages");
		loadTiles(new File(folderPath));
	}

	public void loadTiles(final File folder){
		System.out.println(folder.exists());
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				loadTiles(fileEntry);
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
		loaded=true;
	}
	
	public void loadDecorations(String folderPath){
		File folder = new File(folderPath);
		
		for (final File fileEntry : folder.listFiles()){
			if (fileEntry.isDirectory()){
				loadTiles(fileEntry);
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
	}
	
	public HashMap<String,Image> getDecorations() {
		return decorations;
	}
	
	
	public HashMap<String,Image> getTiles(){
		return tiles;
	}
}
