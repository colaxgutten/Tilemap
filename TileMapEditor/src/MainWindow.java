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
	HashMap<String,Image> images = null;
	final String saveFolder = "src\\saveFiles";

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
		images = il.getImages("src\\images");

		window = primaryStage;
		window.setTitle("MapEditor");
		window.setWidth(mapWidth);
		window.setHeight(mapHeight);
		
		FXHandler handler = new FXHandler(currentLoadZone, window);
		handler.loadLeftSide(images);
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
