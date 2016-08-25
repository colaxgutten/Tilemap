import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class LoadZone {
	private TileMap tileMap;
	private DecorationMap decMap;
	
	public void loadFromFile(String fileName) {
		File file = new File(fileName);
		
		try(FileReader fr = new FileReader(file)) {
			BufferedReader br = new BufferedReader(fr);
			
			tileMap = new TileMap();
			decMap = new DecorationMap();

			String tileLine = br.readLine();
			String decLine = br.readLine();
			
			for(String tileString : tileLine.split(" ")) {
				String[] tileArray = tileString.split(",");
				int posX = Integer.valueOf(tileArray[0]);
				int posY = Integer.valueOf(tileArray[1]);
				int tileImageId = Integer.valueOf(tileArray[2]);
				boolean solid = Boolean.valueOf(tileArray[3]);
				
				Tile tile = new Tile(tileImageId, solid);
				
				tileMap.setTile(new Point(posX, posY), tile);
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
	 * Lager en tekst ut av tilemappen for å lagre slik:
	 * xpos,ypos,tileImageId,solid 
	 * med mellomrom som skilletegn mellom hver tile som skal lagres.
	 * @return save tekst.
	 */
	public void saveToFile(String fileName) {
		File file = new File(fileName);
		
	}
}
