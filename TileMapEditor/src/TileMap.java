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

public class TileMap {
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
	
	public void printTiles(){
		System.out.println("trying to print tiles");
		System.out.println("size of tileMap is: "+grid.size());
		for (Tile t : grid.values()){
			System.out.println(t);
		}
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
	private String mapToSaveString(){
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
	
	public void saveFile(String fileName){
		File f = new File(fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mapToSaveString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFile(String fileName){
		String save;
		File file = new File(fileName);
		if (file.exists()) {

			try (FileReader fr = new FileReader(file)) {
				BufferedReader br = new BufferedReader(fr);
				Map<Point, Tile> tileMap = new HashMap<>();
				while ((save = br.readLine())!=null){
					String[] tiles = save.split(" ");
					for (String tile: tiles){
						String[] tileVariables=tile.split(",");
						Point p = new Point(Integer.parseInt(tileVariables[0]),Integer.parseInt(tileVariables[1]));
						Boolean solid =false;
						if (tileVariables[3].equals("f"))
							solid=true;
						Tile t = new Tile(Integer.parseInt(tileVariables[2]),solid);
						tileMap.put(p, t);
					}
				}
				grid=tileMap;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}