import java.awt.Point;
import java.io.File;
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
	
	public static TileMap loadFromFile(File file) {
		TileMap tileMap = new TileMap();
		
		if(file == null || !file.exists()) {
			return tileMap;
		}

		return tileMap;
	}
}