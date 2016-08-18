import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xerces.internal.util.ShadowedSymbolTable;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class MainWindow extends Application {
	int mapHeight=720;
	int mapWidth=1080;
	int canvasXpos=0;
	int canvasYpos=0;
	
	int tileSize=48;
	
	int rightClickselectedItem=0;
	int leftClickSelectedItem =0;
	int prevSelectedListViewItemIndex=0;
	Stage window;
	long renderTimer;
	CheckBox showSolid;
	CheckBox solid;
	Tile[][] tiles; 
	Image image;
	Image image2;
	VBox propertiesBox;
	List<Image> images = null;
	List<Image> sideImages = null;
	boolean loaded=false;
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		File file = new File("saveFile.txt");
		if (file.exists()){
			System.out.println("file exists! yay");
			tiles = readSave(file);
		}

		image = new Image("images\\runite.jpg");
		image2 = new Image("images\\illuminati.jpg");
		ImageLoader il = new ImageLoader();
		images = il.getImages("src\\images");
		sideImages= new ArrayList<Image>(images);
				loaded=true;
		window=primaryStage;
		window.setTitle("MapEditor");
		window.setWidth(mapWidth);
		window.setHeight(mapHeight);
		BorderPane border = new BorderPane();
		TextField tf = new TextField("Heisann!!!!!!!!!!!");
		ListView<Image> listImages = new ListView<Image>();
		Button save = new Button("Save");
		solid = new CheckBox("Solid");
		showSolid = new CheckBox("Show solid");
		save.setOnAction(e -> {
			saveFile(new File("saveFile.txt"));
		});
		listImages.getItems().addAll(sideImages);
		listImages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listImages.setPrefWidth(100);
		//sets value of selected item eighter with right click or left click(working as left and right click key-binding)
		listImages.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY ){
				leftClickSelectedItem=prevSelectedListViewItemIndex;
			} else if (e.getButton() == MouseButton.SECONDARY){
				rightClickselectedItem=prevSelectedListViewItemIndex;
			}
		});
		listImages.getSelectionModel().selectedItemProperty().addListener((view,oldValue,newValue) -> {
				prevSelectedListViewItemIndex = sideImages.indexOf(newValue);	
		});
		//makes the listView draw images instead of objects in text form
		listImages.setCellFactory(listView -> new ListCell<Image>() {
			@Override
			 protected void updateItem(Image image,boolean empty){
				ImageView im = new ImageView();
				im.setFitHeight(80);
				im.setFitWidth(80);
				 super.updateItem(image, empty);
				 if (image==null || empty){
					 setText(null);
					 setGraphic(null);
				 }else{
					 im.setImage(image);
					 setGraphic(im);
				 }
			 }
		}
		);
		Canvas canvas = new Canvas();
		canvas.setFocusTraversable(true);
		canvas.setOnMouseClicked(e -> {
			canvas.requestFocus();
			double x = e.getX();
			double y = e.getY();
			int tileX=(int)Math.floor(x/tileSize)+canvasXpos;
			int tileY=(int)Math.floor(y/tileSize)+canvasYpos;
			int i = leftClickSelectedItem;
			if (e.getButton().equals(MouseButton.SECONDARY))
				i = rightClickselectedItem;
			if (tileX < 16 && tileY<16 && tileX>=0 && tileY >=0){
				tiles[tileX][tileY].setTileImageId(i);
				if (solid.isSelected())
					tiles[tileX][tileY].setSolid(true);
				else
					tiles[tileX][tileY].setSolid(false);
			}
				
		});
		canvas.setOnMouseDragged(e -> {
			int x = (int) (Math.floor(e.getX())/tileSize)+canvasXpos;
			int y = (int) (Math.floor(e.getY())/tileSize)+canvasYpos;
			if (x<0 || x>=16 || y<0 || y>=16){
				
			} else {
			if (e.getButton()==MouseButton.PRIMARY){
				tiles[x][y].setTileImageId(leftClickSelectedItem);
			}
			else if (e.getButton()==MouseButton.SECONDARY){
				tiles[x][y].setTileImageId(rightClickselectedItem);
			}
			if (solid.isSelected())
				tiles[x][y].setSolid(true);
			else
				tiles[x][y].setSolid(false);
			}
		});
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				
				switch (event.getCode()){
				case UP : canvasYpos+=1;
					System.out.println("Up");
					break;
				case RIGHT : canvasXpos-=1;
				System.out.println("Right");
					break;
				case DOWN : canvasYpos-=1;
				System.out.println("Down");
					break;
				case LEFT : canvasXpos+=1;
				System.out.println("Left");
					break;
				case SPACE : canvasXpos=0; canvasYpos=0;
					break;
				}
				
			}
			
		});
		HBox leftSideBox = new HBox();
		propertiesBox = new VBox();
		propertiesBox.setSpacing(20);
		propertiesBox.getChildren().add(solid);
		propertiesBox.getChildren().add(showSolid);
		leftSideBox.getChildren().add(listImages);
		leftSideBox.getChildren().add(propertiesBox);
		border.setMargin(canvas,new Insets(0,0,0,0));
		border.setMargin(tf,new Insets(0,0,110,0));
		border.setBottom(tf);
		border.setLeft(leftSideBox);
		border.setRight(save);
		border.setMargin(save, new Insets(100,100,100,0));
		border.setMargin(listImages, new Insets(0,0,0,0));
		canvas.setHeight(window.getHeight());
		canvas.setWidth(720);
		border.setCenter(canvas);
		
		Scene scene = new Scene(border,1280,1000);
		canvas.setVisible(true);
		window.setScene(scene);
		window.show();
		renderTimer=System.nanoTime();
		new AnimationTimer(){

			@Override
			public void handle(long now) {
				listImages.refresh();
				drawTiles(canvas.getGraphicsContext2D());
			}
			
		}.start();
		for (Node nodes : getAllNodes(border)){
			nodes.setFocusTraversable(false);
		}
		canvas.setFocusTraversable(true);
	}
	/**
	 * draw the tiles in the tilemap"tiles". 
	 * @param gc
	 */
	private void drawTiles(GraphicsContext gc){
		gc.clearRect(0, 0,768 , 768);
		Image imageById = null;
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				if (i>=15 || j>=15 || i <0 || j<0)
					continue;
				
				int id =tiles[i][j].getTileImageId();
				if (id<images.size()&& id>=0)
					imageById=images.get(id);
				if (imageById!=null){
					if (showSolid.isSelected()&& !tiles[i][j].isSolid()){
						gc.setGlobalAlpha(0.2);
					}
				gc.drawImage(imageById, (i-canvasXpos)*tileSize, (j-canvasYpos)*tileSize,tileSize,tileSize);
				gc.setGlobalAlpha(1);
				}
			}
		}
		
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
	            addAllDescendents((Parent)node, nodes);
	    }
	}
	
	/**
	 * read from saveFile.txt and parses text to tiles
	 * set basic tiles if there are null tiles or if reading goes wrong
	 * @param file
	 * @return tileSet
	 */
	private Tile[][] readSave(File file){
		Tile[][] tileSet = new Tile[16][16];
		if (file.exists()){
			
		try(FileReader fr = new FileReader(file)){
		BufferedReader br = new BufferedReader(fr);
		
		for (int j =0;j<16;j++){
			String infoUnsplitted=br.readLine();
		String[] info = infoUnsplitted.split("kake");
		System.out.println(infoUnsplitted);
		for (int i = 0;i<16;i++){
			System.out.println(info[i]);
			if (info.length>=i){
			tileSet[j][i]=Tile.fromFileToTile(info[i]);
			}
		}
		}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		}
		for (int i = 0; i<tileSet.length;i++){
			for (int j = 0;j<tileSet[i].length;j++){
				if (tileSet[i][j]==null){
					tileSet[i][j]=Tile.getBasicTile();
				}
			}
		}
		return tileSet;
	}
	/**
	 * saves the current state of the tileset, separate each tile with "kake"
	 * @param file
	 */
	private void saveFile(File file){
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i =0;i<16;i++){
				for (int j = 0;j<16;j++){
					bw.write(tiles[i][j].toSaveString()+"kake");
					bw.flush();
				}
				fw.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
