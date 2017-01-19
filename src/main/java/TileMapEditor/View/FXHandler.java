package TileMapEditor.View;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import TileMapEditor.TileMapEngine.Decoration;
import TileMapEditor.TileMapEngine.ImageLoader;
import TileMapEditor.TileMapEngine.LoadZone;
import TileMapEditor.Tiles.Tile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FXHandler {
	
	private LoadZone currentLoadZone;
	private Stage stage;
	private Canvas canvas;

	int tilesToBePainted = 16;

	private int canvasXpos;
	private int canvasYpos;
	private int tileSize = 48;

	private TileMenu leftSideBox;
	private FileManagerMenu rightSideBox;
	
	private final String tileFolder = getClass().getResource("/images/tileImages").getFile();
	private final String decorationFolder = getClass().getResource("/images/decorations").getFile();
	private final String saveFolder = getClass().getResource("/saveFiles").getFile();
	private HashMap<String, Image> decorations;
	private HashMap<String, Image> tiles;

	final private TextField saveName = new TextField();;
	final private ObservableList<String> saveStrings = FXCollections.observableArrayList();;
	final private ComboBox<String> savedFiles = new ComboBox<String>(saveStrings);;
//	private String currentSaveFile = ""; TODO: UNUSED VARIABLE!
	final private Label zoomValueLabel = new Label();;
	final private ScrollBar canvasZoom = new ScrollBar();;
	final private Label cordinates = new Label();;

	public FXHandler(LoadZone currentLoadZone, Stage stage) {
		this.currentLoadZone = currentLoadZone;
		this.stage = stage;
		loadImages();
		
		leftSideBox = new TileMenu(decorations, tiles, currentLoadZone);
		rightSideBox = new FileManagerMenu();
		
		loadRightSide();
		loadCanvas();
		setup();
	}
	
	private void loadImages() {
		ImageLoader il = new ImageLoader();
		il.loadFolderTiles(tileFolder);
		il.loadDecorations(decorationFolder);
		tiles = il.getTiles();
		decorations = il.getDecorations();
	}

	public void setup() {
		BorderPane border = new BorderPane();

		border.setLeft(leftSideBox);
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

	public void loadRightSide() {
		saveStrings.addAll(loadSaveStrings(new File(saveFolder)));

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

		VBox.setMargin(save, new Insets(20, 20, 20, 0));
		VBox.setMargin(zoomValueLabel, new Insets(0, 0, 20, 0));
		rightSideBox.getChildren().add(canvasZoom);
		rightSideBox.getChildren().add(zoomValueLabel);
		rightSideBox.getChildren().add(saveName);
		rightSideBox.getChildren().add(save);
		rightSideBox.getChildren().add(load);
		rightSideBox.getChildren().add(savedFiles);
		rightSideBox.getChildren().add(cordinates);
	}

	/*
	 * private void copyOfStrings(ObservableList<String> stringsToCopy,
	 * ObservableList<String> saveStrings2) { saveStrings2.clear(); for (String
	 * s : stringsToCopy){ if (!saveStrings2.contains(s)) saveStrings2.add(s); }
	 * }
	 */

	private void setCanvasEvents(Canvas canvas) {
		canvas.setOnMouseMoved(e -> {
			int x = (int) (Math.floor(e.getX()) / tileSize) + canvasXpos;
			int y = (int) (Math.floor(e.getY()) / tileSize) + canvasYpos;
			String tilePos = x + "," + y;
			cordinates.setText(tilePos);
		});

		canvas.setOnMouseClicked(e -> {
			canvas.requestFocus();

			double x = e.getX();
			double y = e.getY();

			if (leftSideBox.deleteDecIsSelected()) {
				currentLoadZone.getTileMap().selectDecorationAt(x, y, new Point(canvasXpos, canvasYpos), tileSize,
						decorations);
				return;
			}

			int tileX = (int) Math.floor(x / tileSize) + canvasXpos;
			int tileY = (int) Math.floor(y / tileSize) + canvasYpos;
			System.out.println(tileX + " " + tileY);
			String selection = leftSideBox.getImageSelectionValue();

			String imageName = leftSideBox.getLeftClickSelectedItem();
			if (e.getButton().equals(MouseButton.SECONDARY))
				imageName = leftSideBox.getRightClickSelectedItem();;
			if (selection.equals("tiles")) {
				Point p = new Point(tileX, tileY);
				Tile t = currentLoadZone.getTileMap().getTile(p);
				t.setTileImageName(imageName);
				if (leftSideBox.solidIsSelected()) {
					t.setSolid(true);
				} else {
					t.setSolid(false);
				}
				System.out.println(currentLoadZone.getTileMap().getSize());
				currentLoadZone.getTileMap().setTile(p, t);
				currentLoadZone.getTileMap().removeSelection();
				currentLoadZone.getTileMap().selectTileAt((int) p.getX(), (int) p.getY());
			} else if (selection.equals("decorations")) {
				if (decorations.containsKey(imageName)) {
					if (currentLoadZone.getTileMap().getTile(new Point(tileX, tileY)) == null)
						currentLoadZone.getTileMap().setTile(new Point(tileX, tileY), Tile.getBasicTile());
					System.out.println(currentLoadZone);
					System.out.println(currentLoadZone.getTileMap());
					System.out.println(currentLoadZone.getTileMap().getTile(new Point(tileX, tileY)));
					System.out.println(currentLoadZone.getTileMap().getTile(new Point(tileX, tileY)).getDecorations());
					currentLoadZone.getTileMap().addDecoration(new Point(tileX, tileY),
							new Decoration(tileX, tileY, imageName));
				}
			}
		});

		canvas.setOnMouseDragged(e -> {
			if (leftSideBox.deleteDecIsSelected())
				return;

			int x = (int) (Math.floor(e.getX()) / tileSize) + canvasXpos;
			int y = (int) (Math.floor(e.getY()) / tileSize) + canvasYpos;
			String imageName = "";

			if (e.getButton() == MouseButton.PRIMARY) {
				imageName = leftSideBox.getLeftClickSelectedItem();
			} else if (e.getButton() == MouseButton.SECONDARY) {
				imageName = leftSideBox.getRightClickSelectedItem();
			}
			String selection = leftSideBox.getImageSelectionValue();
			if (selection.equals("tiles")) {
				Point p = new Point(x, y);
				Tile t = currentLoadZone.getTileMap().getTile(p);
				t.setTileImageName(imageName);
				if (leftSideBox.solidIsSelected())
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
					currentLoadZone.getTileMap().deleteSelectedDec();
				default:
					System.out.println(event.getCode());
					break;
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
		// listImages.refresh();
		// System.out.println("canvas: " + canvas + ", canvasXpos: " +
		// canvasXpos + ", canvasYpos: " + canvasYpos + ", tileSize: " +
		// tileSize + ", showSolid: " + showSolid + ", tiles: " + tiles + ",
		// decorations: " + decorations);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.clearRect(0, 0, 768, 768);

		currentLoadZone.draw(canvas, new Point(canvasXpos, canvasYpos), tileSize, leftSideBox.showSolidIsSelected(),
				tiles, decorations);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public boolean isSolid() {
		return false;
	}
}
