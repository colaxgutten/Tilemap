import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Tile {
	private String tileImageName;
	private int toolId=0;
	boolean solid;
	
	/**
	 * Creates a Tile with imageId and whether its solid or not
	 * @param tileImageName
	 * @param solid
	 */
	public Tile(String tileImageName, boolean solid){
		this.tileImageName=tileImageName;
		this.solid=solid;
	}
	
	public void setTileImageName(String name){
		tileImageName=name;
	}
	
	public String getTileImageId(){
		return tileImageName;
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
	
	@Override
	public String toString(){
		return ""+tileImageName +" "+ solid;
	}

	public static Tile getBasicTile() {
		return new Tile("illuminati.jpg",false);
	}
	
	public void onWalkOver() {}
}