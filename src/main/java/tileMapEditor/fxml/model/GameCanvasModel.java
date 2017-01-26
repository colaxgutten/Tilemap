package tileMapEditor.fxml.model;

import TileMapEditor.Tiles.TileMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseButton;

public class GameCanvasModel {
	private int canvasXPos = 0, canvasYPos = 0, mouseX = 0, mouseY = 0;
	private MouseButton mouseButton;
	private FileManagerMenuModel fileManagerMenuModel;
	private TileMap tileMap;
	
	private final IntegerProperty mouseXProperty = new SimpleIntegerProperty(mouseX), mouseYProperty = new SimpleIntegerProperty(mouseY); ;

	public void changeCanvasXPosBy(int i) {
		canvasXPos += i;
	}
	
	public void changeCanvasYPosBy(int i) {
		canvasYPos += i;
	}
	
	public int getCanvasXPos() {
		return canvasXPos;
	}

	public void setCanvasXPos(int canvasXPos) {
		this.canvasXPos = canvasXPos;
	}

	public int getCanvasYPos() {
		return canvasYPos;
	}

	public void setCanvasYPos(int canvasYPos) {
		this.canvasYPos = canvasYPos;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public MouseButton getMouseButton() {
		return mouseButton;
	}

	public void setMouseButton(MouseButton mouseButton) {
		this.mouseButton = mouseButton;
	}

	public FileManagerMenuModel getFileManagerMenuModel() {
		return fileManagerMenuModel;
	}

	public void setFileManagerMenuModel(FileManagerMenuModel fileManagerMenuModel) {
		this.fileManagerMenuModel = fileManagerMenuModel;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public IntegerProperty getMouseXProperty() {
		return mouseXProperty;
	}

	public IntegerProperty getMouseYProperty() {
		return mouseYProperty;
	}
	
	
}
