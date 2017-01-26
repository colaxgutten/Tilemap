package tileMapEditor.fxml.view;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import TileMapEditor.TileMapEngine.Decoration;
import TileMapEditor.Tiles.Tile;
import TileMapEditor.Tiles.TileMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import tileMapEditor.fxml.model.TileMenuModel;

public class TileMenu extends VBox implements Initializable {
	
	TileMenuModel model;

	@FXML
	private ComboBox<String> imageSelectionComboBox, tileTypesComboBox;
	
	@FXML
	private Slider decorationSlider;
	
	@FXML
	private ListView<ImageView> imageListView;
	
	@FXML
	private CheckBox showSolidCheckBox, selectDecorationCheckBox;
	
	@FXML
	private TextField imageSearchTextField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageSelectionComboBox.getItems().addAll(TileMenuModel.IMAGE_SELECTION_OPTIONS);
		imageSelectionComboBox.getSelectionModel().select(TileMenuModel.IMAGE_SELECTION_OPTIONS[0]);
		
		tileTypesComboBox.getItems().addAll(TileMenuModel.TILE_TYPES);
		
		setLocalListeners();
		addImagesToListViewFromList(TileMenuModel.getTiles().values());
	}

	public void addImagesToListViewFromList(Collection<? extends Image> list) {
		for(Image i : list) {
			ImageView img = new ImageView(i);
			img.setFitHeight(80);
			img.setFitWidth(80);
			imageListView.getItems().add(img);
		}
	}
	
	private ArrayList<ImageView> searchTileOrDec(String s) {
		ArrayList<ImageView> list = null;
		String selected = imageSelectionComboBox.getSelectionModel().getSelectedItem();
		
		if(selected.equals(TileMenuModel.IMAGE_SELECTION_OPTIONS[0]))
			list = (ArrayList<ImageView>) TileMenuModel.getSearchtreeTiles(s);
		else if(selected.equals(TileMenuModel.IMAGE_SELECTION_OPTIONS[1]))
			list = (ArrayList<ImageView>) TileMenuModel.getSearchtreeDecorations(s);
		
		return list;
	}
	
	private void setListeners() {
		
	}
	
	private void setLocalListeners() {
		imageSelectionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals(oldValue))
					return;
				
				imageListView.getItems().clear();
				if(newValue.equals(TileMenuModel.IMAGE_SELECTION_OPTIONS[0]))
					addImagesToListViewFromList(TileMenuModel.getTiles().values());
				else if(newValue.equals(TileMenuModel.IMAGE_SELECTION_OPTIONS[1]))
					addImagesToListViewFromList(TileMenuModel.getDecorations().values());
			}
		});
		
		decorationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			// TODO: FIX THIS SHIT!
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Decoration dec = model.getTileMap().getSelectedDec();
				if (dec != null)
					dec.setyAdjust(-decorationSlider.getValue());
			}
		});
		
		imageSearchTextField.textProperty().addListener(((o, ol, ne) -> {
			ArrayList<ImageView> list =  searchTileOrDec(ne);

			imageListView.getItems().clear();
			imageListView.getItems().addAll(list);
		}));
		
		/*
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
		*/
	}
	
	/*

	public boolean deleteDecIsSelected() {
		return deleteDec.isSelected();
	}

	public boolean solidIsSelected() {
		return solid.isSelected();
	}

	public boolean showSolidIsSelected() {
		return showSolid.isSelected();
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
	*/
	
	public void canvasMouseLeftClicked(double x, double y, int canvasX, int canvasY) {
		if (selectDecIsSelected()) {
			if(model.getTileMap().getSelectedDec() != null)
//			System.out.println(tileMap.getSelectedDec().getxAdjust() + ", " + tileMap.getSelectedDec().getyAdjust() + " & " + tileMap.getSelectedDec().getxPos() + ", " + tileMap.getSelectedDec().getyPos());
			
				model.getTileMap().selectDecorationAt(x, y, new Point(0, 0), 10,
					TileMenuModel.getDecorations()); //TODO: FIX THIS!!!
			return;
		}

		int tileX = (int) (Math.floor(x/10)); //+ canvasXpos;
		int tileY = (int) (Math.floor(y/10)); //+ canvasYpos;
		System.out.println(tileX + " " + tileY);

		if (TileMenuModel.IMAGE_SELECTION_OPTIONS[imageListView.getSelectionModel().getSelectedIndex()].equals("tiles")) {
			Point p = new Point(tileX, tileY);
			Tile t = model.getTileMap().getTile(p);
			t.setTileImageName(""); // TODO: HAS TO BE CHANGED!!!
			if (solidIsSelected()) {
				t.setSolid(true);
			} else {
				t.setSolid(false);
			}
			model.getTileMap().setTile(p, t);
			model.getTileMap().removeSelection();
			model.getTileMap().selectTileAt((int) p.getX(), (int) p.getY());
		} else if (TileMenuModel.IMAGE_SELECTION_OPTIONS[imageListView.getSelectionModel().getSelectedIndex()].equals("decorations")) {
				if (model.getTileMap().getTile(new Point(tileX, tileY)) == null)
					model.getTileMap().setTile(new Point(tileX, tileY), Tile.getBasicTile());
				model.getTileMap().addDecoration(new Point(tileX, tileY),
						new Decoration(tileX, tileY, "")); // TODO: HAS TO BE CHANGED!!!
		}
	}
	
	public void canvasMouseRightClicked(double x, double y, int canvasX, int canvasY) {
		
	}
	
	public void canvasMouseLeftDragged(double x, double y, int canvasX, int canvasY) {
		int tileX = (int) (Math.floor(x/10)); //+ canvasXpos;
		int tileY = (int) (Math.floor(y/10)); //+ canvasYpos;
		
		if (selectDecIsSelected()) {
			if(model.getTileMap().getSelectedDec() == null)
				return;
			
//			currentLoadZone.getTileMap().getSelectedDec().setxPos(x);
//			currentLoadZone.getTileMap().getSelectedDec().setyPos(y);
			// TODO: Reactivate this once the Hashmap<Point, Decoration> has been changed!
			// Until change takes effect this functionality is broke, though implemented.
			
			return;
		}

		if (imageSelectionComboBox.selectionModelProperty().getName().equals("tiles")) {
			Point p = new Point(tileX, tileY);
			Tile t = model.getTileMap().getTile(p);
			t.setTileImageName(""); // TODO: CHANGE THIS!!!
			if (solidIsSelected())
				t.setSolid(true);
			else
				t.setSolid(false);
			model.getTileMap().setTile(p, t);
		}
	}
	
	public void canvasMouseRightDragged(double x, double y, int canvasX, int canvasY) {
		
	}
	
	public boolean selectDecIsSelected() {
		return selectDecorationCheckBox.isSelected();
	}
	
	public boolean solidIsSelected() {
		return showSolidCheckBox.isSelected();
	}
	
	public void imageListSearchField() {
		
	}
	
	public void setModel(TileMenuModel model) {
		this.model = model;
	}
	
	public TileMenuModel getModel() {
		return model;
	}
}
