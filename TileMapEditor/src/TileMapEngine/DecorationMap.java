package TileMapEngine;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class DecorationMap {
	private Map<Point, Decoration> decorations = new HashMap<>();
	private Point selected;
	
	public void add(Point pos, Decoration dec) {
		decorations.put(pos, dec);
	}
	
	public Map<Point, Decoration> getAll() {
		return decorations;
	}
	
	public void draw(Canvas canvas, Point canvasPos, int tileSize, HashMap<String,Image> images) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		List<Map.Entry<Point, Decoration>> entries = new ArrayList<>(decorations.entrySet());
		
		Collections.sort(entries, new Comparator<Map.Entry<Point, Decoration>>() {
			@Override
			public int compare(Map.Entry<Point, Decoration> o1, Map.Entry<Point, Decoration> o2) {
				if (o1.getKey().getY()<o2.getKey().getY())
					return -1;
				if (o1.getKey().getY()>o2.getKey().getY())
					return 1;
				if (o1.getValue().getyAdjust()<o2.getValue().getyAdjust())
					return -1;
				if (o1.getValue().getyAdjust()>o2.getValue().getyAdjust())
					return 1;
				return 0;
			}
		});
		for(Map.Entry<Point, Decoration> entry : entries) {
			Decoration dec = entry.getValue();
			Point pos = entry.getKey();
			
			Image image = images.get(dec.getImageName());
			if(image != null) {
			double scalevalue= tileSize/48.0;
			double drawPosX = (pos.getX() - canvasPos.x + dec.getxAdjust()) * tileSize - image.getWidth()/2*scalevalue + (tileSize/2);
			double drawPosY = (pos.getY() - canvasPos.y + dec.getyAdjust()) * tileSize - image.getHeight()*scalevalue + tileSize;
			
			
				gc.drawImage(image, drawPosX, drawPosY, image.getWidth()*scalevalue, image.getHeight()*scalevalue);
				if(isSelected(pos)) {
					gc.setStroke(Color.BLUE);
					gc.setLineWidth(2);
					gc.strokeRect(drawPosX, drawPosY, image.getWidth()*scalevalue, image.getHeight()*scalevalue);
				}
			}
		}
	}
	
	public boolean selectDecorationAt(double x, double y, Point canvasPos, int tileSize, Map<String, Image> images) {
		Point newSelection = null;
		
		for(Map.Entry<Point, Decoration> entry : decorations.entrySet()) {
			Decoration dec = entry.getValue();
			Point pos = entry.getKey();
			
			Image image = images.get(dec.getImageName());
			double scalevalue= tileSize/48.0;
			double topLeftX = (pos.getX() - canvasPos.x) * tileSize - image.getWidth()/2*scalevalue + (tileSize/2);
			double topLeftY = (pos.getY() - canvasPos.y) * tileSize - image.getHeight()*scalevalue + tileSize;
			double width = image.getWidth();
			double height = image.getHeight();
			
			if(x >= topLeftX && x <= topLeftX + width && y >= topLeftY && y <= topLeftY + height) {
				if((newSelection == null || pos.getY() > newSelection.getY())) {
					newSelection = pos;
				}
			}
		}
		
		if(newSelection != null) {
			selected = newSelection;
			return true;
		}
		
		return false;
	}
	
	public Decoration getSelected() {
		return decorations.get(selected);
	}
	
	public boolean isSelected(Point pos) {
		return selected != null && selected.equals(pos);
	}
	
	public void deleteSelected() {
		if(selected != null) {
			decorations.remove(selected);
		}
	}
	
	public String toString() {
		String save = "";
		for (Decoration dec : decorations.values()){
			save += dec.toString() + " ";
		}
		return save;
	}
}
