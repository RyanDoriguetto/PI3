package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ingresso.Ingresso;
import model.Usuario;
import repository.Conexao;
import repository.IngressoRepository;
import service.*;
import util.UsuarioUtil;
import view.App;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ImpressaoIngressoController implements Initializable {

    @FXML
    public Button btnCancelar;
    @FXML
    private TableView<Ingresso> tabelaIngressos;
    @FXML private TableColumn<Ingresso, Integer> colId;
    @FXML private TableColumn<Ingresso, String> colHorario;
    @FXML private TableColumn<Ingresso, String> colArea;
    @FXML private TableColumn<Ingresso, String> colPosicaoPoltrona;
    @FXML private TableColumn<Ingresso, String> colValorPago;

    private IngressoService ingressoService;
    private UsuarioService usuarioService;
    private SessaoService sessaoService;
    private AreaService areaService;

    private ObservableList<Ingresso> ingressosObservable = FXCollections.observableArrayList();

    private Usuario usuarioLogado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            var conexao = Conexao.getConexao();

            EnderecoService enderecoService = new EnderecoService(conexao);
            usuarioService = new UsuarioService(conexao, enderecoService);
            sessaoService = new SessaoService(conexao, new PecaService(Conexao.getConexao()), new HorarioService(Conexao.getConexao()));
            areaService = new AreaService(conexao);
            ingressoService = new IngressoService(
                    new IngressoRepository(conexao, usuarioService, sessaoService, areaService),
                    areaService.getTodasAreas()
            );

            pedirCpf();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar serviços: " + e.getMessage(), e);
        }
    }

    private void pedirCpf() {
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
                showAlert(Alert.AlertType.ERROR, "CPF inválido.");
                pedirCpf();
                return;
            }

            usuarioLogado = usuarioService.buscarPorCpf(cpfSemMascara);

            if (usuarioLogado == null) {
                showAlert(Alert.AlertType.INFORMATION, "CPF não cadastrado. Redirecionando para o cadastro...");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CadastroUsuarioView.fxml"));
                    Parent root = loader.load();
                    App.rootPane.getChildren().setAll(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Erro ao carregar tela de cadastro.");
                }
                return;
            }

            try {
                carregarIngressosUsuario();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void carregarIngressosUsuario() throws SQLException {
        List<Ingresso> ingressos = ingressoService.buscarPorUsuario(usuarioLogado);

        if (ingressos.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum ingresso válido para este CPF.");
            tabelaIngressos.setItems(FXCollections.emptyObservableList());
            return;
        }

        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdIngresso()).asObject());

        colArea.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getArea().getNome()));

        colPosicaoPoltrona.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPosicaoPoltrona()));

        colValorPago.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getValorPagoFormatado()));

        colHorario.setCellValueFactory(cellData -> {
            var sessao = cellData.getValue().getSessao();
            String horarioCompleto = sessao.getHorario().getHoraFim() + " - " + sessao.getHorario().getHoraFim();
            return new SimpleStringProperty(horarioCompleto);
        });
        ingressosObservable.setAll(ingressos);
        tabelaIngressos.setItems(ingressosObservable);
    }

    @FXML
    private void cancelarIngressoSelecionado() {
        Ingresso selecionado = tabelaIngressos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            showAlert(Alert.AlertType.WARNING, "Selecione um ingresso para cancelar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Cancelamento");
        confirm.setHeaderText(null);
        confirm.setContentText("Tem certeza que deseja cancelar o ingresso selecionado?");

        confirm.showAndWait().ifPresent(resp -> {
            if (resp == ButtonType.OK) {
                boolean sucesso;
                try {
                    ingressoService.cancelarIngresso(selecionado.getIdIngresso());
                    sucesso = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    sucesso = false;
                }

                if (sucesso) {
                    ingressosObservable.remove(selecionado);
                    showAlert(Alert.AlertType.INFORMATION, "Ingresso cancelado com sucesso.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro ao cancelar ingresso.");
                }
            }
        });
    }

    private void showAlert(Alert.AlertType tipo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void handleVoltar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicialView.fxml"));
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro ao voltar para a tela inicial.");
        }
    }
}
