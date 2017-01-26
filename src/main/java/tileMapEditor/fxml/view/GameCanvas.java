package tileMapEditor.fxml.view;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;

import TileMapEditor.TileMapEngine.Decoration;
import TileMapEditor.Tiles.TileMap;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import tileMapEditor.fxml.model.GameCanvasModel;
import tileMapEditor.fxml.model.TileMenuModel;

public class GameCanvas extends Canvas implements Initializable {

	private GameCanvasModel model;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setListeners();
	}
	
	public void setModel(GameCanvasModel model) {
		this.model = model;
	}
	
	public GameCanvasModel getModel() {
		return model;
	}
	
	public void setListeners() {
		System.out.println("Listeners for canvas set!");
		
		setOnMouseMoved(e -> {
			System.out.println("x and y updated");
			int x = (int) (Math.floor(e.getX()) / model.getFileManagerMenuModel().getTileSize()) + model.getCanvasXPos();
			int y = (int) (Math.floor(e.getY()) / model.getFileManagerMenuModel().getTileSize()) + model.getCanvasYPos();
			model.setMouseX(x);
			model.setMouseY(y);
		});

		setOnMouseClicked(e -> {
			requestFocus();
			System.out.println("Click!");

			if(e.getButton().equals(MouseButton.PRIMARY)) {
				model.setMouseButton(MouseButton.PRIMARY);
			}
			else if(e.getButton().equals(MouseButton.SECONDARY))
				model.setMouseButton(MouseButton.SECONDARY);
		});

		setOnMouseDragged(e -> {
			System.out.println("Draaaaaaaaaaaag!");
			if(e.getButton().equals(MouseButton.PRIMARY))
				model.setMouseButton(MouseButton.PRIMARY);
			else if(e.getButton().equals(MouseButton.SECONDARY))
				model.setMouseButton(MouseButton.SECONDARY);
		});

		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("Click key!");

				switch (event.getCode()) {
				case UP:
					model.changeCanvasYPosBy(-1);
					break;
				case RIGHT:
					model.changeCanvasXPosBy(1);
					break;
				case DOWN:
					model.changeCanvasYPosBy(1);
					break;
				case LEFT:
					model.changeCanvasXPosBy(-1);
					break;
				case SPACE:
					model.changeCanvasXPosBy(-1*model.getCanvasXPos());
					model.changeCanvasYPosBy(-1*model.getCanvasYPos());
					break;
				case DELETE:
					model.getTileMap().deleteSelectedDec();
				default:
					System.out.println(event.getCode());
					break;
				}
			}

		}); 
//		*/
	}
	
	public void draw() {
		GraphicsContext gc = getGraphicsContext2D();
		int tileSize = model.getFileManagerMenuModel().getTileSize();
		int xPos = model.getCanvasXPos(), yPos = model.getCanvasYPos();
		
		for(int i = xPos; i < getWidth() / tileSize + xPos; i++) {
			for(int j = yPos; j < getHeight() / tileSize + yPos; j++) {
				Image image;
				String imageName = model.getTileMap().getTile(new Point(i, j)).getTileImageId();
				
				if (TileMenuModel.getTiles().get(imageName) == null || imageName == null){
					image = TileMenuModel.getTiles().get(TileMap.DEFAULT_TILE);
				} else {
					image = TileMenuModel.getTiles().get(imageName);
				}
				
				if (!model.getTileMap().getTile(new Point(i, j)).isSolid()) {
					gc.setGlobalAlpha(0.2);
				}
				gc.drawImage(image, (i - xPos) * tileSize, (j - yPos) * tileSize, tileSize, tileSize);
				gc.setGlobalAlpha(1);
				
				if(model.getTileMap().getSelectedTile() != null && model.getTileMap().getSelectedTile().equals(model.getTileMap().getTile(new Point(i, j)))) {
					gc.setLineWidth(2);
					gc.setStroke(Color.BLUE);
					gc.strokeRect((i - xPos) * tileSize, (j - yPos) * tileSize, tileSize, tileSize);
				}
			}
		}
		
		for(int i = xPos; i < getWidth() / tileSize + xPos; i++) {
			for(int j = yPos; j < getHeight() / tileSize + yPos; j++) {
				for(int k = 0; model.getTileMap().getTile(new Point(i, j)).getDecorations() != null && k < model.getTileMap().getTile(new Point(i, j)).getDecorations().size(); ++k) {
			Decoration dec = model.getTileMap().getTile(new Point(i, j)).getDecorations().get(k);
			Image decImage = TileMenuModel.getDecorations().get(dec.getImageName());
			if(decImage != null) {
			double scalevalue= tileSize/48.0;
			double drawPosX = (i - xPos + dec.getxAdjust()) * tileSize - decImage.getWidth()/2*scalevalue + (tileSize/2);
			double drawPosY = (j - yPos + dec.getyAdjust()) * tileSize - decImage.getHeight()*scalevalue + tileSize;
			
			
				gc.drawImage(decImage, drawPosX, drawPosY, decImage.getWidth()*scalevalue, decImage.getHeight()*scalevalue);
				if(model.getTileMap().getSelectedTile() != null) {
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
	
	public void stealFocus() {
		requestFocus();
	}
	
}
