package uos.Game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class SlgApp extends Application {
	GridPane root;
	Pane solid, liquid,gas,items; 
	Label sLabel,lLabel, gLabel; 
	Scene scene;
	Image image,vol,sfxImg; 
	ImageView imageView;
	GraphicsDevice gd;
	int width; //screen width
	int height; //screen height
	Canvas canvas;
	GraphicsContext gc;
	ArrayList<GameObject> itemList = new ArrayList<GameObject>(); //list of game objects
	@SuppressWarnings("unchecked")
	ArrayList<String> itemNames = new ArrayList(Arrays.asList("gold","icecube","juice","water","helium","steam"));//list of names of game objects
	FactoryPattern factory;
	EventHandler<MouseEvent> mouseHandler; //mouse handler for dragging
	Boolean volStatus ;//boolean to indicate if volume is muted or not
	Boolean sfxStatus;//boolean to indicate if sound effects are on/off
	CommandPattern commandPattern = new CommandPattern();  
public static void main(String[] args)
	{
		launch(args);
	}

	@SuppressWarnings({ "unchecked", "exports" })
	@Override
	public void start(Stage primaryStage) throws Exception {
//		Get the width and height of the screen
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();
		canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
//		icon
		Image image = new Image(getClass().getResourceAsStream("slg_icon.png"));
		primaryStage.getIcons().add(image);
//		title 
		primaryStage.setTitle("SLG - Have fun learning about solids, liquids and gases by identifying them");
		
		root = new GridPane();
		scene = new Scene(root,1280,720);
		primaryStage.setScene(scene);
		primaryStage.show();
		
//		Set up the gridpane
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(25);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(25);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(25);
	    ColumnConstraints column4 = new ColumnConstraints();
	    column4.setPercentWidth(25);
	    root.getColumnConstraints().addAll(column1, column2, column3,column4);
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(100);
	    root.getRowConstraints().addAll(row1);
//	    Solid
		solid = new Pane();
		solid.setStyle("-fx-background-color: #07B8F7;"
				+ "-fx-border-color: #2F06D6;"
				+ "-fx-border-width: 5px;");
		sLabel = new Label("Solids");
		sLabel.setLayoutX(100);
		sLabel.setLayoutY(20);
		sLabel.setFont(Font.font("Cambria",50));
		sLabel.setTextFill(Color.WHITE);
		solid.getChildren().add(sLabel);	
//		Liquid
		liquid = new Pane();
		liquid.setStyle("-fx-background-color: #F0657B;" 
				+"-fx-border-color:#FA392B ;"
				+ "-fx-border-width: 5px;");
		lLabel = new Label("Liquids");
		lLabel.setLayoutX(100);
		lLabel.setLayoutY(20);
		lLabel.setFont(Font.font("Cambria",50));
		lLabel.setTextFill(Color.WHITE);
		liquid.getChildren().add(lLabel);
//		Gas
		gas = new Pane();
		gas.setStyle("-fx-background-color:#5BDE6E;"
				+ "-fx-border-color:#3BA805;"
				+ "-fx-border-width: 5px;");
		gLabel = new Label("Gases");
		gLabel.setLayoutX(100);
		gLabel.setLayoutY(20);
		gLabel.setFont(Font.font("Cambria",50));
		gLabel.setTextFill(Color.WHITE);
		gas.getChildren().add(gLabel);
//		Second row(purple row) consists of 3 columns
		items = new Pane();
		items.setStyle("-fx-background-color:#2A154F");
		root.getChildren().addAll(solid,liquid,gas,items);
//		Create and store the items 
		factory = new FactoryPattern(gc);
		int index=0;
		for(int i=0;i<3;i++) 
		{
			String itemType ="";
			if(i==0)
				itemType = "solid";
			else if(i==1)
				itemType = "liquid";
			else if(i==2)
				itemType = "gas";
			for(int j=0;j<2;j++) 
			{
				String path = 
//						"/images/"+
			itemType+(j+1)+".png";
				image = new Image(getClass().getResourceAsStream(path));
				itemList.add(factory.createItem(itemType, (index+1)*100, 30,image,items,itemNames.get(index)));
				 index++;
			}
        }
		
//		Display the items
		for(GameObject obj:itemList) 
		obj.display();
		
//	    Mouse handler for dragging(dragging command is implemented by the command pattern , while the coordinates are from the handler)		
		mouseHandler = new EventHandler<MouseEvent>() 
			{@Override
				public void handle(MouseEvent event) 
				{
				  for(GameObject obj:itemList) 
					{
					
					  if(event.getPickResult().getIntersectedNode().equals(obj.label))
					  {
						  DragCommand d = new DragCommand(event.getX(),event.getY(),obj); 
							commandPattern.addCommand(d);
					 }
					}
//				  System.out.println(event.getPickResult()); 
				}
			};
			 scene.setOnMouseDragged(mouseHandler);
			 
//			Sound effects for when an objects intersects(collides) with the corerct/wrong pane 
		String MEDIA_URL1=SlgApp.class.getResource("correct_sound.mp3").toExternalForm();
		Media media1 = new Media(MEDIA_URL1);
		MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
		MediaView mediaView1 = new MediaView(mediaPlayer1);
		mediaView1.setX(scene.getWidth());
		mediaView1.setY(scene.getHeight());
		root.getChildren().add(mediaView1);
		String MEDIA_URL2=SlgApp.class.getResource("incorrect_sound.mp3").toExternalForm();
		Media media2 = new Media(MEDIA_URL2);
		MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
		MediaView mediaView2 = new MediaView(mediaPlayer2);
		mediaView2.setX(scene.getWidth());
		mediaView2.setY(scene.getHeight());
		root.getChildren().add(mediaView2);
				
		 for(GameObject obj:itemList) 
				{
		 obj.label.setOnMouseDragged((MouseEvent event) -> {
			 if(sfxStatus) {
				 if(obj.label.getBoundsInParent().intersects(solid.getBoundsInParent())) {
					 if((obj.label.getText().equals("icecube")||(obj.label.getText().equals("gold")))) 
					 {mediaPlayer1.play();
						  if(mediaPlayer1.getCurrentCount() ==1)
							  mediaPlayer1.stop();
					 }
					 else
						{mediaPlayer2.play();
						 if(mediaPlayer2.getCurrentCount() ==1)
							  mediaPlayer2.stop();
						}
				}
				 else if(obj.label.getBoundsInParent().intersects(gas.getBoundsInParent())) {
					 if((obj.label.getText().equals("steam")||(obj.label.getText().equals("helium")))) 
					 {	mediaPlayer1.play();
						  if(mediaPlayer1.getCurrentCount() ==1)
							  mediaPlayer1.stop();
					 }
					 else
						{mediaPlayer2.play();
						 if(mediaPlayer2.getCurrentCount() ==1)
							  mediaPlayer2.stop();
						}
				}
				 else if(obj.label.getBoundsInParent().intersects(liquid.getBoundsInParent())) {
					 if((obj.label.getText().equals("water")||(obj.label.getText().equals("juice")))) 
					 {	  mediaPlayer1.play();
						  if(mediaPlayer1.getCurrentCount() ==1)
							  mediaPlayer1.stop();
					 }
					 else
						{mediaPlayer2.play();
						 if(mediaPlayer2.getCurrentCount() ==1)
							  mediaPlayer2.stop();
						}
				}
			 }
			 
		 });}
			GridPane.setRowIndex(solid, 0);
		    GridPane.setColumnIndex(solid, 3);
		    GridPane.setRowIndex(liquid, 0);
		    GridPane.setColumnIndex(liquid, 1);
		    GridPane.setRowIndex(gas, 0);
		    GridPane.setColumnIndex(gas, 2);
		    GridPane.setRowIndex(items, 0);
		    GridPane.setColumnIndex(items, 0);
	
//		Music
	    String MEDIA_URL=SlgApp.class.getResource("jazz.mp3").toExternalForm();
		Media media = new Media(MEDIA_URL);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.autoPlayProperty();//Start playing immediately after it ends
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setX(scene.getWidth());
		mediaView.setY(scene.getHeight());
		root.getChildren().add(mediaView);
//		Sound effects button on/off 
		Button sfxBtn=new Button("");
		sfxBtn.setLayoutX(225);
		sfxBtn.setLayoutY(10);
		sfxBtn.setStyle("-fx-background-color:transparent");
		sfxImg = new Image(getClass().getResourceAsStream("sfx.png"));
		imageView =  new ImageView(sfxImg);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		sfxBtn.setGraphic(imageView);
		items.getChildren().add(imageView);
		sfxStatus=true;
		sfxBtn.setOnAction(new EventHandler() {
			  @Override
				public void handle(Event event) {
				  if(sfxStatus) {
				sfxStatus=false;
				 sfxImg = new Image(getClass().getResourceAsStream("off.png"));
					imageView =  new ImageView(sfxImg);
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					items.getChildren().add(imageView);
					sfxBtn.setGraphic(imageView);	
				  }
				  else {
				 sfxStatus=true;
				  sfxImg = new Image(getClass().getResourceAsStream("sfx.png"));
					imageView =  new ImageView(sfxImg);
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					items.getChildren().add(imageView);
					sfxBtn.setGraphic(imageView);
					}
				}
			});
		
		items.getChildren().add(sfxBtn);
		
//		Volume button mute or unmute
		Button btn=new Button("");
		btn.setLayoutX(5);
		btn.setLayoutY(10);
		btn.setStyle("-fx-background-color:transparent");
		 vol = new Image(getClass().getResourceAsStream("volume_on.png"));
		imageView =  new ImageView(vol);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		items.getChildren().add(imageView);
		btn.setGraphic(imageView);
		volStatus = true; 
		btn.setOnAction(new EventHandler() {
			  @Override
				public void handle(Event event) {
				  if(volStatus) { //if volume is on the time it was clicked then turn off 
				 mediaPlayer.pause();
				 volStatus=false;
				 vol = new Image(getClass().getResourceAsStream("volume_off.png"));
					imageView =  new ImageView(vol);
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					items.getChildren().add(imageView);
					btn.setGraphic(imageView);	
				  }
				  else {
				  mediaPlayer.play();
				  volStatus=true;
				  vol = new Image(getClass().getResourceAsStream("volume_on.png"));
					imageView =  new ImageView(vol);
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);
					items.getChildren().add(imageView);
					btn.setGraphic(imageView);
					}
				}
			});
		
		items.getChildren().add(btn);
//      Keyboard handler , different actions will be initiated when certain keys are pressed
	    EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent> () {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.M) {
                      //mute or unmute					
					 if(volStatus) {
						 mediaPlayer.pause();
						 volStatus=false;
						 vol = new Image(getClass().getResourceAsStream("volume_off.png"));
							imageView =  new ImageView(vol);
							imageView.setFitHeight(50);
							imageView.setFitWidth(50);
							items.getChildren().add(imageView);
							btn.setGraphic(imageView);	
						  }
						  else {
						  mediaPlayer.play();
						  volStatus=true;
						  vol = new Image(getClass().getResourceAsStream("volume_on.png"));
							imageView =  new ImageView(vol);
							imageView.setFitHeight(50);
							imageView.setFitWidth(50);
							items.getChildren().add(imageView);
							btn.setGraphic(imageView);
							}
				}
				if(event.getCode() == KeyCode.S) { //sound effects on/off
					  if(sfxStatus) {
						  mediaPlayer1.stop();
						  mediaPlayer2.stop();
							sfxStatus=false;
							 sfxImg = new Image(getClass().getResourceAsStream("off.png"));
								imageView =  new ImageView(sfxImg);
								imageView.setFitHeight(50);
								imageView.setFitWidth(50);
								items.getChildren().add(imageView);
								sfxBtn.setGraphic(imageView);	
							  }
							  else {
								  mediaPlayer1.play();
								  mediaPlayer2.play();
							 sfxStatus=true;
							  sfxImg = new Image(getClass().getResourceAsStream("sfx.png"));
								imageView =  new ImageView(sfxImg);
								imageView.setFitHeight(50);
								imageView.setFitWidth(50);
								items.getChildren().add(imageView);
								sfxBtn.setGraphic(imageView);
								}
							
				}
				if(event.getCode() == KeyCode.Y) { //redo command
					commandPattern.redo();
				}
				if(event.getCode() == KeyCode.Z) { //undo command
					commandPattern.undo();
				}
			}};
			scene.setOnKeyPressed(keyHandler);
		
