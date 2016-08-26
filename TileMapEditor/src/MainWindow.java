import java.io.File;
import java.util.HashMap;

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
	final String saveFolder = "src\\saveFiles";
	final String decorationFolder = "src\\decorations";
	final String tileFolder = "src\\tiles";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		currentLoadZone = new LoadZone();
		File file = new File(saveFolder+"\\"+"saveFile.txt");
		if (file.exists()) {
			System.out.println("file exists! yay");
			currentLoadZone.loadFromFile(saveFolder+"\\"+"saveFile.txt");
		}

		ImageLoader il = new ImageLoader();
		il.loadFolderTiles(tileFolder);
		il.loadDecorations(decorationFolder);
		tiles = il.getTiles();
		decorations = il.getDecorations();

		window = primaryStage;
		window.setTitle("MapEditor");
		window.setWidth(mapWidth);
		window.setHeight(mapHeight);
		
		FXHandler handler = new FXHandler(currentLoadZone, window);
		handler.loadLeftSide(tiles,decorations);
		handler.loadRightSide(saveFolder);
		handler.loadCanvas();
		handler.setup();
		
		renderTimer = System.nanoTime();
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				handler.drawTiles();
				
			}

		}.start();
	}
}
