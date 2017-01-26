package tileMapEditor.fxml.view;
import java.util.HashMap;

import TileMapEditor.TileMapEngine.LoadZone;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		startValues(primaryStage);
//		setStartScene(primaryStage);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXHandler.fxml"));
		Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
		
		/* TODO: Do we still need this?
		File file = new File(saveFolder+"\\"+"saveFile.txt");
		if (file.exists()) {
			System.out.println("file exists! yay");
			currentLoadZone.loadFromFile(saveFolder+"\\"+"saveFile.txt");
		}*/
	}
	
	private void startValues(Stage stage) {
		stage.setTitle("MapEditor");
		stage.setWidth(mapWidth);
		stage.setHeight(mapHeight);
	}
}
