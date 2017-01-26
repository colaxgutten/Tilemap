package TileMapEditor.Tiles;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import TileMapEditor.TileMapEngine.Decoration;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TileMap {
	public static final String DEFAULT_TILE = "illuminati.jpg";
	
	private Map<Point, Tile> grid = new HashMap<>();
	private Point selected;
	private Tile selectedTile;
	private Decoration selectedDec;
	private int selectedDecIndex;

	public void setTile(Point pos, Tile tile){
		grid.put(pos, tile);
	}

	public Tile getTile(Point pos) {
		if(!grid.containsKey(pos)) {
			return Tile.getBasicTile();
		}
		
		return grid.get(pos);
	}
	
	public int getSize(){
		return grid.size();
	}
	
	public void addDecoration(Point pos, Decoration dec) {
		if(grid.get(pos) == null) {
			grid.put(pos, Tile.getBasicTile());
		}
		grid.get(pos).getDecorations().add(dec);
	}
	
	public void printTiles(){
		System.out.println("trying to print tiles");
		System.out.println("size of tileMap is: "+grid.size());
		for (Tile t : grid.values()){
			System.out.println(t);
		}
	}
	
	public boolean selectDecorationAt(double x, double y, Point canvasPos, int tileSize, Map<String, Image> images) {
		int newIndex = 0;
		selectedDec = null;
		selectedTile = null;
		selectedDecIndex = 0;
		
		for(Map.Entry<Point, Tile> entry : grid.entrySet()) {
			Tile tile = entry.getValue();
			Point pos = entry.getKey();
			for(int i = 0; i < tile.getDecorations().size(); ++i) {
				Decoration dec = tile.getDecorations().get(i);
				Image image = images.get(dec.getImageName());
				double scalevalue= tileSize/48.0;
				double topLeftX = (pos.getX() - canvasPos.x) * tileSize - image.getWidth()/2*scalevalue + (tileSize/2);
				double topLeftY = (pos.getY() - canvasPos.y) * tileSize - image.getHeight()*scalevalue + tileSize;
				double width = image.getWidth();
				double height = image.getHeight();
				
				if(x >= topLeftX && x <= topLeftX + width && y >= topLeftY && y <= topLeftY + height) {
					if(selectedDec == null) {
						selectedDec = dec;
						selectedTile = tile;
						selectedDecIndex = newIndex;
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public Decoration getSelectedDec() {
//		Tile t = grid.get(new Point(selectedDec.getxPos(), selectedDec.getyPos()));
		
//		if(t == null)
//			return null;
		
//		return t.getDecorations().get(selectedDecIndex);
		
		return selectedDec;
	}
	
	public boolean decIsSelected(Point pos, int index) {
		return selectedTile != null && selectedTile.equals(pos) && selectedDecIndex == index;
	}
	
	public void deleteSelectedDec() {
		if(selectedTile == null)
			return;
		
		if(selectedTile.getDecorations().size() <= selectedDecIndex)
			return;
			
		
		selectedTile.getDecorations().remove(selectedDecIndex);
		selectedTile = null;
	}
	
	public static TileMap loadFromFile(File file) {
		TileMap tileMap = new TileMap();
		
		if(file == null || !file.exists()) {
			return tileMap;
		}

		return tileMap;
	}
	
	public void selectTileAt(int x, int y) {
		selected = new Point(x, y);
	}
	
	public Tile getSelectedTile() {
		return selectedTile;
	}
	
	public void removeSelection() {
		selected = null;
	}
	
	public String decString() {
		String s = "";
		for(Map.Entry<Point, Tile> entry : grid.entrySet()) {
			s += entry.getValue().getDecorationSaveString();
		}
		return s;
	}
	
	/**
	 * Lager en tekst ut av tilemappen for � lagre slik:
	 * xpos,ypos,tileImageId,solid 
	 * med mellomrom som skilletegn mellom hver tile som skal lagres.
	 * @return save tekst.
	 */
	public String toString(){
		String save ="";
		for (Entry<Point,Tile> entry : grid.entrySet()){
			Point p = entry.getKey();
			Tile t = entry.getValue();
			save+=p.x+","+p.y+","+t.getTileImageId()+",";
			if(t.isSolid())
				save+="t";
			else
				save+="f";
			save+=",";
			save+=t.getClass().getName();
			save+=",";
			save+=t.getParamString();
			save+=" ";
		}
		return save;
	}
}