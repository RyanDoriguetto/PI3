package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.App;

import java.util.Objects;

public class TelaInicialController {

    @FXML private ImageView imagemTeatro;
    @FXML private Label imagemErro;
    @FXML private Button btnCadastro;
    @FXML private Button btnComprar;

    @FXML
    public void initialize() {
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/layoutTeatro.jpg")));
            imagemTeatro.setImage(img);
            imagemErro.setVisible(false);
        } catch (Exception e) {
            imagemTeatro.setVisible(false);
            imagemErro.setVisible(true);
        }
    }

    @FXML
    public void handleCadastro() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/CadastroUsuarioView.fxml"));
            javafx.scene.Parent root = loader.load();
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Erro ao carregar tela de cadastro.").showAndWait();
        }
    }

    @FXML
    public void handleCompraIngresso() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/SelecaoCompraView.fxml"));
            javafx.scene.Parent root = loader.load();
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Erro ao carregar tela de compra.").showAndWait();
        }
    }
}
