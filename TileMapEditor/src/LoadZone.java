import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class LoadZone {
	private TileMap tileMap = new TileMap();
	private DecorationMap decMap = new DecorationMap();
	
	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public DecorationMap getDecMap() {
		return decMap;
	}

	public void setDecMap(DecorationMap decMap) {
		this.decMap = decMap;
	}
	
	public void draw(Canvas canvas, Point pos, int tileSize, boolean showSolid, HashMap<String,Image> tiles, HashMap<String, Image> decorations) {
		tileMap.draw(canvas, pos, tileSize, showSolid, tiles);
		decMap.draw(canvas, pos, tileSize, decorations);
	}

	public void loadFromFile(String fileName) {
		System.out.println("Denne blir kallt");
		File file = new File(fileName);
		
		try(FileReader fr = new FileReader(file)) {
			BufferedReader br = new BufferedReader(fr);
			
			tileMap = new TileMap();
			decMap = new DecorationMap();

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
				//String type = tileArray[4];
				//String[] params = tileArray[5].split(";");
				
				boolean solid = false;
				if (tileArray[3].equals("t"))
					solid=true;
				
				Tile tile = new Tile(tileImageId, solid);
				
				tileMap.setTile(new Point(posX, posY), tile);
			}
			
			String decLine = br.readLine();

			if(decLine == null) {
				System.out.println("no decoration line");
				return;
			}
			
			for(String decString : decLine.split(" ")) {
				String[] decArray = decString.split(",");
				double posX = Double.valueOf(decArray[0]);
				double posY = Double.valueOf(decArray[1]);
				
				Decoration dec = new Decoration(posX, posY, decArray[2]);
				
				decMap.add(dec);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lager en tekst ut av tillemappen for å lagre slik:
	 * xpos,ypos,tileImageId,solid 
	 * med mellomrom som skilletegn mellom hver tile som skal lagres.
	 * @return save tekst.
	 */
	public void saveToFile(String fileName) {
		File file = new File(fileName);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(tileMap.toString());
			bw.newLine();
			bw.write(decMap.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
