package TileMapEditor.View;

import java.io.File;
import java.util.ArrayList;

import TileMapEditor.TileMapEngine.LoadZone;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FileManagerMenu extends VBox {
	
	final private ObservableList<String> saveStrings = FXCollections.observableArrayList();
	final private ComboBox<String> savedFiles = new ComboBox<String>(saveStrings);
	final private String saveFolder = getClass().getResource("/saveFiles").getFile();
	final private TextField saveName = new TextField();
	final private Label zoomValueLabel = new Label();
	final private ScrollBar canvasZoom = new ScrollBar();
	final private Label cordinates = new Label();
//	final private int tilesToBePainted = 16;
	
	private int tileSize = 48;
	private LoadZone currentLoadZone;
	
	public FileManagerMenu(LoadZone currentLoadZone) {
		this.currentLoadZone = currentLoadZone;
		initialize();
	}
	
	public void initialize() {
		Button save = new Button("Save");
		Button load = new Button("Load");

		saveName.setText("saveFile");
		zoomValueLabel.setText("Rute st�rrelse: " + tileSize);

		save.setOnAction(e -> {
			String s = saveName.getText() + ".txt";
			currentLoadZone.saveToFile(saveFolder + "\\" + s);
			if (!savedFiles.getItems().contains(s))
				savedFiles.getItems().add(s);
		});
		load.setOnAction(e -> {
			String saveToLoad = (String) savedFiles.getSelectionModel().getSelectedItem();
			currentLoadZone.loadFromFile(saveFolder + "\\" + saveToLoad);
//			currentSaveFile = saveToLoad; TODO: UNUSED VARIABLE!
			saveName.setText(saveToLoad.substring(0, saveToLoad.length() - 4));
		});
		
		VBox.setMargin(save, new Insets(20, 20, 20, 0));
		VBox.setMargin(zoomValueLabel, new Insets(0, 0, 20, 0));
		saveStrings.addAll(loadSaveStrings(new File(saveFolder)));
		
		canvasZoom.setMax(96);
		canvasZoom.setMin(12);
		canvasZoom.setValue(48);
		canvasZoom.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				tileSize = newValue.intValue();
//				tilesToBePainted = (int) Math.floor(16 * 48 / FXHandler.tileSize);
				zoomValueLabel.setText("Rute st�rrelse: " + tileSize);
			}
		});

		getChildren().add(canvasZoom);
		getChildren().add(zoomValueLabel);
		getChildren().add(saveName);
		getChildren().add(save);
		getChildren().add(load);
		getChildren().add(savedFiles);
		getChildren().add(cordinates);
	}
	
	
	private ArrayList<String> loadSaveStrings(final File folder) {
		ArrayList<String> savedFileStrings = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				loadSaveStrings(fileEntry);
			} else {
				if (fileEntry.getName().endsWith(".txt")) {
					String name = fileEntry.getName();
					savedFileStrings.add(name);
				}
			}
		}
		return savedFileStrings;
	}
	
	public void setCoordinatesText(String s) {
		cordinates.setText(s);
	}
	
	public int getTileSize() {
		return tileSize;
	}
}
