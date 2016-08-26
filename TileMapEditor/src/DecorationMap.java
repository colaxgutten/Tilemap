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
		
		for(Decoration dec : decorations) {
			Image image = images.get(dec.getImageName());
			if(image != null) {
				gc.drawImage(image, pos.x + dec.getxPos() * tileSize, pos.y + dec.getyPos() * tileSize, tileSize, tileSize);
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
