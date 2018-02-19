package testPackage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.application.Application;


public class MainPage extends Application implements EventHandler<ActionEvent>{
    Stage window;
    Button button,button1;
    Scene scene;
    String[] bigMovie;
    public static void main(String[] args)
    {
        launch(args);

    }

    @Override
    public void start(Stage arg0) throws Exception {
        window = arg0;
        window.setTitle("Movie Rating");
        TextField nameInput = new TextField();
        button = new Button("Search Me");
        //button.setOnAction(e ->isInt(nameInput,nameInput.getText()));


        //button.setOnAction(e ->AlertBox.display("Error Message","Movie Cannot be Found"));
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(nameInput,button);
        scene = new Scene(layout,300,200);
        window.setScene(scene);
        window.show();


    }

    private boolean isInt(TextField nameInput, String text) {
        try {
            imdbpulls.keyword = text;

            bigMovie = imdbpulls.movies;
            imdbpulls.parse();
            for (String s: bigMovie) {
                System.out.println(s);
            }
            return true;
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void handle(ActionEvent event) {



    }




}
