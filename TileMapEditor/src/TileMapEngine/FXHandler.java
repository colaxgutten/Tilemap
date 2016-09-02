package TileMapEngine;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Tiles.Tile;
import TilesFromWeb.ImageReaderFromOpenArt;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FXHandler {
	private LoadZone currentLoadZone;
	private Stage stage;
	private Canvas canvas;
	private HBox leftSideBox;
	private VBox rightSideBox;
	private VBox leftContainer;
	private Canvas center;
	private String[] tileTypes = {"DoorTile","Tile","ChestTile"};

	int tilesToBePainted = 16;
	
	private int canvasXpos;
	private int canvasYpos;
	private int tileSize = 48;
	private String leftClickSelectedItem;
	private String rightClickselectedItem;
	String prevSelectedListViewItemIndex = "";
	ImageReaderFromOpenArt imgr = new ImageReaderFromOpenArt();
	ArrayList<Image> webImagesLoaded;
	
	TextField saveName;
	TextField searchBarForTiles = new TextField();
	TextField eventInput;
	TextField webImages;
	ListView<Image> webImageListView;
	ComboBox tileTypesForEventInput;
	ObservableList<String> saveStrings;
	ObservableList<String> searchStrings;
	ComboBox savedFiles;
	ComboBox imageSelection;
	String currentSaveFile = "";
	Label zoomValueLabel;
	ScrollBar canvasZoom;
	Slider decorationSlider;
	double decorationSliderMaxValue = 5;
	
	CheckBox solid;
	CheckBox showSolid;
	CheckBox deleteDec;
	VBox propertiesBox;
	HashMap<String,Image> tiles;
	HashMap<String,Image> decorations;
	ListView<String> listImages;
	Label cordinates;
	
	public FXHandler(LoadZone currentLoadZone, Stage stage) {
		this.currentLoadZone = currentLoadZone;
		this.stage = stage;
	}
	
	public void setup() {
		BorderPane border = new BorderPane();

		border.setLeft(leftContainer);
		border.setRight(rightSideBox);
		
		canvas.setHeight(720);
		canvas.setWidth(720);
		border.setCenter(canvas);
		
		Scene scene = new Scene(border, 1280, 1000);
		canvas.setVisible(true);
		stage.setScene(scene);
		stage.show();

		for (Node nodes : getAllNodes(border)) {
			nodes.setFocusTraversable(false);
		}
		canvas.setFocusTraversable(true);
	}
	
	public void loadCanvas() {
		canvas = new Canvas();
		setCanvasEvents(canvas);
	}

	public void loadRightSide(String saveFolder) {
		saveStrings = FXCollections.observableArrayList();
		saveStrings.addAll(loadSaveStrings(new File(saveFolder)));
		savedFiles = new ComboBox(saveStrings);
		cordinates = new Label();
		
		rightSideBox = new VBox();
		
		Button save = new Button("Save");
		Button load = new Button("Load");
		
		saveName = new TextField();
		saveName.setText("saveFile");
		zoomValueLabel = new Label();
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
			currentSaveFile = saveToLoad;
			saveName.setText(saveToLoad.substring(0, saveToLoad.length() - 4));
		});
		canvasZoom = new ScrollBar();
		canvasZoom.setMax(96);
		canvasZoom.setMin(12);
		canvasZoom.setValue(48);
		canvasZoom.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				tileSize = newValue.intValue();
				tilesToBePainted = (int) Math.floor(16 * 48 / tileSize);
				zoomValueLabel.setText("Rute st�rrelse: " + tileSize);
			}
		});
		

		rightSideBox.setMargin(save, new Insets(20, 20, 20, 0));
		rightSideBox.setMargin(zoomValueLabel, new Insets(0, 0, 20, 0));
		rightSideBox.getChildren().add(canvasZoom);
		rightSideBox.getChildren().add(zoomValueLabel);
		rightSideBox.getChildren().add(saveName);
		rightSideBox.getChildren().add(save);
		rightSideBox.getChildren().add(load);
		rightSideBox.getChildren().add(savedFiles);
		rightSideBox.getChildren().add(cordinates);
	}

	public void loadLeftSide(HashMap<String,Image> tiles,HashMap<String,Image> decorations) {
		leftSideBox = new HBox();
		leftContainer = new VBox();
		eventInput = new TextField();
		decorationSlider = new Slider();
		tileTypesForEventInput = new ComboBox();
		tileTypesForEventInput.getItems().addAll(tileTypes);
		webImages=new TextField();
		webImageListView = new ListView<Image>();
		webImageListView.setCellFactory( list -> new ListCell<Image> () {
			@Override
			protected void updateItem(Image img, boolean empty) {
				ImageView im = new ImageView();
				im.setFitHeight(80);
				im.setFitWidth(80);
				super.updateItem(img, empty);
				if (img == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					im.setImage(img);
					setGraphic(im);
				}
			}
		});
		webImages.setOnAction(e -> {
			String search = webImages.getText();
			if (search!= null && search.length()>0){
				webImagesLoaded = imgr.getImagesFromWeb(search);
				webImages.clear();
				webImageListView.getItems().addAll(webImagesLoaded);
			}
		});
		
		imageSelection = new ComboBox();
		imageSelection.getItems().add("tiles");
		imageSelection.getItems().add("decorations");
		imageSelection.getSelectionModel().select(0);
		
		this.tiles = tiles;
		this.decorations = decorations;
		
		decorationSlider.setMax(decorationSliderMaxValue);
		decorationSlider.setMin(0);
		decorationSlider.valueProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {		
					Decoration dec = currentLoadZone.getDecMap().getSelected();
					if (dec!=null){
						dec.setyAdjust(-decorationSlider.getValue());
					}	
			}
			
		});
		
		imageSelection.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	          if (t1.equals("tiles")){
	        	  listImages.getItems().clear();
	        	  listImages.getItems().addAll(tiles.keySet());
	          }else if (t1.equals("decorations")){
	        	  listImages.getItems().clear();
	        	  listImages.getItems().addAll(decorations.keySet());
	          }
	        }    
	    });
		searchBarForTiles = new TextField();
		searchStrings = FXCollections.observableArrayList();
		
		
		
		
		listImages = new ListView<String>();
		listImages.getItems().addAll(tiles.keySet());
		listImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listImages.setPrefWidth(100);
		searchBarForTiles.setOnAction(e -> {
			TextField tf = (TextField)e.getSource();
			String s = tf.getText();
			if (s.length()>0){
				searchStrings.clear();
				listImages.getItems().clear();
				listImages.getItems().addAll(tiles.keySet());
			for (String search: listImages.getItems()){
				if (search.contains(s)){
					searchStrings.add(search);
				}
			}
			listImages.getItems().clear();
			listImages.getItems().addAll(searchStrings);

			}
			else{
				listImages.getItems().clear();
				listImages.getItems().addAll(tiles.keySet());
			}
		});
		// sets value of selected item eighter with right click or left
		// click(working as left and right click key-binding)
		listImages.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				leftClickSelectedItem = prevSelectedListViewItemIndex;
			} else if (e.getButton() == MouseButton.SECONDARY) {
				rightClickselectedItem = prevSelectedListViewItemIndex;
			}
		});
		listImages.getSelectionModel().selectedItemProperty().addListener((view, oldValue, newValue) -> {
			prevSelectedListViewItemIndex = newValue;
			System.out.println(prevSelectedListViewItemIndex);
		});
		// makes the listView draw images instead of objects in text form
		listImages.setCellFactory(listView -> new ListCell<String>() {
			@Override
			protected void updateItem(String key, boolean empty) {
				ImageView im = new ImageView();
				im.setFitHeight(80);
				im.setFitWidth(80);
				super.updateItem(key, empty);
				if ((tiles.get(key) == null && decorations.get(key) == null) || empty) {
					setText(null);
					setGraphic(null);
				} else {
					String selection = (String)imageSelection.getValue();
					if(selection.equals("tiles")){
					im.setImage(tiles.get(key));
					} else if (selection.equals("decorations")){
						im.setImage(decorations.get(key));
					}
					setGraphic(im);
				}
			}
		});

		solid = new CheckBox("Solid");
		showSolid = new CheckBox("Show solid");
		deleteDec = new CheckBox("Delete decoration");
		
		propertiesBox = new VBox();
		propertiesBox.setSpacing(20);
		propertiesBox.getChildren().add(solid);
		propertiesBox.getChildren().add(showSolid);
		propertiesBox.getChildren().add(deleteDec);
		propertiesBox.getChildren().add(tileTypesForEventInput);
		propertiesBox.getChildren().add(eventInput);
		propertiesBox.getChildren().add(decorationSlider);
		
		leftSideBox.getChildren().add(listImages);
		leftSideBox.getChildren().add(propertiesBox);
		leftContainer.getChildren().add(searchBarForTiles);
		leftContainer.getChildren().add(imageSelection);
		leftContainer.getChildren().add(leftSideBox);
		leftContainer.getChildren().add(webImages);
		leftContainer.getChildren().add(webImageListView);
	}

	private void copyOfStrings(ObservableList<String> stringsToCopy, ObservableList<String> saveStrings2) {
		saveStrings2.clear();
		for (String s : stringsToCopy){
			if (!saveStrings2.contains(s))
				saveStrings2.add(s);
		}
	}

	private void setCanvasEvents(Canvas canvas) {
		canvas.setOnMouseMoved(e -> {
			int x = (int) (Math.floor(e.getX()) / tileSize) + canvasXpos;
			int y = (int) (Math.floor(e.getY()) / tileSize) + canvasYpos;
			String tilePos = x +","+y;
			cordinates.setText(tilePos);
		});
		
		canvas.setOnMouseClicked(e -> {
			canvas.requestFocus();
			
			double x = e.getX();
			double y = e.getY();
			
			if(deleteDec.isSelected()) {
				currentLoadZone.getDecMap().selectDecorationAt(x, y, new Point(canvasXpos, canvasYpos), tileSize, decorations);
				return;
			}
			
			int tileX = (int) Math.floor(x / tileSize) + canvasXpos;
			int tileY = (int) Math.floor(y / tileSize) + canvasYpos;
			System.out.println(tileX + " " + tileY);
			String selection = (String)imageSelection.getValue();
			
			String imageName = leftClickSelectedItem;
			if (e.getButton().equals(MouseButton.SECONDARY))
				imageName = rightClickselectedItem;
			if (selection.equals("tiles")){
			Point p = new Point(tileX, tileY);
			Tile t = currentLoadZone.getTileMap().getTile(p);
			t.setTileImageName(imageName);
			if (solid.isSelected()) {
				t.setSolid(true);
			} else {
				t.setSolid(false);
			}
			System.out.println(currentLoadZone.getTileMap().getSize());
			currentLoadZone.getTileMap().setTile(p, t);
			currentLoadZone.getTileMap().removeSelection();
			currentLoadZone.getTileMap().selectTileAt((int)p.getX(), (int)p.getY());
			} else if (selection.equals("decorations")){
				if (decorations.containsKey(imageName)){
				currentLoadZone.getDecMap().add(new Point(tileX, tileY), new Decoration(tileX,tileY,imageName));
				}
			}
		});
		
		canvas.setOnMouseDragged(e -> {
			if(deleteDec.isSelected()) return;
			
			int x = (int) (Math.floor(e.getX()) / tileSize) + canvasXpos;
			int y = (int) (Math.floor(e.getY()) / tileSize) + canvasYpos;
			String imageName = "";
			
			if (e.getButton() == MouseButton.PRIMARY) {	
				imageName=leftClickSelectedItem;
			} else if (e.getButton() == MouseButton.SECONDARY) {	
				imageName=rightClickselectedItem;
			}
			String selection = (String) imageSelection.getValue();
			if (selection.equals("tiles")){
			Point p = new Point(x, y);
			Tile t = currentLoadZone.getTileMap().getTile(p);
			t.setTileImageName(imageName);
			if (solid.isSelected())
				t.setSolid(true);
			else
				t.setSolid(false);
			currentLoadZone.getTileMap().setTile(p, t);
			}
		});

		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				switch (event.getCode()) {
				case UP:
					canvasYpos -= 1;
					System.out.println("Up");
					break;
				case RIGHT:
					canvasXpos += 1;
					System.out.println("Right");
					break;
				case DOWN:
					canvasYpos += 1;
					System.out.println("Down");
					break;
				case LEFT:
					canvasXpos -= 1;
					System.out.println("Left");
					break;
				case SPACE:
					canvasXpos = 0;
					canvasYpos = 0;
					break;
				case DELETE:
					currentLoadZone.getDecMap().deleteSelected();
				}
			}

		});
	}

	public ArrayList<String> loadSaveStrings(final File folder) {
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

	/**
	 * draw the tiles in the tilemap"tiles".
	 * 
	 * @param gc
	 */
	public void drawTiles() {
		listImages.refresh();
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.clearRect(0, 0, 768, 768);
		
		currentLoadZone.draw(canvas, new Point(canvasXpos, canvasYpos), tileSize, showSolid.isSelected(), tiles, decorations);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	 
	public boolean isSolid() {
		return false;
	}
}
