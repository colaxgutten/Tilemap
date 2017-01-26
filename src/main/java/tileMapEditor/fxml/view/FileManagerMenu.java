package tileMapEditor.fxml.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tileMapEditor.fxml.model.FileManagerMenuModel;

public class FileManagerMenu extends VBox {
	
	FileManagerMenuModel model = new FileManagerMenuModel();
	
	@FXML
	Label labelTileCoordinator;
	
//	final private int tilesToBePainted = 16;
	
//	private LoadZone currentLoadZone;
	
		/*
		Button save = new Button("Save");
		Button load = new Button("Load");

		saveName.setText("saveFile");
		zoomValueLabel.setText("Rute st�rrelse: " + tileSize);

		save.setOnAction(e -> {
			String s = saveName.getText() + ".txt";
			currentLoadZone.saveToFile(s);
			if (!savedFiles.getItems().contains(s))
				savedFiles.getItems().add(s);
		});
		load.setOnAction(e -> {
			String saveToLoad = (String) savedFiles.getSelectionModel().getSelectedItem();
			currentLoadZone.loadFromFile(saveToLoad);
			saveName.setText(saveToLoad.substring(0, saveToLoad.length() - 4));
		});
		
		VBox.setMargin(save, new Insets(20, 20, 20, 0));
		VBox.setMargin(zoomValueLabel, new Insets(0, 0, 20, 0));
		saveStrings.addAll(SaveFileHandler.getInstance().getFiles());
		
		canvasZoom.setMax(96);
		canvasZoom.setMin(12);
		canvasZoom.setValue(48);
		canvasZoom.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				tileSize = newValue.intValue();
//				tilesToBePainted = (int) Math.floor(16 * 48 / FXHandler.tileSize);
				zoomValueLabel.setText("Rute st�rrelse: " + tileSize);
			}
		});

		getChildren().add(canvasZoom);
		getChildren().add(zoomValueLabel);
		getChildren().add(saveName);
		getChildren().add(save);
		getChildren().add(load);
		getChildren().add(savedFiles);
		getChildren().add(cordinates);
		*/
	

	/*
	private ArrayList<String> loadSaveStrings(final File folder) {
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
	} */
	
	public void initializeListeners() {
		model.getGameCanvasModel().getMouseXProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("CHANGEX");
				model.setCoordinates(newValue + ", " + model.getGameCanvasModel().getCanvasYPos());
			}
		});
		
		model.getGameCanvasModel().getMouseYProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("CHANGEY");
				model.setCoordinates(model.getGameCanvasModel().getCanvasXPos() + ", " + newValue);
			}
		});
	}
	
	public void setModel(FileManagerMenuModel model) {
		this.model = model;
	}
	
	public FileManagerMenuModel getModel() {
		return model;
	}
}
