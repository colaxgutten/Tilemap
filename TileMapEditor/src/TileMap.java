import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
}