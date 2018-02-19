package testPackage;


import java.io.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import javafx.scene.control.TableColumn;


public class Main extends Application implements EventHandler<ActionEvent>  {


    String movies[] = imdbpulls.movies;
    private String word;
    private Crawl reddit = new Crawl();
    private SearchTweets twitter = new SearchTweets();
    Label title = new Label();
    Label details = new Label();
    Label synopsis = new Label();
    Label rating = new Label();
    Label Sentiment = new Label();
    Image img = new Image("http://laoblogger.com/images/images-white-2.jpg");
    ImageView imgView = new ImageView(img);
    TableView<TestData> reddit_table;
    TableView<twitterPassing> twitter_table;
    mews senti = new mews();
    NumberFormat formatter = new DecimalFormat("#0.00");

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Movies");
        TextField textfield = new TextField();
        textfield.setPrefWidth(300);
        textfield.setAlignment(Pos.CENTER);
        ObservableList<String> options = FXCollections.observableArrayList(
                "Past Month",
                "Past Year",
                "Past Week"
        );
        ComboBox timePeriod = new ComboBox(options);
        timePeriod.setPrefWidth(200);
        Button button = new Button("Search");
        button.setPrefWidth(80);
        button.setOnAction(e -> {
            try {
                isInt(textfield, textfield.getText(), timePeriod, timePeriod.getValue().toString());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });


        SplitPane spane = new SplitPane();
        spane.setOrientation(Orientation.VERTICAL);
        SplitPane spane2 = new SplitPane();
        spane2.setOrientation(Orientation.HORIZONTAL);
        imgView.setFitHeight(250);
        imgView.setFitWidth(200);

        //Comment Column for Reddit Table
        TableColumn commentColumn =  new TableColumn("Reddit Comments");
        commentColumn.setMinWidth(450);
        commentColumn.setMaxWidth(1000);
        commentColumn.setPrefWidth(450);
        commentColumn.setResizable(false);
        commentColumn.setCellValueFactory(new PropertyValueFactory<TestData, String>("reddit_comment"));

        // Date Created Column for Reddit Table
        TableColumn dateColumn = new TableColumn("Date Created");
        dateColumn.setMinWidth(150);
        dateColumn.setMaxWidth(250);
        dateColumn.setPrefWidth(150);
        dateColumn.setResizable(true);
        dateColumn.setCellValueFactory(new PropertyValueFactory<TestData, String>("dateCreated"));

        //sentiment Column for Reddit Table
        TableColumn senColumn = new TableColumn("Sentiments");
        senColumn.setMinWidth(150);
        senColumn.setMaxWidth(250);
        senColumn.setPrefWidth(150);
        senColumn.setResizable(true);
        senColumn.setCellValueFactory(new PropertyValueFactory<TestData, String>("senti"));


        this.reddit_table = new TableView<>();
        this.reddit_table.getColumns().addAll(commentColumn, dateColumn, senColumn);

        //Comment Column for twitter Table
        TableColumn commentColumn2 =  new TableColumn("Twitter Comments");
        commentColumn2.setMinWidth(450);
        commentColumn2.setMaxWidth(1000);
        commentColumn2.setPrefWidth(450);
        commentColumn2.setResizable(true);
        commentColumn2.setCellValueFactory(new PropertyValueFactory<twitterPassing, String>("twitter_comment"));

        // Date Created Column for Twitter Table
        TableColumn dateColumn2 = new TableColumn("Date Created");
        dateColumn2.setMinWidth(150);
        dateColumn2.setMaxWidth(250);
        dateColumn2.setPrefWidth(150);
        dateColumn2.setResizable(false);
        dateColumn2.setCellValueFactory(new PropertyValueFactory<twitterPassing, String>("twitter_date"));

        //sentiment Column for Twitter Table
        TableColumn twiSenColumn = new TableColumn("Sentiments");
        twiSenColumn.setMinWidth(150);
        twiSenColumn.setMaxWidth(250);
        twiSenColumn.setPrefWidth(150);
        twiSenColumn.setResizable(true);
        twiSenColumn.setCellValueFactory(new PropertyValueFactory<TestData, String>("twitter_sen"));

        this.twitter_table = new TableView<>();
        this.twitter_table.getColumns().addAll(commentColumn2, dateColumn2, twiSenColumn);

        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(textfield,timePeriod, button);


        HBox hbox2 = new HBox();
        //hbox2.getChildren().addAll(label5);
        hbox2.getChildren().addAll(this.reddit_table, this.twitter_table);

        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(title, details, synopsis, rating, Sentiment);

        VBox vbox2 = new VBox();
        vbox2.getChildren().add(imgView);


        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(vbox, spane2, vbox2);

        spane.getItems().add(hbox);
        spane.getItems().add(hbox2);
        spane.getItems().add(hbox3);
        spane2.getItems().add(vbox);
        spane2.getItems().add(vbox2);

        VBox root = new VBox();
        root.getChildren().addAll(hbox, hbox2, hbox3, spane);

        hbox.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.CENTER_RIGHT);

