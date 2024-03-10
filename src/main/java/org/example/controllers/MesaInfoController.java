package org.example.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.example.DBManager;
import org.example.models.Comanda;
import org.example.models.Mesa;
import org.example.models.Producto;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class MesaInfoController {
    //TODO: GENERAR INFORMES
    //TODO: HACER EJECUTABLE
    //TODO: HACER GITHUB
    @FXML
    private Label labelNumMesa;
    @FXML
    private ToggleButton btnEstadoMesa;
    @FXML
    private ListView<String> listViewTiposPlatos;
    @FXML
    private Label labelPrecioTotal;
    @FXML
    private Label labelCantidad;
    @FXML
    private Label labelPlatos;
    @FXML
    private Label labelProductoSelec;
    @FXML
    private ListView<String> listViewPlatos;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnGenerarHistorico;
    @FXML
    private Button btnFactura;
    private Mesa selectedMesa;
    private DBManager dbManager;
    private MesasController mesasController;
    private VBox vboxAddPlato;
    private List<Producto> productosSeleccionados;
    private Comanda comanda;

    public void setMesasController(MesasController mesasController) {
        this.mesasController = mesasController;
    }

    private void setLabels() {
        if (selectedMesa.getEstado().equals("Ocupada")) {
            labelNumMesa.setText("MESA Nº " + selectedMesa.getNum_mesa());
        } else {
            labelNumMesa.setText("MESA Nº " + selectedMesa.getNum_mesa());
        }
        labelNumMesa.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        labelPrecioTotal.setText("Precio total de la cuenta: " + comanda.getPreciototal() + "€");
        labelCantidad.setText("Cantidad de productos pedidos: " + comanda.getProductos().size());
        labelPlatos.setText("Lista de platos");
        labelProductoSelec.setText("Ningún producto seleccionado");
    }

    public void setDefaultBtnEstadoMesa(boolean estadoMesa) {
        if (estadoMesa) {
            Color redColor = Color.rgb(214, 23, 23);
            BackgroundFill backgroundFill = new BackgroundFill(redColor, new CornerRadii(10), Insets.EMPTY);

            Background background = new Background(backgroundFill);
            btnEstadoMesa.setBackground(background);
            btnEstadoMesa.setText("OCUPADO");
        } else {
            Color greenColor = Color.rgb(50, 205, 50);
            BackgroundFill backgroundFill = new BackgroundFill(greenColor, new CornerRadii(10), Insets.EMPTY);

            Background background = new Background(backgroundFill);
            btnEstadoMesa.setBackground(background);
            btnEstadoMesa.setText("Libre");
        }
    }


    public void setListViewTiposPlatos() {
        listViewTiposPlatos.setCellFactory(param -> new ListCell<String>() {
            private HBox content = new HBox();
            private ImageView imageView = new ImageView();
            private Text text = new Text();

            {
                content.getChildren().addAll(imageView, text);
                content.setSpacing(10);
                content.setAlignment(Pos.CENTER_LEFT);
                setOnMouseClicked(event -> {
                    updateListViewPlatos(text.getText());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                listViewTiposPlatos.setStyle("-fx-background-color: transparent;");
                if (empty || item == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    String[] parts = item.split(":");
                    String imagePath = parts[0];
                    String tipoPlato = parts[1];

                    Image image = new Image(new File(imagePath).toURI().toString());

                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);

                    imageView.setImage(image);
                    text.setText(tipoPlato);

                    //TODO: CAMBIAR EL ALTERNAR COLORES
                    String backgroundColor = getIndex() % 2 == 0 ? "#E0E0E0" : "#F0F0F0"; // Alternar colores
                    content.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));

                    text.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

                    setGraphic(content);
                }
            }
        });

        listViewTiposPlatos.getItems().addAll(
                "src/main/resources/org/example/plato_principal.png:Platos principales",
                "src/main/resources/org/example/entrante.png:Entrantes",
                "src/main/resources/org/example/bebidas.png:Bebidas",
                "src/main/resources/org/example/guarnicion.png:Guarniciones",
                "src/main/resources/org/example/postre.png:Postres"
        );
    }

    private void updateListViewPlatos(String tipoPlato) {
        listViewPlatos.setCellFactory(param -> new ListCell<String>() {
            private HBox content = new HBox();
            private ImageView imageView = new ImageView();
            private Text text = new Text();

            {
                content.getChildren().addAll(imageView, text);
                content.setSpacing(10);
                content.setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                listViewPlatos.setStyle("-fx-background-color: transparent;");
                if (empty || item == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    String[] parts = item.split(":");
                    String imagePath = parts[0];
                    String platoNombre = parts[1];

                    Image image = new Image(new File(imagePath).toURI().toString());

                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);

                    imageView.setImage(image);
                    text.setText(platoNombre);

                    // TODO: CAMBIAR EL ALTERNAR COLORES
                    String backgroundColor = getIndex() % 2 == 0 ? "#E0E0E0" : "#F0F0F0"; // Alternar colores
                    content.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));

                    text.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

                    setGraphic(content);
                }
            }
        });

        listViewPlatos.getItems().setAll(getPlatosByTipo(tipoPlato));
    }

    private List<String> getPlatosByTipo(String tipoPlato) {

        List<String> platos = new ArrayList<>();
        switch (tipoPlato) {
            case "Platos principales":
                platos.addAll(List.of("src/main/resources/org/example/polloalcurry.png:Pollo al curry",
                        "src/main/resources/org/example/pastaalfredo.png:Pasta alfredo con champiñones",
                        "src/main/resources/org/example/salmonparrilla.png:Salmón a la parrilla",
                        "src/main/resources/org/example/risottochampiñones.png:Risotto de champiñones",
                        "src/main/resources/org/example/tacoscarne.png:Tacos de carne"));
                break;
            case "Entrantes":
                platos.addAll(List.of("src/main/resources/org/example/rollitosprimavera.png:Rollitos de primavera",
                        "src/main/resources/org/example/cevichedepescado.png:Ceviche de pescado",
                        "src/main/resources/org/example/sopa.png:Sopa",
                        "src/main/resources/org/example/calamares.png:Ración de calamares",
                        "src/main/resources/org/example/hummus.png:Hummus con vegetales"));
                break;
            case "Bebidas":
                platos.addAll(List.of("src/main/resources/org/example/agua.png:Agua",
                        "src/main/resources/org/example/cafe.png:Cafe",
                        "src/main/resources/org/example/mojito.png:Mojito",
                        "src/main/resources/org/example/cocacola.png:Coca-Cola",
                        "src/main/resources/org/example/tehelado.png:Té helado"));
                break;
            case "Guarniciones":
                platos.addAll(List.of("src/main/resources/org/example/arroz.png:Arroz",
                        "src/main/resources/org/example/ensalada.png:Ensalada",
                        "src/main/resources/org/example/vegetalesasados.png:Vegetales asados",
                        "src/main/resources/org/example/quinoafrutossecos.png:Quinoa con frutos secos",
                        "src/main/resources/org/example/purepatatas.png:Puré de patatas"));
                break;
            case "Postres":
                platos.addAll(List.of("src/main/resources/org/example/tartamanzana.png:Tarta de manzana",
                        "src/main/resources/org/example/brownie.png:Brownie de chocolate",
                        "src/main/resources/org/example/heladocasero.png:Helado casero",
                        "src/main/resources/org/example/crepesnutellaplatano.png:Crepes de nutella y platano",
                        "src/main/resources/org/example/tiramisu.png:Tiramisú"));
                break;
        }
        return platos;
    }

    private void actualizarPrecioTotal() {
        double precioTotal = 0.0;
        for (Producto producto : comanda.getProductos()) {
            precioTotal += producto.getPrecio();
        }
        comanda.setPreciototal(precioTotal);
        labelPrecioTotal.setText("Precio total de la cuenta: " + comanda.getPreciototal() + "€");
        labelCantidad.setText("Cantidad de productos pedidos: " + comanda.getProductos().size());
        String item = listViewPlatos.getSelectionModel().getSelectedItem();
        String[] parts = item.split(":");
        String platoNombre = parts[1].trim();
        labelProductoSelec.setText("Producto seleccionado: " + platoNombre);
    }

    private void setPrecioTotalComanda() {
        double preciototal = 0.0;
        for (Producto producto : comanda.getProductos()) {
            preciototal += producto.getPrecio();
        }
        comanda.setPreciototal(preciototal);
    }

    private void setSyleBtns() {
        Image image = new Image(new File("src/main/resources/org/example/add.png").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);

        btnAdd.setGraphic(vbox);
        btnAdd.setBackground(Background.EMPTY);

        image = new Image(new File("src/main/resources/org/example/remove.png").toURI().toString());
        imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        vbox = new VBox();
        vbox.getChildren().addAll(imageView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        btnRemove.setGraphic(vbox);
        btnRemove.setBackground(Background.EMPTY);

        btnEstadoMesa.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        setDefaultBtnEstadoMesa(selectedMesa.getEstado().equals("Ocupada"));

        Color greenColor = Color.rgb(50, 205, 50);
        BackgroundFill backgroundFill = new BackgroundFill(greenColor, new CornerRadii(10), Insets.EMPTY);

        Background background = new Background(backgroundFill);
        btnGenerarHistorico.setBackground(background);

        Color blueColor = Color.rgb(50, 50, 205);
        backgroundFill = new BackgroundFill(blueColor, new CornerRadii(10), Insets.EMPTY);

        background = new Background(backgroundFill);
        btnFactura.setBackground(background);
    }

    private void setBtnsActions() {
        btnAdd.setOnAction(event -> {
            String item = listViewPlatos.getSelectionModel().getSelectedItem();
            if (item != null) {
                String[] parts = item.split(":");
                String platoNombre = parts[1];
                if (platoNombre != null) {
                    Producto producto = dbManager.getProductoByNombre(platoNombre);
                    productosSeleccionados.add(producto);
                    comanda.addProducto(producto);
                    dbManager.addProductoToComanda(selectedMesa.getId(), producto.getId(), 1, producto.getPrecio());
                    actualizarPrecioTotal();
                }
            }
        });

        btnRemove.setOnAction(event -> {
            String item = listViewPlatos.getSelectionModel().getSelectedItem();
            if (item != null) {
                String[] parts = item.split(":");
                String platoNombre = parts[1].trim();

                Producto productoARemover = null;
                for (Producto producto : comanda.getProductos()) {
                    if (producto.getNombre().trim().equalsIgnoreCase(platoNombre)) {
                        productoARemover = producto;
                        break;
                    }
                }

                if (productoARemover != null) {
                    productosSeleccionados.remove(productoARemover);
                    comanda.removeProducto(productoARemover);
                    dbManager.removeProductoComanda(selectedMesa.getId(), productoARemover.getId());
                    actualizarPrecioTotal();
                }
            }
        });

        btnEstadoMesa.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    Color redColor = Color.rgb(214, 23, 23);
                    BackgroundFill backgroundFill = new BackgroundFill(redColor, new CornerRadii(10), Insets.EMPTY);

                    Background background = new Background(backgroundFill);
                    btnEstadoMesa.setBackground(background);
                    btnEstadoMesa.setText("OCUPADO");
                    selectedMesa.setEstado("Ocupada");
                    dbManager.setEstadoMesa(selectedMesa.getId(), "Ocupada");
                    mesasController.updateMesaVisualState(selectedMesa);
                } else {
                    Color greenColor = Color.rgb(50, 205, 50);
                    BackgroundFill backgroundFill = new BackgroundFill(greenColor, new CornerRadii(10), Insets.EMPTY);

                    Background background = new Background(backgroundFill);
                    btnEstadoMesa.setBackground(background);
                    btnEstadoMesa.setText("Libre");
                    selectedMesa.setEstado("Vacia");
                    dbManager.setEstadoMesa(selectedMesa.getId(), "Vacia");
                    mesasController.updateMesaVisualState(selectedMesa);
                }
            }
        });

        btnFactura.setOnAction(event -> {
            if (comanda.getProductos().isEmpty()) {
                return;
            }

            // Guardar historial de la mesa
            dbManager.guardarHistorialMesa(selectedMesa.getId(), selectedMesa.getEstado(), "Vacia");

            // Limpiar la comanda y actualizar la interfaz
            productosSeleccionados.clear();
            comanda.getProductos().clear();
            comanda.setPreciototal(0.0);
            comanda.setCantidad(0);
            dbManager.removeComanda(selectedMesa.getId());

            // Actualizar etiquetas y estado de la mesa
            setLabels();
            setDefaultBtnEstadoMesa(true);

            generateFactura();

            // Mensaje de éxito o realizar otras acciones
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Factura generada");
            alert.setHeaderText(null);
            alert.setContentText("Se ha generado la factura y limpiado la mesa correctamente.");
            alert.showAndWait();
        });

        btnGenerarHistorico.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                generateHistorico();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Historico  generada");
                alert.setHeaderText(null);
                alert.setContentText("Se ha generado el histórico de la mesa correctamente.");
                alert.showAndWait();
            }
        });
    }

    public void generateFactura() {
        try {
            JasperPrint jasperPrint;
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_mesaid", selectedMesa.getId());

            InputStream inputStream = getClass().getResourceAsStream("/org/example/facturabar.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dbManager.conexionDB());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void generateHistorico() {
        try {
            JasperPrint jasperPrint;
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_mesaid", selectedMesa.getId());

            InputStream inputStream = getClass().getResourceAsStream("/org/example/historico.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dbManager.conexionDB());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(Mesa mesa) {
        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.selectedMesa = mesa;

        if (comanda == null) {
            productosSeleccionados = new ArrayList<>();
            comanda = new Comanda();
            List<Producto> productosEnComanda = dbManager.getProductosOnComanda(selectedMesa.getId());
            comanda.setProductos(productosEnComanda);
            setPrecioTotalComanda();
        }

        setSyleBtns();
        setLabels();
        setBtnsActions();
        setListViewTiposPlatos();
    }

}
