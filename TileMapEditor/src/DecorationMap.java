import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class DecorationMap {
	private List<Decoration> decorations = new ArrayList<>();
	
	public void add(Decoration dec) {
		decorations.add(dec);
	}
	
	public String toString() {
		String save = "";
		for (Decoration dec : decorations){
			save += dec.toString() + " ";
		}
		return save;
	}
}
