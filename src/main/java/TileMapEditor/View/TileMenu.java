package TileMapEditor.View;

import java.util.ArrayList;
import java.util.HashMap;

import TileMapEditor.TileMapEngine.Decoration;
import TileMapEditor.TileMapEngine.LoadZone;
import TileMapEditor.TileMapEngine.SearchTree;
import TileMapEditor.TilesFromWeb.ImageReaderFromOpenArt;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TileMenu extends VBox {

	final private HBox hbox = new HBox();
	final private ListView<String> listImages = new ListView<>();
	final private VBox propertiesBox = new VBox();
	final private ComboBox<String> imageSelection = new ComboBox<>();
	final private TextField eventInput = new TextField();
	final private Slider decorationSlider = new Slider();
	final private String[] tileTypes = { "DoorTile", "Tile", "ChestTile" };
	final private double decorationSliderMaxValue = 5;
	final private TextField webImages = new TextField();
	final private ListView<Image> webImageListView = new ListView<>();
	final private TextField searchBarForTiles = new TextField();
	final private ImageReaderFromOpenArt imgr = new ImageReaderFromOpenArt();
	final private CheckBox solid = new CheckBox("Solid");
	final private CheckBox showSolid = new CheckBox("Show solid");
	final private CheckBox deleteDec = new CheckBox("Select decoration");
	final private ComboBox<String> tileTypesForEventInput = new ComboBox<>();
	final private HashMap<String, Image> decorations;
	final private HashMap<String, Image> tiles;
	final private SearchTree<String> searchTreeTiles = new SearchTree<>();
	final private SearchTree<String> searchTreeDecorations = new SearchTree<>();

	private ArrayList<Image> webImagesLoaded = new ArrayList<>();
	private LoadZone currentLoadZone;
	private String prevSelectedListViewItemIndex = "";
	private String leftClickSelectedItem;
	private String rightClickSelectedItem;

	public TileMenu(HashMap<String, Image> decorations, HashMap<String, Image> tiles, LoadZone currentLoadZone) {
		this.currentLoadZone = currentLoadZone;
		this.decorations = decorations;
		this.tiles = tiles;
		initialize();
	}

	private void initialize() {
		tileTypesForEventInput.getItems().addAll(tileTypes);
		setupSearchTree();
		searchBarForTiles.textProperty().addListener(getSearchBarEvent());

		decorationSlider.setMax(decorationSliderMaxValue);
		decorationSlider.setMin(0);

		propertiesBox.setSpacing(20);
		propertiesBox.getChildren().add(solid);
		propertiesBox.getChildren().add(showSolid);
		propertiesBox.getChildren().add(deleteDec);
		propertiesBox.getChildren().add(tileTypesForEventInput);
		propertiesBox.getChildren().add(eventInput);
		propertiesBox.getChildren().add(decorationSlider);

		listImages.getItems().addAll(tiles.keySet());
		listImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listImages.setStyle("-fx-selection-bar-non-focused: #ACCEF7;");
		listImages.setPrefWidth(100);

		imageSelection.getItems().add("tiles");
		imageSelection.getItems().add("decorations");
		imageSelection.getSelectionModel().select(0);
		imageSelection.valueProperty().addListener(getImageListener());

		setListeners();

		hbox.getChildren().add(listImages);
		hbox.getChildren().add(propertiesBox);

		getChildren().add(searchBarForTiles);
		getChildren().add(imageSelection);
		getChildren().add(hbox);
		getChildren().add(webImages);
		getChildren().add(webImageListView);
	}

	private void setupSearchTree() {
		for (String s : tiles.keySet())
			searchTreeTiles.put(s, s);
		
		for (String s : decorations.keySet())
			searchTreeDecorations.put(s, s);
	}

	private void setListeners() {
		webImageListView.setCellFactory(list -> new ListCell<Image>() {
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
			if (search != null && search.length() > 0) {
				webImagesLoaded = imgr.getImagesFromWeb(search);
				webImageListView.getItems().clear();
				webImageListView.getItems().addAll(webImagesLoaded);
			}
		});

		addDecorationListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Decoration dec = currentLoadZone.getTileMap().getSelectedDec();
				if (dec != null)
					dec.setyAdjust(-getDecorationSlider().getValue());
			}
		});

		addListImagesListener((view, oldValue, newValue) -> {
			prevSelectedListViewItemIndex = newValue;
			System.out.println(prevSelectedListViewItemIndex);
		});

		setListImagesOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				leftClickSelectedItem = prevSelectedListViewItemIndex;
			} else if (e.getButton() == MouseButton.SECONDARY) {
				rightClickSelectedItem = prevSelectedListViewItemIndex;
			}
		});

		// sets value of selected item with either left or right click
		// click(working as left and right click key-binding)
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
					String selection = (String) imageSelection.getValue();
					if (selection.equals("tiles")) {
						im.setImage(tiles.get(key));
					} else if (selection.equals("decorations")) {
						im.setImage(decorations.get(key));
					}
					setGraphic(im);
				}
			}
		});
	}

	public boolean deleteDecIsSelected() {
		return deleteDec.isSelected();
	}

	public boolean solidIsSelected() {
		return solid.isSelected();
	}

	public boolean showSolidIsSelected() {
		return showSolid.isSelected();
	}

	public Slider getDecorationSlider() {
		return decorationSlider;
	}

	public void addDecorationListener(ChangeListener<? super Number> listener) {
		decorationSlider.valueProperty().addListener(listener);
	}

	public void setListImagesOnMouseClicked(EventHandler<MouseEvent> e) {
		listImages.setOnMouseClicked(e);
	}

	public ChangeListener<String> getImageListener() {
		return (new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (t1.equals("tiles")) {
					listImages.getItems().clear();
					listImages.getItems().addAll(tiles.keySet());
				} else if (t1.equals("decorations")) {
					listImages.getItems().clear();
					listImages.getItems().addAll(decorations.keySet());
				}
			}
		});
	}

	public ChangeListener<String> getSearchBarEvent() {
		return ((o, ol, ne) -> {
			ArrayList<String> list =  searchTileOrDec(ne);

			listImages.getItems().clear();
			listImages.getItems().addAll(list);
		});
	}
	
	private ArrayList<String> searchTileOrDec(String s) {
		ArrayList<String> list = null;
		String selected = imageSelection.getSelectionModel().getSelectedItem();
			
			if(selected.equals("tiles"))
				list = (ArrayList<String>) searchTreeTiles.searchAll(s);
			else if(selected.equals("decorations"))
				list = (ArrayList<String>) searchTreeDecorations.searchAll(s);
		return list;
	}

	public void addListImagesListener(ChangeListener<String> e) {
		listImages.getSelectionModel().selectedItemProperty().addListener(e);
	}

	public String getImageSelectionValue() {
		return imageSelection.getValue();
	}

	public String getLeftClickSelectedItem() {
		return leftClickSelectedItem;
	}

	public String getRightClickSelectedItem() {
		return rightClickSelectedItem;
	}
}
