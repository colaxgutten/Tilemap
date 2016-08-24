import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Tile {
	private int tileImageId;
	private int toolId=0;
	boolean solid;
	
	/**
	 * Creates a Tile with imageId and whether its solid or not
	 * @param tileImageId
	 * @param solid
	 */
	public Tile(int tileImageId, boolean solid){
		this.tileImageId=tileImageId;
		this.solid=solid;
	}
	
	public void setTileImageId(int id){
		tileImageId=id;
	}
	
	public int getTileImageId(){
		return tileImageId;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public void setSolid(boolean solid){
		this.solid=solid;
	}
	
	public String toSaveString(){
		String s ="";
		s+=tileImageId+",";
		if (solid)
			s+="t,";
		else
			s+="f,";
		return s;
	}
	
	public static Tile fromFileToTile(String savedTileString){
		String[] split = savedTileString.split(",");
		boolean solid =false;
		int imgId =0;
		if (split[0]!=null){
			imgId = Integer.parseInt(split[0]);
			if (split.length>1)
				solid = split[1].equals("t") ? true : false;
		}
		Tile tile = new Tile(imgId,solid);
		return tile;
	}
	
	@Override
	public String toString(){
		return ""+tileImageId +" "+ solid;
	}

	public static Tile getBasicTile() {
		return new Tile(0,false);
	}
	
	public void onWalkOver() {}
}