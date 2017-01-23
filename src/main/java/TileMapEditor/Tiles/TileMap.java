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
	private Point selectedDecTile;
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
	
	public void draw(Canvas canvas, Point canvasPos, int tileSize, boolean showSolid, HashMap<String,Image> images, HashMap<String,Image> decImages) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		for(int i = canvasPos.x; i < canvas.getWidth() / tileSize + canvasPos.x; i++) {
			for(int j = canvasPos.y; j < canvas.getHeight() / tileSize + canvasPos.y; j++) {
				Image image;
				String imageName = getTile(new Point(i, j)).getTileImageId();
				
				if (images.get(imageName) == null || imageName == null){
					image = images.get(DEFAULT_TILE);
				} else {
					image = images.get(imageName);
				}
				
				if (showSolid && !getTile(new Point(i, j)).isSolid()) {
					gc.setGlobalAlpha(0.2);
				}
				gc.drawImage(image, (i - canvasPos.x) * tileSize, (j - canvasPos.y) * tileSize, tileSize, tileSize);
				gc.setGlobalAlpha(1);
				
				if(selected != null && 	selected.equals(new Point(i, j))) {
					gc.setLineWidth(2);
					gc.setStroke(Color.BLUE);
					gc.strokeRect((i - canvasPos.x) * tileSize, (j - canvasPos.y) * tileSize, tileSize, tileSize);
				}
			}
		}
		
		for(int i = canvasPos.x; i < canvas.getWidth() / tileSize + canvasPos.x; i++) {
			for(int j = canvasPos.y; j < canvas.getHeight() / tileSize + canvasPos.y; j++) {
				for(int k = 0; getTile(new Point(i, j)).getDecorations()!=null && k < getTile(new Point(i, j)).getDecorations().size(); ++k) {
			Decoration dec = getTile(new Point(i, j)).getDecorations().get(k);
			Image decImage = decImages.get(dec.getImageName());
			if(decImage != null) {
			double scalevalue= tileSize/48.0;
			double drawPosX = (i - canvasPos.x + dec.getxAdjust()) * tileSize - decImage.getWidth()/2*scalevalue + (tileSize/2);
			double drawPosY = (j - canvasPos.y + dec.getyAdjust()) * tileSize - decImage.getHeight()*scalevalue + tileSize;
			
			
				gc.drawImage(decImage, drawPosX, drawPosY, decImage.getWidth()*scalevalue, decImage.getHeight()*scalevalue);
				if(decIsSelected(new Point(i, j), k)) {
					gc.setStroke(Color.BLUE);
					gc.setLineWidth(2);
					gc.strokeRect(drawPosX, drawPosY, decImage.getWidth()*scalevalue, decImage.getHeight()*scalevalue);
				}
			}
		}
			}
			}
		
		/*
		
		Image imageById = null;
		for (int i = canvasXpos; i < canvasXpos + tilesToBePainted; i++) {
			for (int j = canvasYpos; j < canvasYpos + tilesToBePainted; j++) {

				String name = getTile(new Point(i, j)).getTileImageId();
				if (images.get(name)==null){
					imageById = images.get("illuminati.jpg");
				}
				else
					imageById=images.get(name);
				if (imageById != null) {
					if (showSolid.isSelected() && !currentLoadZone.getTileMap().getTile(new Point(i, j)).isSolid()) {
						gc.setGlobalAlpha(0.2);
					}
					gc.drawImage(imageById, (i - canvasXpos) * tileSize, (j - canvasYpos) * tileSize, tileSize,
							tileSize);
					gc.setGlobalAlpha(1);
				}
			}
		}*/
	}
	
	public boolean selectDecorationAt(double x, double y, Point canvasPos, int tileSize, Map<String, Image> images) {
		Point newSelection = null;
		int newIndex = 0;
		
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
					if((newSelection == null || pos.getY() > newSelection.getY())) {
						newSelection = pos;
						newIndex = i;
					}
				}
			}
		}
		
		if(newSelection != null) {
			selectedDecTile = newSelection;
			selectedDecIndex = newIndex;
			return true;
		}
		
		return false;
	}
	
	public Decoration getSelectedDec() {
		Tile t = grid.get(selectedDecTile);
		
		if(t == null)
			return null;
		
		return t.getDecorations().get(selectedDecIndex);
	}
	
	public boolean decIsSelected(Point pos, int index) {
		return selectedDecTile != null && selectedDecTile.equals(pos) && selectedDecIndex == index;
	}
	
	public void deleteSelectedDec() {
		if(selectedDecTile == null)
			return;
		
		if(grid.get(selectedDecTile).getDecorations().size() <= selectedDecIndex)
			return;
			
		
		grid.get(selectedDecTile).getDecorations().remove(selectedDecIndex);
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
	
	public Tile getSelected() {
		return getTile(selected);
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