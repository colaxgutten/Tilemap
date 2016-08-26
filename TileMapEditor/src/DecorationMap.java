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
