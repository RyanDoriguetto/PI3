package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Usuario;
import repository.Conexao;
import service.EnderecoService;
import service.UsuarioService;
import util.UsuarioUtil;
import view.App;

import java.util.Objects;
import java.util.Optional;

public class TelaInicialController {

    @FXML
    private ImageView imagemTeatro;
    @FXML
    private Label imagemErro;
    @FXML
    private Button btnCadastro;
    @FXML
    private Button btnComprar;
    private UsuarioService usuarioService;

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
        try {
            usuarioService = new UsuarioService(Conexao.getConexao(), new EnderecoService(Conexao.getConexao()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao inicializar serviço de usuário.").showAndWait();
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Identificação");
        dialog.setHeaderText("Digite seu CPF para prosseguir:");
        dialog.setContentText("CPF:");

        TextField input = dialog.getEditor();
        input.textProperty().addListener((obs, oldVal, newVal) -> {
            input.setText(UsuarioUtil.aplicarMascaraCPF(newVal));
            input.positionCaret(input.getText().length());
        });

        dialog.showAndWait().ifPresent(cpfComMascara -> {
            String cpfSemMascara = cpfComMascara.replaceAll("[^\\d]", "");

            if (!UsuarioUtil.validarCpf(cpfSemMascara)) {
                new Alert(Alert.AlertType.ERROR, "CPF inválido.").showAndWait();
                return;
            }

            Usuario usuario = usuarioService.buscarPorCpf(cpfSemMascara);

            if (usuario != null) {
                App.setUsuarioLogado(usuario); // se estiver usando controle de sessão
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SelecaoCompraView.fxml"));
                    Parent root = loader.load();
                    App.rootPane.getChildren().setAll(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de compra.").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "CPF não cadastrado. Redirecionando para o cadastro...").showAndWait();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CadastroUsuarioView.fxml"));
                    Parent root = loader.load();
                    App.rootPane.getChildren().setAll(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de cadastro.").showAndWait();
                }
            }
        });
    }

    @FXML
    public void handleImpressaoIngresso() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/ImpressaoIngressoView.fxml"));
            javafx.scene.Parent root = loader.load();
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Erro ao carregar tela de impressão do ingresso.").showAndWait();
        }
    }

    @FXML
    public void handleEstatistica() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/EstatisticaView.fxml"));
            javafx.scene.Parent root = loader.load();
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Erro ao carregar tela de Estatísticas.").showAndWait();
        }
    }
}
