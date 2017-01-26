package tileMapEditor.fxml.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileManagerMenuModel {
	private String coordinates = "0.0";
	private int tileSize = 48;
	
	private final IntegerProperty tileSizeProperty = new SimpleIntegerProperty(tileSize);
	private final StringProperty coordinatesProperty = new SimpleStringProperty(coordinates);
	private final ObservableList<String> saveStrings = FXCollections.observableArrayList();
	
	private GameCanvasModel gameCanvasModel;
	
	public void addCoordinatesListener(ChangeListener<? super String> listener) {
		coordinatesProperty.addListener(listener);
	}
	
	public void addTileSizeListener(ChangeListener<? super Number> listener) {
		tileSizeProperty.addListener(listener);
	}
	
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public ObservableList<String> getSaveStrings() {
		return saveStrings;
	}

	public GameCanvasModel getGameCanvasModel() {
		return gameCanvasModel;
	}

	public void setGameCanvasModel(GameCanvasModel gameCanvasModel) {
		this.gameCanvasModel = gameCanvasModel;
	}

}
