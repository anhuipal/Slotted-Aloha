package slottedAloha;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiFx extends Application {

    //Text area
    TextField probStartText = new TextField();
    TextField probIncriment = new TextField();
    TextField nodesText = new TextField();
    TextArea testResults = new TextArea();

    //Plots
    NumberAxis xAxisPlot1 = new NumberAxis();
    NumberAxis yAxisPlot1 = new NumberAxis();
    LineChart<Number, Number> lineChartPlot1 = new LineChart<Number, Number>(
            xAxisPlot1, yAxisPlot1);
    NumberAxis xAxisPlot2 = new NumberAxis();
    NumberAxis yAxisPlot2 = new NumberAxis();
    LineChart<Number, Number> lineChartPlot2 = new LineChart<Number, Number>(
            xAxisPlot2, yAxisPlot2);

    //Plot 2 Data
    XYChart.Series<Number, Number> seriesPlot2 = new XYChart.Series<Number, Number>();

    //Network
    static Network network;

    Scene mainScene;
    Stage window;

    @Override
    public void start(Stage primaryStage) {

        //Stage
        window = primaryStage;
        window.setTitle("Slotted Aloha Procotol Simulator");

        //Text Result
        testResults.setFont(Font.font("Verdana",16));
        testResults.setText("Please fill out the above fields and press the " + "'" + "Start Simulation" + "'" + " button.");
        testResults.setEditable(false);

        //Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu view = new Menu("View");

        MenuItem mainMenu = new MenuItem("Main Menu");
        MenuItem plot1Menu = new MenuItem("Plot 1");
        MenuItem plot2Menu = new MenuItem("Plot 2");

        view.getItems().add(mainMenu);
        view.getItems().add(plot1Menu);
        view.getItems().add(plot2Menu);

        Menu operations = new Menu("Operations");

        MenuItem exit = new MenuItem("Exit");
        MenuItem restart = new MenuItem("Restart");
        exit.setOnAction(e-> window.close() );
        restart.setOnAction(e-> this.restartApp() );

        operations.getItems().addAll(restart,exit);

        exit.setOnAction(e-> window.close());

        menuBar.getMenus().add(view);
        menuBar.getMenus().add(operations);

        //Buttons
        Button btnStart = new Button("Start Simulation");
        btnStart.setPrefWidth(300);
        Button btnPlot1 = new Button("Plot 1");
        btnPlot1.setPrefWidth(300);
        Button btnPlot2 = new Button("Plot 2");
        btnPlot2.setPrefWidth(300);
        Button mainMenuOp = new Button("Main Menu");
        mainMenuOp.setPrefWidth(300);
        Button restartBtn = new Button("Restart App");
        restartBtn.setPrefWidth(300);

        //Labels
        Label propStartTextL = new Label("Starting Propability : ");
        propStartTextL.setPrefWidth(500);
        Label propIncrimentL = new Label("Incriment of Propability as a % : ");
        propIncrimentL.setPrefWidth(500);
        Label nodesTextL = new Label("Number of Nodes : ");
        nodesTextL.setPrefWidth(500);

        //Main Scene layouts
        GridPane centerGrid = new GridPane();
        centerGrid.setPadding(new Insets(40,10,10,10));
        BorderPane mainSceneLayout = new BorderPane();
        mainSceneLayout.setPadding(new Insets(5,5,5,5));
        centerGrid.setHgap(8);
        centerGrid.setVgap(5);

        //TextPane
        probStartText.setPrefWidth(500);
        VBox textPane = new VBox(20);
        textPane.setPadding(new Insets(10,10,10,10));
        textPane.setAlignment(Pos.CENTER);
        textPane.getChildren().addAll(probStartText,probIncriment,nodesText);

        //Labels Pane
        VBox labelsPane = new VBox(30);
        labelsPane.setPadding(new Insets(10,10,10,10));
        labelsPane.setAlignment(Pos.CENTER);
        labelsPane.getChildren().addAll(propStartTextL,propIncrimentL,nodesTextL);

        //Grid Center Layout
        centerGrid.add(labelsPane, 0, 0);
        centerGrid.add(textPane,1,0);

        //Buttons Pane
        VBox btnPane = new VBox(20);
        btnPane.setPadding(new Insets(10,10,10,10));
        btnPane.getChildren().addAll(btnStart,btnPlot1,btnPlot2,mainMenuOp,restartBtn);
        btnPane.setAlignment(Pos.BASELINE_CENTER);

        mainSceneLayout.setLeft(btnPane);
        mainSceneLayout.setCenter(centerGrid);
        mainSceneLayout.setTop(menuBar);
        mainSceneLayout.setBottom(testResults);

        //Plot1
        xAxisPlot1.setLabel("Network Propability");
        yAxisPlot1.setLabel("Successfull Transmitions");

        lineChartPlot1.setTitle("Successfully Transmitions Over Network Propability");

        //Plot2
        xAxisPlot2.setLabel("Nodes");
        yAxisPlot2.setLabel("Successfull Transmitions");


        lineChartPlot2.setTitle("Successfully Transmitions Over the Number of Nodes");

        //Set Menus
        plot1Menu.setOnAction(e -> mainSceneLayout.setCenter(lineChartPlot1));
        plot2Menu.setOnAction(e -> mainSceneLayout.setCenter(lineChartPlot2));
        mainMenu.setOnAction(e -> mainSceneLayout.setCenter(centerGrid));

        //Set Buttons
        btnPlot1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // populating the series with data
                XYChart.Series<Number, Number> seriesPlot1 = new XYChart.Series<Number, Number>();
                for(Metrics m: network.getMetrics()){
                    seriesPlot1.getData().add(new XYChart.Data<Number, Number>(m.getP(), m.getSuccesses()));
                }
                seriesPlot1.setName("For : " + nodesText.getText() + " Nodes;");
                lineChartPlot1.getData().add(seriesPlot1);
                mainSceneLayout.setCenter(lineChartPlot1);
            }
        });
        restartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                restartApp();
            }
        });

        btnPlot2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                seriesPlot2.getData().add(new XYChart.Data<Number, Number>(network.getNodes(),network.getSucTotalTrans()));
                lineChartPlot2.getData().add(seriesPlot2);
                mainSceneLayout.setCenter(lineChartPlot2);
            }
        });

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                testResults.setText("");
                network = null;
                network = new Network(Double.parseDouble(probStartText.getText()),Integer.parseInt(probIncriment.getText()),Integer.parseInt(nodesText.getText()));
                network.init();
                network.testNetworkEfficiency();
                testResults.setText(network.printSet());
            }
        });

        mainMenuOp.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                mainSceneLayout.setCenter(centerGrid);
            }
        });

        //MainScene
        mainScene = new Scene(mainSceneLayout,900,700);
        //Center Window
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

        //Render window
        mainScene.setRoot(mainSceneLayout);
        mainScene.getStylesheets().add("slottedAloha/style.css");
        window.setScene(mainScene);
        window.show();

    }

    public void restartApp(){

        network = null;
        window.close();
        lineChartPlot1.getData().clear();
        lineChartPlot2.getData().clear();
        this.probStartText.setText("");
        this.probIncriment.setText("");
        this.nodesText.setText("");
        this.testResults.setText("");
        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
