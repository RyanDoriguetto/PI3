package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Endereco;
import repository.Conexao;
import service.EnderecoService;
import service.UsuarioService;
import util.UsuarioUtil;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroUsuarioView {


        Stage stage = new Stage();
        stage.setTitle("Cadastro de Usuário");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        TextField nomeField = new TextField();
        TextField cpfField = new TextField();
        TextField telefoneField = new TextField();
        DatePicker dataNascPicker = new DatePicker();

        TextField ruaField = new TextField();
        TextField quadraField = new TextField();
        TextField loteField = new TextField();
        TextField bairroField = new TextField();
        TextField cidadeField = new TextField();
        TextField estadoField = new TextField();

        Button salvarBtn = new Button("Salvar");

        root.getChildren().addAll(
                new Label("Nome:"), nomeField,
                new Label("CPF:"), cpfField,
                new Label("Telefone:"), telefoneField,
                new Label("Data de Nascimento (dd/mm/aaaa):"), dataNascPicker,
                new Label("Rua:"), ruaField,
                new Label("Quadra:"), quadraField,
                new Label("Lote:"), loteField,
                new Label("Bairro:"), bairroField,
                new Label("Cidade:"), cidadeField,
                new Label("Estado:"), estadoField,
                salvarBtn
        );

        cpfField.textProperty().addListener((obs, oldValue, newValue) -> {
            cpfField.setText(util.UsuarioUtil.aplicarMascaraCPF(newValue));
            cpfField.positionCaret(cpfField.getText().length());
        });

        telefoneField.textProperty().addListener((obs, oldValue, newValue) -> {
            telefoneField.setText(util.UsuarioUtil.aplicarMascaraTelefone(newValue));
            telefoneField.positionCaret(telefoneField.getText().length());
        });

        dataNascPicker.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            String mascarado = util.UsuarioUtil.aplicarMascaraData(newValue);
            dataNascPicker.getEditor().setText(mascarado);
            dataNascPicker.getEditor().positionCaret(mascarado.length());
        });

        salvarBtn.setOnAction(event -> {
            try {
                String dataStr = dataNascPicker.getEditor().getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataNasc = LocalDate.parse(dataStr, formatter);

                String cpfLimpo = cpfField.getText().replaceAll("[^0-9]", "");
                if (!UsuarioUtil.validarCpf(cpfLimpo)) {
                    throw new IllegalArgumentException("CPF Inválido");
                }

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

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Sucesso");
                alert.setContentText("Usuário cadastrado com sucesso!");
                alert.showAndWait();
                stage.close();

            } catch (DateTimeParseException e) {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setHeaderText("Erro");
                erro.setContentText("Data inválida! Use o formato dd/mm/aaaa.");
                erro.showAndWait();
            } catch (Exception e) {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setHeaderText("Erro");
                erro.setContentText("Falha ao salvar: " + e.getMessage());
                erro.showAndWait();
            }
        });

        stage.setScene(new Scene(root, 400, 700));
        stage.show();
    }
}