//			Creating a dialog for information
	      Dialog<String> dialog = new Dialog<String>();
	      //Setting the title
	      dialog.setTitle("Information");
	      ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
	      //Setting the content of the dialog
	      DialogPane dialogPane = new DialogPane();
	      dialogPane.setPrefWidth(700);
	      dialogPane.setPrefHeight(300);
	      dialogPane.setHeaderText("SLG - Solids,Liquids,Gases | What are they?");
	      dialogPane.setContentText("Solid, Liquid and Gas, are states of matter."
		      		+ " Matter is any object that takes up space and has mass.\n"
		      		+ "What's a solid?\n"
		      		+ "A solid keeps its shape, like a basketball. Even if you dribble a basketball, it still keeps its shape.\n"
		      		+ "What's a liquid?\n"
		      		+ "A Liquid takes the shape of its container, such as a glass, jug or pot. Some examples of liquids are coffee,tea and soda\n"
		      		+ "What's a gas?\n"
		      		+ "A gas fills its entire container. When you blow into a balloon, you fill it up with air which is a gas.");
	      
	      dialog.setDialogPane(dialogPane);
	      //Adding buttons to the dialog pane
	      dialog.getDialogPane().getButtonTypes().add(type);
	       //Creating a button
	      Button button = new Button("");
	      button.setLayoutX(80);
	      button.setLayoutY(10);
	      button.setStyle("-fx-background-color:transparent");
	      imageView =  new ImageView(new Image(this.getClass().getResource("info.png").toString()));
	      imageView.setFitHeight(50);
		  imageView.setFitWidth(50);
		  items.getChildren().add(imageView);
		  button.setGraphic(imageView);
	      //Showing the dialog when the button is clicked
	      button.setOnAction(e -> {
	         dialog.showAndWait();
	      });
	        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image(this.getClass().getResource("info.png").toString()));
	     items.getChildren().add(button);
		
 //		  Creating a dialog for controls
	      Dialog<String> dialog2 = new Dialog<String>();
	      //Setting the title
	      dialog2.setTitle("Learn the controls");
	      //Setting the content of the dialog
	      DialogPane dialogPane2 = new DialogPane();
	      dialogPane2.setPrefWidth(400);
	      dialogPane2.setPrefHeight(200);
	      dialogPane2.setHeaderText("Controls");
	      dialogPane2.setContentText("Press S to turn on/off sound effects \n"
	      		+ "Press M to turn on/off background music \n"
	      		+ "\n You can start playing by dragging an object slowly to the correct pane.");
	      dialog2.setDialogPane(dialogPane2);
	      //Adding buttons to the dialog pane
	      dialog2.getDialogPane().getButtonTypes().add(type);
	       //Creating a button
	      Button button2 = new Button("");
	      button2.setLayoutX(160);
	      button2.setLayoutY(10);
	      button2.setStyle("-fx-background-color:transparent");
	      imageView =  new ImageView(new Image(this.getClass().getResource("help.png").toString()));
	      imageView.setFitHeight(50);
		  imageView.setFitWidth(50);
		  items.getChildren().add(imageView);
		  button2.setGraphic(imageView);
	      //Showing the dialog when the button is clicked
	      button2.setOnAction(e -> {
	         dialog2.showAndWait();
	      });
	        Stage stage2 = (Stage) dialog2.getDialogPane().getScene().getWindow();
	        stage2.getIcons().add(new Image(this.getClass().getResource("help.png").toString()));
	     items.getChildren().add(button2);
	}

}  
