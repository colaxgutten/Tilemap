package TileMapEditor.TileMapEngine;
import java.io.File;
import java.util.HashMap;

import TileMapEditor.View.FXHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application {
	int mapHeight = 768;
	int mapWidth = 1280;

	Stage window;
	long renderTimer;
	LoadZone currentLoadZone;
	HashMap<String,Image> tiles = null;
	HashMap<String,Image> decorations = null;

	//Using ClassLoader to load the files from resources. Add resources to your classpath if it does not work
	ClassLoader classLoader = getClass().getClassLoader();
	final String saveFolder = classLoader.getResource("saveFiles").getFile();
	final String lol = classLoader.getResource("images/tileImages").getFile();
	final String decorationFolder = classLoader.getResource("images/decorations").getFile();
	final String tileFolder = classLoader.getResource("images/tileImages").getFile();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		currentLoadZone = new LoadZone();
		/* TODO: Do we still need this?
		File file = new File(saveFolder+"\\"+"saveFile.txt");
		if (file.exists()) {
			System.out.println("file exists! yay");
			currentLoadZone.loadFromFile(saveFolder+"\\"+"saveFile.txt");
		}*/

		window = primaryStage;
		window.setTitle("MapEditor");
		window.setWidth(mapWidth);
		window.setHeight(mapHeight);
		
		FXHandler handler = new FXHandler(currentLoadZone, window);

		renderTimer = System.nanoTime();
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				handler.drawTiles();
				
			}

		}.start();
	}
}
