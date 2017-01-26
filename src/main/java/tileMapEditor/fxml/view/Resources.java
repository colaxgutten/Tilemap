package tileMapEditor.fxml.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Resources {
		public static void loadFXML(Node node) {
			try {
				String name = "/tileMapEditor/fxml/view/" + node.getClass().getSimpleName() + ".fxml";
				FXMLLoader fxmlLoader = new FXMLLoader(Resources.class.getResource(name));

				fxmlLoader.setRoot(node);
				fxmlLoader.setController(node);

				System.out.println("Loading: " + node.getClass().getSimpleName());

				fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void main(String[] args) {
			Application.launch(MainWindow.class, args);
		}
}

