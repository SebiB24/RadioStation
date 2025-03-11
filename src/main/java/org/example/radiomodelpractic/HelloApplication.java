package org.example.radiomodelpractic;

import Domain.Piesa;
import Repo.SQLRepository;
import Service.PiesaService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.w3c.dom.events.EventException;

import javax.swing.plaf.basic.BasicTreeUI;
import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        SQLRepository repoP = new SQLRepository();
        PiesaService servP = new PiesaService(repoP);

        HBox mainBox = new HBox();

        HBox leftSideBox = new HBox();

        ObservableList<Piesa> PiesaList = FXCollections.observableArrayList(servP.getAll());

        /// Crearea LISTA pentru afisarea datelor

        ListView<Piesa> PieseListView = new ListView<>();
        PieseListView.setItems(PiesaList);

        PieseListView.setMinWidth(275);
        PieseListView.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(PieseListView, Priority.ALWAYS);


        /// Creare TABEL pentru afisarea datelor
        /// !!! ADAUGA (opens Domain to javafx.base;) IN (module-info.java) !!!!!!

        TableView<Piesa> PieseTable = new TableView<>();
        //creem cate o coloana pe rand
        //textul din paranteze este header-ul
        TableColumn<Piesa, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Piesa, String> formatieColumn = new TableColumn<>("Formatie");
        TableColumn<Piesa, String> titluColumn = new TableColumn<>("Titlu");
        TableColumn<Piesa, String> genColumn = new TableColumn<>("Gen");
        TableColumn<Piesa, String> durataColumn = new TableColumn<>("Durata");

        //specificam cum se vor completa coloanele - ce camp dintr-un
        //obiect Musician vine pe fiecare coloana

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        formatieColumn.setCellValueFactory(new PropertyValueFactory<>("formatie"));
        titluColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        genColumn.setCellValueFactory(new PropertyValueFactory<>("gen"));
        durataColumn.setCellValueFactory(new PropertyValueFactory<>("durata"));

        //adaugam coloanele la tabel
        PieseTable.getColumns().add(idColumn);
        PieseTable.getColumns().add(formatieColumn);
        PieseTable.getColumns().add(titluColumn);
        PieseTable.getColumns().add(genColumn);
        PieseTable.getColumns().add(durataColumn);


        //creem o lista de musicians care se vor afisa in tabel
        PieseTable.setItems(PiesaList);
        PieseTable.setMaxWidth(300);

        /// Adaugare Tabel/Lista in LBox

        leftSideBox.getChildren().add(PieseTable);
        leftSideBox.setPadding(new Insets(10, 10, 10, 10));

        /// Adaugare LBox in MBox

        mainBox.getChildren().add(leftSideBox);


        VBox CenterBox = new VBox();

        GridPane labelsAndFieldsPane = new GridPane();
        labelsAndFieldsPane.setVgap(3.5);
        labelsAndFieldsPane.setHgap(3.5);
        Label idLabel = new Label("ID");
        Label formatieLabel = new Label("Formatie");
        Label titluLabel = new Label("Titlu");
        Label genLabel = new Label("Gen");
        Label durataLabel = new Label("Durata");
        TextField idTextField = new TextField();
        TextField formatieTextField = new TextField();
        TextField titluTextField = new TextField();
        TextField genTextField = new TextField();
        TextField durataTextField = new TextField();

        labelsAndFieldsPane.add(idLabel, 0, 0);
        labelsAndFieldsPane.add(idTextField, 1, 0);
        labelsAndFieldsPane.add(formatieLabel, 0, 1);
        labelsAndFieldsPane.add(formatieTextField, 1, 1);
        labelsAndFieldsPane.add(titluLabel, 0, 2);
        labelsAndFieldsPane.add(titluTextField, 1, 2);
        labelsAndFieldsPane.add(genLabel, 0, 3);
        labelsAndFieldsPane.add(genTextField, 1, 3);
        labelsAndFieldsPane.add(durataLabel, 0, 4);
        labelsAndFieldsPane.add(durataTextField, 1, 4);


        CenterBox.getChildren().add(labelsAndFieldsPane);

        //new HBox pentru butoane
        HBox buttonBox = new HBox();
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");

        buttonBox.getChildren().add(addButton);
        buttonBox.getChildren().add(deleteButton);
        buttonBox.getChildren().add(updateButton);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        buttonBox.setSpacing(10);


        CenterBox.getChildren().add(buttonBox);

        CenterBox.setSpacing(15);
        CenterBox.setPadding(new Insets(10, 10, 10, 10));

        mainBox.getChildren().add(CenterBox);

        VBox RightSideBox = new VBox();
        RightSideBox.setPadding(new Insets(10, 10, 10, 10));
        RightSideBox.setSpacing(5);

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        Button resetButton = new Button("Reset");
        searchButton.setMaxWidth(Double.MAX_VALUE);
        resetButton.setMaxWidth(Double.MAX_VALUE);

        RightSideBox.getChildren().add(searchField);
        RightSideBox.getChildren().add(searchButton);
        RightSideBox.getChildren().add(resetButton);

        TextField createField = new TextField();
        Button createButton = new Button("Create List");
        createButton.setMaxWidth(Double.MAX_VALUE);

        RightSideBox.getChildren().add(createField);
        RightSideBox.getChildren().add(createButton);

        mainBox.getChildren().add(RightSideBox);


        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    int id = Integer.parseInt(idTextField.getText());
                    String formatie = formatieTextField.getText();
                    String titlu = titluTextField.getText();
                    String gen = genTextField.getText();
                    String durata = durataTextField.getText();
                    servP.add(id, formatie, titlu, gen, durata);
                    PiesaList.setAll(servP.getAll());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }

                System.out.println("We pressed the add button.");
            }
        });

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int id = Integer.parseInt(idTextField.getText());
                    servP.removeById(id);
                    PiesaList.setAll(servP.getAll());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }
                System.out.println("We pressed the delete button.");
            }
        });

        updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int id = Integer.parseInt(idTextField.getText());
                    String formatie = formatieTextField.getText();
                    String titlu = titluTextField.getText();
                    String gen = genTextField.getText();
                    String durata = durataTextField.getText();
                    servP.update(id, formatie, titlu, gen, durata);
                    PiesaList.setAll(servP.getAll());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }
                System.out.println("We pressed the update button.");
            }
        });

        PieseTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        PieseTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Piesa selectedPiesa = (Piesa) PieseTable.getSelectionModel().getSelectedItem();
                idTextField.setText(String.valueOf(selectedPiesa.getId()));
                formatieTextField.setText(selectedPiesa.getFormatie());
                titluTextField.setText(selectedPiesa.getTitlu());
                genTextField.setText(selectedPiesa.getGen());
                durataTextField.setText(selectedPiesa.getDurata());
            }
        });

        searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    String text = searchField.getText();
                    if(servP.search(text).isEmpty()){
                        throw new Exception("Piesa nu a putut fi gasita :(");
                    }
                    PiesaList.setAll(servP.search(text));
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Cautare Esuata");
                    alert.setContentText("Motiv: " + e.getMessage());
                    alert.show();
                }
            }
        });

        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PiesaList.setAll(servP.getAll());
            }
        });

        createButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    String nume = createField.getText();
                    servP.create(nume);
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }
                System.out.println("We pressed the create button.");
            }
        });

        Scene scene = new Scene(mainBox, 750, 400);
        stage.setTitle("Radio App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}