package TileMapEditor.Tiles;
import TileMapEditor.TileMapEngine.Decoration;
import TileMapEditor.TileMapEngine.Player;

import java.util.ArrayList;
import java.util.List;


public class Tile implements TileInterface {
	private String tileImageName;
	private String[] params;
	private int toolId=0;
	boolean solid;
	
	private List<Decoration> decorations;
	
	public Tile(String tileImageName, boolean solid) {
		this.tileImageName=tileImageName;
		this.solid=solid;
	}
	
	/**
	 * Creates a Tile with imageId and whether its solid or not
	 * @param tileImageName
	 * @param solid
	 */
	public Tile(String[] params, String tileImageName, boolean solid){
		this.params = params;
		this.tileImageName=tileImageName;
		this.solid=solid;
		decorations = new ArrayList<Decoration>();
	}
	
	public void setTileImageName(String name){
		tileImageName=name;
	}
	
	public String getTileImageId(){
		return tileImageName;
	}
	
	public List<Decoration> getDecorations() {
		return decorations;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public void setSolid(boolean solid){
		this.solid=solid;
	}
	
	public String toSaveString(){
		String s ="";
		s+=tileImageName+",";
		if (solid)
			s+="t,";
		else
			s+="f,";
		return s;
	}
	
	public String getDecorationSaveString() {
		String s = "";
		for(Decoration dec : decorations) {
			s += dec.toString() + " ";
		}
		return s;
	}
	
	public static Tile fromFileToTile(String savedTileString){
		String[] split = savedTileString.split(",");
		boolean solid =false;
		String imgId ="";
		if (split[0]!=null){
			imgId = split[0];
			if (split.length>1)
				solid = split[1].equals("t") ? true : false;
		}
		Tile tile = new Tile(imgId,solid);
		return tile;
	}
	
	public static Tile getTile(String type, String[] params, String imageName, boolean solid) {
		switch(type) {
		case "doorTile":
			return new DoorTile(params, imageName, solid);
		//case "chest":
		default:
			return new Tile(params, imageName, solid);
		}
	}
	
	public String getParamString() {
		return params != null ? String.join(";", params) : "";
	}
	
	@Override
	public String toString(){
		return ""+tileImageName +" "+ solid;
	}

	public static Tile getBasicTile() {
		String[] s = new String[1];
		Tile t = new Tile(s, "illuminati.jpg",false);
		return t;
	}
	@Override
	public void onWalkOver(Player player) {}
	@Override
	public void onAction(Player player) {}
	@Override
	public void onTrigger(String event, Tile caller) {}
}