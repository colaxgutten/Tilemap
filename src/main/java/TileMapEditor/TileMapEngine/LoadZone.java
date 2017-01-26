package TileMapEditor.TileMapEngine;
import java.awt.Point;
import java.io.*;
import java.util.HashMap;

import TileMapEditor.Tiles.Tile;
import TileMapEditor.Tiles.TileMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

//import javafx.jnlp.FileSaveService;

public class LoadZone {
//	private DecorationMap decMap = new DecorationMap(); Unused?

	/*
	 * Unused?
	public DecorationMap getDecMap() {
		return decMap;
	}

	public void setDecMap(DecorationMap decMap) {
		this.decMap = decMap;
	}
	*/
	
	public void loadFromFile(String fileName, TileMap tileMap) {
		File file = null;
		try {
			file = SaveFileHandler.getInstance().getFile(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try(FileReader fr = new FileReader(file)) {
			BufferedReader br = new BufferedReader(fr);
			
			tileMap = new TileMap();
//			decMap = new DecorationMap(); Unused?

			String tileLine = br.readLine();
			
			if(tileLine == null) {
				System.out.println("no tile line");
				return;
			}
			
			for(String tileString : tileLine.split(" ")) {
				String[] tileArray = tileString.split(",");
				int posX = Integer.valueOf(tileArray[0]);
				int posY = Integer.valueOf(tileArray[1]);
				String tileImageId = tileArray[2];
				String type = tileArray[4];
				String[] params = tileArray.length > 5 ? tileArray[5].split(";") : new String[0];
				
				boolean solid = false;
				if (tileArray[3].equals("t"))
					solid=true;
				
				Tile tile = Tile.getTile(type, params, tileImageId, solid);
				
				tileMap.setTile(new Point(posX, posY), tile);
			}
			
			String decLine = br.readLine();

			if(decLine == null) {
				System.out.println("no decoration line");
				return;
			}
			
			for(String decString : decLine.split(" ")) {
				String[] decArray = decString.split(",");
				int posX = Integer.valueOf(decArray[0]);
				int posY = Integer.valueOf(decArray[1]);
				double xAdjust = Double.valueOf(decArray[2]);
				double yAdjust = Double.valueOf(decArray[3]);
				
				Decoration dec = new Decoration(posX, posY, xAdjust, yAdjust, decArray[4]);
				
				tileMap.addDecoration(new Point(posX, posY), dec);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	public void saveToFile(String fileName, TileMap tileMap) {
		SaveFileHandler.getInstance().writeFile(fileName, tileMap);
	}
}
