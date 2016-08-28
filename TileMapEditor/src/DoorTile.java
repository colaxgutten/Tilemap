
public class DoorTile extends Tile {
	String[] params;
	
	public DoorTile(String[] params, String imageName) {
		super(imageName, true);
		this.params = params;
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
