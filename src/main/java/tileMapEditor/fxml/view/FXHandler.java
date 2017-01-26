package tileMapEditor.fxml.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import TileMapEditor.Tiles.TileMap;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import tileMapEditor.fxml.model.FileManagerMenuModel;
import tileMapEditor.fxml.model.GameCanvasModel;
import tileMapEditor.fxml.model.TileMenuModel;

public class FXHandler extends BorderPane implements Initializable {
	
	TileMap tileMap;
	
	@FXML
	private FileManagerMenu fileManagerMenuController;
	
	@FXML
	private TileMenu tileMenuController;
	
	@FXML
	private GameCanvas gameCanvasController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FileManagerMenuModel fileManagerMenuModel = new FileManagerMenuModel();
		fileManagerMenuController.setModel(fileManagerMenuModel);
		
		GameCanvasModel gameCanvasModel = new GameCanvasModel();
		gameCanvasModel.setTileMap(tileMap);
		gameCanvasModel.setFileManagerMenuModel(fileManagerMenuController.getModel());
		gameCanvasController.setModel(gameCanvasModel);
		
		fileManagerMenuModel.setGameCanvasModel(gameCanvasController.getModel());
		
		TileMenuModel tileMenuModel = new TileMenuModel();
		tileMenuModel.setGameCanvasModel(gameCanvasController.getModel());
		tileMenuModel.setTileMap(tileMap);
		tileMenuController.setModel(tileMenuModel);
		
//		gameCanvasController.initializeListeners()
		fileManagerMenuController.initializeListeners();
		
		gameCanvasController.setListeners();
		for (Node nodes : getAllNodes(this)) {
			nodes.setFocusTraversable(false);
		}
		
		gameCanvasController.setFocusTraversable(true);
		gameCanvasController.requestFocus();
		
		gameCanvasController.setOnMouseClicked(e -> {
			gameCanvasController.requestFocus();
		});
		
		new AnimationTimer() {
			@Override
			public void handle(long now) {
//				draw();
//				System.out.println("Has focus: " + getScene().getFocusOwner());
				System.out.println("Canvas is focused? " + gameCanvasController.isFocused());
			}
		}.start();
	}
	
	public static ArrayList<Node> getAllNodes(Parent root) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		addAllDescendents(root, nodes);
		return nodes;
	}
	
	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
		for (Node node : parent.getChildrenUnmodifiable()) {
			nodes.add(node);
			if (node instanceof Parent)
				addAllDescendents((Parent) node, nodes);
		}
	}
	
	public void stealFocus() {
		gameCanvasController.requestFocus();
	}
	
	public void draw() {
		gameCanvasController.draw();
	}
}
