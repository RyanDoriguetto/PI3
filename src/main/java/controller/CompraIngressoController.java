package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.Horario;
import repository.Conexao;
import service.AreaService;
import service.HorarioService;
import service.PecaService;
import service.SessaoService;
import view.App;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompraIngressoController implements Initializable {

    @FXML private ToggleButton btnRomeuJulieta;
    @FXML private ToggleButton btnAutoCompadecida;
    @FXML private ToggleButton btnChapeuzinho;

    @FXML private ToggleButton btnHorario1; // Manhã
    @FXML private ToggleButton btnHorario2; // Tarde
    @FXML private ToggleButton btnHorario3; // Noite

    @FXML private VBox boxAreas;

    @FXML private ToggleButton btnPlateiaA;
    @FXML private ToggleButton btnPlateiaB;
    @FXML private ToggleButton btnFrisa;
    @FXML private ToggleButton btnCamarote;
    @FXML private ToggleButton btnBalcaoNobre;

    private PecaService pecaService;
    private HorarioService horarioService;
    private SessaoService sessaoService;
    private AreaService areaService;

    private ToggleGroup grupoPecas = new ToggleGroup();
    private ToggleGroup grupoHorarios = new ToggleGroup();
    private ToggleGroup grupoAreas = new ToggleGroup();

    private int idPecaSelecionada = -1;
    private int idHorarioSelecionado = -1;
    private int idAreaSelecionada = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pecaService = new PecaService(Conexao.getConexao());
        horarioService = new HorarioService(Conexao.getConexao());
        sessaoService = new SessaoService(Conexao.getConexao(), pecaService, horarioService);
        areaService = new AreaService(Conexao.getConexao());

        // Grupo peças
        btnRomeuJulieta.setToggleGroup(grupoPecas);
        btnAutoCompadecida.setToggleGroup(grupoPecas);
        btnChapeuzinho.setToggleGroup(grupoPecas);

        // Grupo horários
        btnHorario1.setToggleGroup(grupoHorarios);
        btnHorario2.setToggleGroup(grupoHorarios);
        btnHorario3.setToggleGroup(grupoHorarios);

        // Grupo áreas
        btnPlateiaA.setToggleGroup(grupoAreas);
        btnPlateiaB.setToggleGroup(grupoAreas);
        btnFrisa.setToggleGroup(grupoAreas);
        btnCamarote.setToggleGroup(grupoAreas);
        btnBalcaoNobre.setToggleGroup(grupoAreas);

        esconderTodosHorarios();
        esconderTodasAreas();

        grupoPecas.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                ToggleButton sel = (ToggleButton) newToggle;
                if (sel == btnRomeuJulieta) idPecaSelecionada = 1;
                else if (sel == btnAutoCompadecida) idPecaSelecionada = 2;
                else if (sel == btnChapeuzinho) idPecaSelecionada = 3;

                mostrarHorariosPorPeca(idPecaSelecionada);

                grupoHorarios.selectToggle(null);
                idHorarioSelecionado = -1;

                esconderTodasAreas();
                grupoAreas.selectToggle(null);
                idAreaSelecionada = -1;
            } else {
                esconderTodosHorarios();
                idPecaSelecionada = -1;
            }
        });

        grupoHorarios.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                ToggleButton sel = (ToggleButton) newToggle;
                if (sel == btnHorario1) idHorarioSelecionado = 1;
                else if (sel == btnHorario2) idHorarioSelecionado = 2;
                else if (sel == btnHorario3) idHorarioSelecionado = 3;

                mostrarTodasAreas();

                grupoAreas.selectToggle(null);
                idAreaSelecionada = -1;
            } else {
                idHorarioSelecionado = -1;

                esconderTodasAreas();
                grupoAreas.selectToggle(null);
                idAreaSelecionada = -1;
            }
        });

        grupoAreas.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                ToggleButton sel = (ToggleButton) newToggle;
                if (sel == btnPlateiaA) idAreaSelecionada = 1;
                else if (sel == btnPlateiaB) idAreaSelecionada = 2;
                else if (sel == btnFrisa) idAreaSelecionada = 3;
                else if (sel == btnCamarote) idAreaSelecionada = 4;
                else if (sel == btnBalcaoNobre) idAreaSelecionada = 5;
            } else {
                idAreaSelecionada = -1;
            }
        });
    }

    private void mostrarHorariosPorPeca(int idPeca) {
        // Pega ids dos horários da peça
        List<Integer> horariosIds = sessaoService.getHorariosPorPeca(idPeca);

        // Deixa os 3 botões horários invisíveis inicialmente
        esconderTodosHorarios();

        for (int id : horariosIds) {
            Horario h = horarioService.getHorarioPorId(id);
            if (h == null) continue;

            String turno = h.getTurno();
            String texto = turno + " " + formatarHorario(h);

            if (turno.equalsIgnoreCase("Manhã")) {
                btnHorario1.setText(texto);
                btnHorario1.setVisible(true);
                btnHorario1.setDisable(false);
            } else if (turno.equalsIgnoreCase("Tarde")) {
                btnHorario2.setText(texto);
                btnHorario2.setVisible(true);
                btnHorario2.setDisable(false);
            } else if (turno.equalsIgnoreCase("Noite")) {
                btnHorario3.setText(texto);
                btnHorario3.setVisible(true);
                btnHorario3.setDisable(false);
            }
        }
    }

    private void esconderTodosHorarios() {
        btnHorario1.setVisible(false);
        btnHorario1.setDisable(true);
        btnHorario1.setSelected(false);
        btnHorario1.setText("");

        btnHorario2.setVisible(false);
        btnHorario2.setDisable(true);
        btnHorario2.setSelected(false);
        btnHorario2.setText("");

        btnHorario3.setVisible(false);
        btnHorario3.setDisable(true);
        btnHorario3.setSelected(false);
        btnHorario3.setText("");
    }

    private void mostrarTodasAreas() {
        btnPlateiaA.setVisible(true);
        btnPlateiaA.setDisable(false);

        btnPlateiaB.setVisible(true);
        btnPlateiaB.setDisable(false);

        btnFrisa.setVisible(true);
        btnFrisa.setDisable(false);

        btnCamarote.setVisible(true);
        btnCamarote.setDisable(false);

        btnBalcaoNobre.setVisible(true);
        btnBalcaoNobre.setDisable(false);
    }

    private void esconderTodasAreas() {
        btnPlateiaA.setVisible(false);
        btnPlateiaA.setDisable(true);

        btnPlateiaB.setVisible(false);
        btnPlateiaB.setDisable(true);

        btnFrisa.setVisible(false);
        btnFrisa.setDisable(true);

        btnCamarote.setVisible(false);
        btnCamarote.setDisable(true);

        btnBalcaoNobre.setVisible(false);
        btnBalcaoNobre.setDisable(true);
    }

    private String formatarHorario(Horario h) {
        return h.getHoraInicio().toString().substring(0, 5) + " - " + h.getHoraFim().toString().substring(0, 5);
    }

    @FXML
    private void escolherAssento(ActionEvent event) {
        if (idPecaSelecionada == -1 || idHorarioSelecionado == -1 || idAreaSelecionada == -1) {
            new Alert(Alert.AlertType.WARNING, "Escolha peça, horário e área antes de continuar.", ButtonType.OK).showAndWait();
            return;
        }

        int horarioCorreto = idHorarioSelecionado;
        if (idPecaSelecionada == 2) horarioCorreto += 3;
        else if (idPecaSelecionada == 3) horarioCorreto += 6;

        System.out.printf("Compra: Peça %d, Horário %d, Área %d\n", idPecaSelecionada, horarioCorreto, idAreaSelecionada);

        // Aqui continuaria o resto da lógica para escolher assento, abrir próxima tela, etc
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/TelaInicialView.fxml"));
            javafx.scene.Parent root = loader.load();
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela inicial.", ButtonType.OK).showAndWait();
        }
    }
}