        hbox.setMinHeight(Region.USE_PREF_SIZE);
        hbox.setPrefHeight(60);
        hbox2.setMinHeight(Region.USE_PREF_SIZE);
        hbox2.setPrefHeight(375);
        hbox3.setMinHeight(Region.USE_PREF_SIZE);
        hbox3.setPrefHeight(250);
        vbox.setMinWidth(Region.USE_PREF_SIZE);
        vbox.setPrefWidth(1300);




        title.setStyle("-fx-font: BOLD 28 arial");
        details.setStyle("-fx-font: 14 arial");
        synopsis.setStyle("-fx-font: 14 arial");
        rating.setStyle("-fx-font: BOLD 32 arial");
        Sentiment.setStyle("-fx-font: 14 arial");

        Scene scene = new Scene(root, 1500, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private boolean isInt(TextField textfield, String text, ComboBox selct, String time)  throws SQLException {
 try {
            this.word = text;
            imdbpulls.keyword = text;
            imdbpulls.parse();
            this.reddit.keyWord = this.word;
            this.reddit.time = time;
            this.reddit.executeQueryforClear();
            this.reddit.StartCrawl();
            this.twitter.keyWord = text;
            this.twitter.StartCrawl();
            title.setText(movies[0]);
            details.setText(movies[1] + " | " + movies[2] + " | " + movies[3] + " | " + movies[4]);
            synopsis.setText(movies[5]);
            synopsis.setWrapText(true);
            rating.setText(movies[6] + "/10");
            imgView.setImage(img = new Image(movies[7]));
            this.reddit_table.setItems(getComments());
            this.twitter_table.setItems(getCommentsForTwitter());
            this.senti.calculate();
            Sentiment.setText("Positive Percentage: " + formatter.format(this.senti.getPositivePer())+"%"+" Negative Percentage: " + formatter.format(this.senti.getNegativePer())+ "%"+ " Neutral Percentage: " + formatter.format(this.senti.getNeutralPer()) + "%");


            return true;
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ObservableList<TestData> getComments() throws SQLException {
        ObservableList<TestData> commentsin = FXCollections.observableArrayList();
        try {
            this.reddit.executeQueryforSelecting();
            int i = 0;
            for (String com: this.reddit.comments) {
                this.senti.setSentence(com);
                commentsin.add(new TestData(com, this.reddit.dateCreate.get(i), this.senti.generateEach()));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR");
        }
        this.reddit.closeconn();
        return commentsin;
    }
    public ObservableList<twitterPassing> getCommentsForTwitter() throws SQLException {
        ObservableList<twitterPassing> commentsin = FXCollections.observableArrayList();
        this.twitter.sqlConnection();
        this.twitter.executeSelect();
        int i =0;
        for (String com: this.twitter.comments) {
            this.senti.setSentence(com);
            commentsin.add(new twitterPassing(com, this.twitter.dateCreate.get(i), this.senti.generateEach()));
            i++;
        }
        this.twitter.closeconn();
        return commentsin;
    }


    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void handle(ActionEvent event) {
        // TODO Auto-generated method stub

    }
}
