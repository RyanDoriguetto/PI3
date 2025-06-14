package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.Endereco;
import repository.Conexao;
import service.EnderecoService;
import service.UsuarioService;
import util.UsuarioUtil;
import view.App;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroUsuarioController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField telefoneField;
    @FXML
    private DatePicker dataNascPicker;

    @FXML
    private TextField ruaField;
    @FXML
    private TextField quadraField;
    @FXML
    private TextField loteField;
    @FXML
    private TextField bairroField;
    @FXML
    private TextField cidadeField;
    @FXML
    private TextField estadoField;

    @FXML
    public void initialize() {
        cpfField.textProperty().addListener((obs, oldVal, newVal) -> {
            String formatted = UsuarioUtil.aplicarMascaraCPF(newVal);
            cpfField.setText(formatted);
            cpfField.positionCaret(formatted.length());
        });

        telefoneField.textProperty().addListener((obs, oldVal, newVal) -> {
            String formatted = UsuarioUtil.aplicarMascaraTelefone(newVal);
            telefoneField.setText(formatted);
            telefoneField.positionCaret(formatted.length());
        });

        dataNascPicker.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            String formatted = UsuarioUtil.aplicarMascaraData(newVal);
            dataNascPicker.getEditor().setText(formatted);
            dataNascPicker.getEditor().positionCaret(formatted.length());
        });
    }

    @FXML
    public void salvarUsuario() {
        try {
            String dataStr = dataNascPicker.getEditor().getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNasc = LocalDate.parse(dataStr, formatter);

            String cpfLimpo = cpfField.getText().replaceAll("[^0-9]", "");
            if (!UsuarioUtil.validarCpf(cpfLimpo)) throw new IllegalArgumentException("CPF inválido");

            Endereco endereco = new Endereco(
                    ruaField.getText(),
                    quadraField.getText(),
                    loteField.getText(),
                    bairroField.getText(),
                    cidadeField.getText(),
                    estadoField.getText()
            );

            Connection conexao = Conexao.getConexao();
            EnderecoService enderecoService = new EnderecoService(conexao);
            enderecoService.salvarEndereco(endereco);

            UsuarioService usuarioService = new UsuarioService(conexao, enderecoService);
            usuarioService.salvarUsuario(
                    endereco,
                    nomeField.getText(),
                    cpfLimpo,
                    telefoneField.getText().replaceAll("[^0-9]", ""),
                    dataNasc
            );

            new Alert(AlertType.INFORMATION, "Usuário cadastrado com sucesso!", ButtonType.OK).showAndWait();
            trocarParaTelaInicial();

        } catch (DateTimeParseException e) {
            new Alert(AlertType.ERROR, "Data inválida! Use o formato dd/mm/aaaa.", ButtonType.OK).showAndWait();
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Falha ao salvar: " + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    @FXML
    public void voltarTelaInicial() {
        trocarParaTelaInicial();
    }

    private void trocarParaTelaInicial() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/TelaInicialView.fxml"));
            javafx.scene.Parent root = loader.load();
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "Erro ao carregar a tela inicial.", ButtonType.OK).showAndWait();
        }
    }
}
