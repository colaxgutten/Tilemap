package TileMapEditor.View;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import TileMapEditor.TileMapEngine.Decoration;
import TileMapEditor.TileMapEngine.ImageLoader;
import TileMapEditor.TileMapEngine.LoadZone;
import TileMapEditor.Tiles.Tile;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FXHandler {
	
	private LoadZone currentLoadZone;
	private Stage stage;
	private Canvas canvas;

	private int canvasXpos;
	private int canvasYpos;

	private TileMenu leftSideBox;
	private FileManagerMenu rightSideBox;
	
	private final String tileFolder = getClass().getResource("/images/tileImages").getFile();
	private final String decorationFolder = getClass().getResource("/images/decorations").getFile();
	private HashMap<String, Image> decorations;
	private HashMap<String, Image> tiles;

//	private String currentSaveFile = ""; TODO: UNUSED VARIABLE!
	
	public FXHandler(LoadZone currentLoadZone, Stage stage) {
		this.currentLoadZone = currentLoadZone;
		this.stage = stage;
		loadImages();
		
		leftSideBox = new TileMenu(decorations, tiles, currentLoadZone);
		rightSideBox = new FileManagerMenu(currentLoadZone);
		
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

	/*
	 * private void copyOfStrings(ObservableList<String> stringsToCopy,
	 * ObservableList<String> saveStrings2) { saveStrings2.clear(); for (String
	 * s : stringsToCopy){ if (!saveStrings2.contains(s)) saveStrings2.add(s); }
	 * }
	 */

	private void setCanvasEvents(Canvas canvas) {
		canvas.setOnMouseMoved(e -> {
			int x = (int) (Math.floor(e.getX()) / rightSideBox.getTileSize()) + canvasXpos;
			int y = (int) (Math.floor(e.getY()) / rightSideBox.getTileSize()) + canvasYpos;
			String tilePos = x + "," + y;
			rightSideBox.setCoordinatesText(tilePos);
		});

		canvas.setOnMouseClicked(e -> {
			canvas.requestFocus();

			double x = e.getX();
			double y = e.getY();

			if (leftSideBox.deleteDecIsSelected()) {
				if(currentLoadZone.getTileMap().getSelectedDec() != null)
				System.out.println(currentLoadZone.getTileMap().getSelectedDec().getxAdjust() + ", " + currentLoadZone.getTileMap().getSelectedDec().getyAdjust() + " & " + currentLoadZone.getTileMap().getSelectedDec().getxPos() + ", " + currentLoadZone.getTileMap().getSelectedDec().getyPos());
				
				currentLoadZone.getTileMap().selectDecorationAt(x, y, new Point(canvasXpos, canvasYpos), rightSideBox.getTileSize(),
						decorations);
				return;
			}

			int tileX = (int) Math.floor(x / rightSideBox.getTileSize()) + canvasXpos;
			int tileY = (int) Math.floor(y / rightSideBox.getTileSize()) + canvasYpos;
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
			int x = (int) (Math.floor(e.getX()) / rightSideBox.getTileSize()) + canvasXpos;
			int y = (int) (Math.floor(e.getY()) / rightSideBox.getTileSize()) + canvasYpos;
			
			if (leftSideBox.deleteDecIsSelected()) {
				if(currentLoadZone.getTileMap().getSelectedDec() == null)
					return;
				
//				currentLoadZone.getTileMap().getSelectedDec().setxPos(x);
//				currentLoadZone.getTileMap().getSelectedDec().setyPos(y);
				// TODO: Reactivate this once the Hashmap<Point, Decoration> has been changed!
				// Until change takes effect this functionality is broke, though implemented.
				
				return;
			}

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

		currentLoadZone.draw(canvas, new Point(canvasXpos, canvasYpos), rightSideBox.getTileSize(), leftSideBox.showSolidIsSelected(),
				tiles, decorations);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public boolean isSolid() {
		return false;
	}
}
