package tileMapEditor.fxml.model;

import java.util.HashMap;
import java.util.List;

import TileMapEditor.TileMapEngine.ImageLoader;
import TileMapEditor.TileMapEngine.SearchTree;
import TileMapEditor.Tiles.TileMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileMenuModel {
	private final static HashMap<String, Image> tiles;
	private final static HashMap<String, Image> decorations;
	private final static SearchTree<ImageView> searchTreeTiles = new SearchTree<>();
	private final static SearchTree<ImageView> searchTreeDecorations = new SearchTree<>();
	private final static String tileFolder = TileMenuModel.class.getResource("/images/tileImages").getFile();
	private final static String decorationFolder = TileMenuModel.class.getResource("/images/decorations").getFile();
	
	public final static String[] IMAGE_SELECTION_OPTIONS = new String[] {"tiles", "decorations"}, TILE_TYPES = { "DoorTile", "Tile", "ChestTile" };
	
	static{
		tiles = ImageLoader.loadTiles(tileFolder);
		decorations = ImageLoader.loadDecorations(decorationFolder);
		
		setupSearchTree();
	}
	
	private TileMap tileMap;
	private String leftClickSelectedItem;
	private String rightClickSelectedItem;
	private GameCanvasModel gameCanvasModel;
	private boolean selectDecorationCheckBoxValue = false;
	
	public String getLeftClickSelectedItem() {
		return leftClickSelectedItem;
	}
	public void setLeftClickSelectedItem(String leftClickSelectedItem) {
		this.leftClickSelectedItem = leftClickSelectedItem;
	}
	public String getRightClickSelectedItem() {
		return rightClickSelectedItem;
	}
	public void setRightClickSelectedItem(String rightClickSelectedItem) {
		this.rightClickSelectedItem = rightClickSelectedItem;
	}
	public boolean isSelectDecorationCheckBoxValue() {
		return selectDecorationCheckBoxValue;
	}
	public void setSelectDecorationCheckBoxValue(boolean selectDecorationCheckBoxValue) {
		this.selectDecorationCheckBoxValue = selectDecorationCheckBoxValue;
	}
	public TileMap getTileMap() {
		return tileMap;
	}
	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}
	public static HashMap<String, Image> getTiles() {
		return tiles;
	}
	public static HashMap<String, Image> getDecorations() {
		return decorations;
	}
	public static List<ImageView> getSearchtreeTiles(String s) {
		return searchTreeTiles.searchAll(s);
	}
	public static List<ImageView> getSearchtreeDecorations(String s) {
		return searchTreeDecorations.searchAll(s);
	}
	
	private static void setupSearchTree() {
		for (String s : tiles.keySet()) {
			ImageView img = new ImageView(tiles.get(s));
			img.setFitHeight(80);
			img.setFitWidth(80);
			searchTreeTiles.put(s, img);
		}
			
		for (String s : decorations.keySet()) {
			ImageView img = new ImageView(tiles.get(s));
			img.setFitHeight(80);
			img.setFitWidth(80);
			searchTreeDecorations.put(s, img);
		}
	}
	public GameCanvasModel getGameCanvasModel() {
		return gameCanvasModel;
	}
	public void setGameCanvasModel(GameCanvasModel gameCanvasModel) {
		this.gameCanvasModel = gameCanvasModel;
	}
}
