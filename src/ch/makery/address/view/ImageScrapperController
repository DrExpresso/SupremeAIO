package ch.makery.address.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// java 8 code
public class ImageScrapperController extends Application {

    //Class containing grid (see below)
    private GridDisplay gridDisplay;
    private ComboBox<String> category = new ComboBox<String>();
    
    private ObservableList<String> statusList = FXCollections.observableArrayList("all", "jackets", "shirts", "tops_sweaters",
			"sweatshirts", "pants", "shorts", "t-shirts", "hats", "bags", "accessories", "skate");

    //Class responsible for displaying the grid containing the Rectangles
    public class GridDisplay {

        private static final double ELEMENT_SIZE = 50;
        private static final double GAP = ELEMENT_SIZE / 10;

        private TilePane tilePane = new TilePane();
        private Group display = new Group(tilePane);
        private int nRows;
        private int nCols;

        public GridDisplay(int nRows, int nCols) {
            tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
            tilePane.setHgap(GAP);
            tilePane.setVgap(GAP);
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

        private void createElements() {
            tilePane.getChildren().clear();
            
            InputStream input = null;
			try {
				input = new URL("https://www.supremenewyork.com/shop/all").openStream();
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
        				image.getChildren().add(imgView);
        				 tilePane.getChildren().add(image);
            		}
              
                
            
        }

        private HBox createElement() {
            Rectangle rectangle = new Rectangle(ELEMENT_SIZE, ELEMENT_SIZE);
            rectangle.setStroke(Color.ORANGE);
            rectangle.setFill(Color.STEELBLUE);
			return null;
            
          
    	

            
        }

    }

    @Override
    public void start(Stage primaryStage) {

        //Represents the grid with Rectangles
        gridDisplay = new GridDisplay(2, 4);

        //Fields to specify number of rows/columns
        TextField rowField = new TextField("0");
        TextField columnField = new TextField("0");
        
        category.getItems().addAll(statusList);
        category.getSelectionModel().select(0);
        
        Button refresh = new Button("Refresh");

        //Function to set an action when text field loses focus
        buildTextFieldActions(rowField, columnField);

        HBox fields = new HBox(10);
       
        fields.getChildren().add(category);
        fields.getChildren().add(refresh);

        BorderPane mainPanel = new BorderPane();
        mainPanel.setCenter(gridDisplay.getDisplay());
        mainPanel.setTop(fields);

        Scene scene = new Scene(mainPanel, 800, 850);
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
