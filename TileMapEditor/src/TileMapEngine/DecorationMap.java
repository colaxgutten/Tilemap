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
	private List<Decoration> decorations = new ArrayList<>();
	
	public void add(Decoration dec) {
		decorations.add(dec);
	}
	
	public List<Decoration> getAll() {
		return decorations;
	}
	
	public void draw(Canvas canvas, Point pos, int tileSize, HashMap<String,Image> images) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Collections.sort(decorations, new Comparator<Decoration>() {
			@Override
			public int compare(Decoration o1, Decoration o2) {
				if (o1.getyPos()<o2.getyPos())
					return -1;
				if (o1.getyPos()>o2.getyPos())
					return 1;
				return 0;
			}
		});
		for(Decoration dec : decorations) {
			Image image = images.get(dec.getImageName());
			if(image != null) {
			double scalevalue= tileSize/48.0;
			double drawPosX = (dec.getxPos() - pos.x) * tileSize - image.getWidth()/2*scalevalue + (tileSize/2);
			double drawPosY = (dec.getyPos() - pos.y) * tileSize - image.getHeight()*scalevalue + tileSize;
			
			
				gc.drawImage(image, drawPosX, drawPosY, image.getWidth()*scalevalue, image.getHeight()*scalevalue);
				if(dec.isSelected()) {
					gc.setStroke(Color.BLUE);
					gc.setLineWidth(2);
					gc.strokeRect(drawPosX, drawPosY, image.getWidth()*scalevalue, image.getHeight()*scalevalue);
				}
			}
		}
	}
	
	public boolean selectDecorationAt(double x, double y, Point pos, int tileSize, Map<String, Image> images) {
		Decoration sel = null;
		
		for(Decoration dec : decorations) {
			Image image = images.get(dec.getImageName());
			double scalevalue= tileSize/48.0;
			double topLeftX = (dec.getxPos() - pos.x) * tileSize - image.getWidth()/2*scalevalue + (tileSize/2);
			double topLeftY = (dec.getyPos() - pos.y) * tileSize - image.getHeight()*scalevalue + tileSize;
			double width = image.getWidth();
			double height = image.getHeight();
			
			dec.setSelected(false);
			
			if(x >= topLeftX && x <= topLeftX + width && y >= topLeftY && y <= topLeftY + height) {
				sel = dec;
			}
		}
		
		if(sel != null) {
			sel.setSelected(true);
			return true;
		}
		
		return false;
	}
	
	public void deleteSelected() {
		for(int i = 0; i < decorations.size(); ++i) {
			if(decorations.get(i).isSelected()) {
				decorations.remove(i);
				--i;
			}
		}
	}
	
	public String toString() {
		String save = "";
		for (Decoration dec : decorations){
			save += dec.toString() + " ";
		}
		return save;
	}
}
