package Tiles;
import TileMapEngine.Player;

public class DoorTile extends Tile {
	boolean isOpen;
	
	public DoorTile(String[] params, String imageName, boolean isSolid) {
		super(params, imageName, true);
	}
	
	@Override
	public void onAction(Player player) {
		
	}
	
	@Override
	public void onWalkOver(Player player) {
		
	}
	
	@Override
	public void onTrigger(String event, Tile caller) {
		switch(event) {
		case "open":
			
		}
	}
}
