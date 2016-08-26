import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileMap {
	public static final String DEFAULT_TILE = "illuminati.jpg";
	
	private Map<Point, Tile> grid = new HashMap<>();

	public void setTile(Point pos, Tile tile){
		grid.put(pos, tile);
	}

	public Tile getTile(Point pos) {
		if(!grid.containsKey(pos)) {
			return Tile.getBasicTile();
		}
		
		return grid.get(pos);
	}
	
	public int getSize(){
		return grid.size();
	}
	
	public void printTiles(){
		System.out.println("trying to print tiles");
		System.out.println("size of tileMap is: "+grid.size());
		for (Tile t : grid.values()){
			System.out.println(t);
		}
	}
	
	public void draw(Canvas canvas, Point pos, int tileSize, HashMap<String,Image> images) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		for(int i = pos.x; i < canvas.getWidth(); i += tileSize) {
			for(int j = pos.y; j < canvas.getHeight(); j += tileSize) {
				Image image;
				String imageName = getTile(new Point(i, j)).getTileImageId();
				
				if (images.get(imageName) == null){
					image = images.get(DEFAULT_TILE);
				} else {
					image = images.get(imageName);
				}
				
				if(imageName != null) {
					gc.drawImage(image, i, j);
				}
			}
		}
		/*
		
		Image imageById = null;
		for (int i = canvasXpos; i < canvasXpos + tilesToBePainted; i++) {
			for (int j = canvasYpos; j < canvasYpos + tilesToBePainted; j++) {

				String name = getTile(new Point(i, j)).getTileImageId();
				if (images.get(name)==null){
					imageById = images.get("illuminati.jpg");
				}
				else
					imageById=images.get(name);
				if (imageById != null) {
					if (showSolid.isSelected() && !currentLoadZone.getTileMap().getTile(new Point(i, j)).isSolid()) {
						gc.setGlobalAlpha(0.2);
					}
					gc.drawImage(imageById, (i - canvasXpos) * tileSize, (j - canvasYpos) * tileSize, tileSize,
							tileSize);
					gc.setGlobalAlpha(1);
				}
			}
		}*/
	}
	
	public static TileMap loadFromFile(File file) {
		TileMap tileMap = new TileMap();
		
		if(file == null || !file.exists()) {
			return tileMap;
		}

		return tileMap;
	}
	
	/**
	 * Lager en tekst ut av tilemappen for å lagre slik:
	 * xpos,ypos,tileImageId,solid 
	 * med mellomrom som skilletegn mellom hver tile som skal lagres.
	 * @return save tekst.
	 */
	public String toString(){
		String save ="";
		for (Entry<Point,Tile> entry : grid.entrySet()){
			Point p = entry.getKey();
			Tile t = entry.getValue();
			save+=p.x+","+p.y+","+t.getTileImageId()+",";
			if(t.isSolid())
				save+="t";
			else
				save+="f";
			save+=" ";
		}
		return save;
	}
}