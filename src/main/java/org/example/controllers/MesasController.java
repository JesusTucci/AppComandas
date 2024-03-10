package org.example.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.DBManager;
import org.example.models.Mesa;

public class MesasController {

    @FXML
    private GridPane gridPane;
    private List<Mesa> mesas;
    private Mesa selectedMesa;
    private DBManager dbManager;

    private void showTables() {
        gridPane.getChildren().clear();
        mesas = dbManager.getMesas();

        int numMesa = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                numMesa++;
                Button button = createButton(numMesa);
                gridPane.add(button, col, row);
            }
        }
    }

    public Button createButton(int numMesa) {
        Button button = new Button();

        Image image = new Image(new File("src/main/resources/org/example/mesa_redonda.png").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        Label label = new Label(String.valueOf(numMesa));
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, label);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);

        button.setGraphic(vbox);

        BackgroundFill backgroundFill = null;
        if (mesas.get(numMesa - 1).getEstado().equals("Vacia")) {
            Color greenColor = Color.rgb(50, 205, 50);
            backgroundFill = new BackgroundFill(greenColor, new CornerRadii(10), Insets.EMPTY);
        } else {
            Color redColor = Color.rgb(214, 23, 23);
            backgroundFill = new BackgroundFill(redColor, new CornerRadii(10), Insets.EMPTY);
        }
        Background background = new Background(backgroundFill);
        button.setBackground(background);

        VBox.setMargin(button, new Insets(10));

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Mesa selectedMesa = getSelectedTable(numMesa);
                setMesaInfo(selectedMesa);
            }
        });

        return button;
    }


    private Mesa getSelectedTable(int numMesa) {
        for (Mesa mesa : mesas) {
            if (mesa.getNum_mesa() == numMesa) {
                return mesa;
            }
        }
        return null;
    }

    private void setMesaInfo(Mesa selectedMesa) {
        this.selectedMesa = selectedMesa;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mesa_info.fxml"));
            Parent root = loader.load();
            MesaInfoController mesaInfoController = loader.getController();

            mesaInfoController.setMesasController(this);

            mesaInfoController.initialize(selectedMesa);
            Stage appStage = new Stage();
            appStage.setMaximized(true);
            appStage.setScene(new Scene(root));
            appStage.setTitle("INFO MESA " + selectedMesa.getNum_mesa());
            appStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initialize() {
        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mesas = new ArrayList<>();
        showTables();
    }

    // Agrega este método a MesasController
    public void updateMesaVisualState(Mesa mesa) {
        int numMesa = mesa.getNum_mesa();
        Button mesaButton = getMesaButton(numMesa);

        if (mesa.getEstado().equals("Ocupada")) {
            // Cambiar fondo a rojo
            Color redColor = Color.rgb(214, 23, 23);
            BackgroundFill backgroundFill = new BackgroundFill(redColor, new CornerRadii(10), Insets.EMPTY);
            Background background = new Background(backgroundFill);
            mesaButton.setBackground(background);
        } else {
            // Cambiar fondo a verde
            Color greenColor = Color.rgb(50, 205, 50);
            BackgroundFill backgroundFill = new BackgroundFill(greenColor, new CornerRadii(10), Insets.EMPTY);
            Background background = new Background(backgroundFill);
            mesaButton.setBackground(background);
        }
    }

    private Button getMesaButton(int numMesa) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                Label label = (Label) ((VBox) button.getGraphic()).getChildren().get(1);
                int mesaNum = Integer.parseInt(label.getText());
                if (mesaNum == numMesa) {
                    return button;
                }
            }
        }
        return null;
    }

}
