import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class TileMap {
	private Map<Point, Tile> grid = new HashMap<>();

	public void setTile(Point pos, Tile tile){
		grid.put(pos, tile);
	}

	public Tile getTile(Point pos) {
		return grid.get(pos);
	}
}