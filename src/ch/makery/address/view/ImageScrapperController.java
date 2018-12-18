package ch.makery.address.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

// java 8 code
public class ImageScrapperController extends Application {

    //Class containing grid (see below)
    private GridDisplay gridDisplay;
    private ComboBox<String> category = new ComboBox<String>();
    private TilePane tilePane;
    private Button refresh;
    
    private ObservableList<String> statusList = FXCollections.observableArrayList("all", "jackets", "shirts", "tops_sweaters",
			"sweatshirts", "pants", "shorts", "t-shirts", "hats", "bags", "accessories", "skate");

    //Class responsible for displaying the grid containing the Rectangles
    public class GridDisplay {

        private static final double ELEMENT_SIZE = 100;
        private static final double GAP = ELEMENT_SIZE / 10;
        public TilePane tilePane =  new TilePane();
        private Group display = new Group(tilePane);
        private int nRows;
        private int nCols;

        public GridDisplay(int nRows, int nCols) {
            tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
            tilePane.setHgap(GAP);
            tilePane.setVgap(GAP);
            tilePane.prefTileWidthProperty().setValue(150);
            tilePane.prefTileHeightProperty().setValue(150);
            setColumns(nCols);
            setRows(nRows);
        }

        public void setColumns(int newColumns) {
            nCols = newColumns;
            tilePane.setPrefColumns(nCols);
            createElements();
        }

        public void setRows(int newRows) {
            nRows = newRows;
            tilePane.setPrefRows(nRows);
            createElements();
        }

        public Group getDisplay() {
            return display;
        }

        public void createElements() {
        	
            tilePane.getChildren().clear();
            
            InputStream input = null;
			try {
				input = new URL("https://www.supremenewyork.com/shop/new").openStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		Document document = new Tidy().parseDOM(input, null);
    		
    		NodeList imgs = document.getElementsByTagName("img");
    		
    		List<String> srcs = new ArrayList<String>();
    		
           
            		for (int k = 0; k <imgs.getLength(); k++) {
            			HBox image = new HBox();
        				Image img = new Image("http:" + imgs.item(k).getAttributes().getNamedItem("src").getNodeValue());
        				ImageView imgView = new ImageView(img);
        				imgView.setFitWidth(150);
        				imgView.setFitHeight(150);
        				image.getChildren().add(imgView);
        				 tilePane.getChildren().add(image);
            		}
              
                
            
        }
        
        
        
        

       
    }
    
    public void initialize(URL location, ResourceBundle resources) {
        refresh.setOnAction(buttonHandler);

    }
    
    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	tilePane.getChildren().clear();
        	event.consume();
        }
    };
    
   
    @Override
    public void start(Stage primaryStage) {

        //Represents the grid with Rectangles
        gridDisplay = new GridDisplay(2, 3);

        //Fields to specify number of rows/columns
        TextField rowField = new TextField("0");
        TextField columnField = new TextField("0");
        
       
        
        final ImageView imageView = new ImageView(
				new Image("file:" + System.getProperty("user.dir") + "/resources/images/refresh.png"));
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        
        refresh = new Button("", imageView);
        refresh.setAlignment(Pos.BASELINE_RIGHT);

        //Function to set an action when text field loses focus
        buildTextFieldActions(rowField, columnField);

        HBox fields = new HBox(10);
        fields.setAlignment(Pos.BASELINE_RIGHT);
       
        fields.getChildren().add(refresh);
        
        refresh.setStyle("-fx-background-color: transparent;");
    	

        
        
        ScrollPane scrollPane = new ScrollPane(gridDisplay.getDisplay());

        BorderPane mainPanel = new BorderPane();
        mainPanel.setCenter(scrollPane);
        mainPanel.setTop(fields);
        mainPanel.setStyle("-fx-padding: 10");

        Scene scene = new Scene(mainPanel, 513, 535);
        
        primaryStage.getIcons().add(
				new Image("file:" + System.getProperty("user.dir") + "/resources/images/" + "imageScrapper.ico"));
        
        String css = this.getClass().getResource("/css/ClearTheme.css").toExternalForm();
        
        scene.getStylesheets().add(css);
       
        primaryStage.setTitle("Image Scrapper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

    private void buildTextFieldActions(final TextField rowField, final TextField columnField) {
        rowField.focusedProperty().addListener((ov, t, t1) -> {
            if (!t1) {
                if (!rowField.getText().equals("")) {
                    try {
                        int nbRow = Integer.parseInt(rowField.getText());
                        gridDisplay.setRows(nbRow);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            }
        });

        columnField.focusedProperty().addListener((ov, t, t1) -> {
            if (!t1) {
                if (!columnField.getText().equals("")) {
                    try {
                        int nbColumn = Integer.parseInt(columnField.getText());
                        gridDisplay.setColumns(nbColumn);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            }
        });
    }
}