package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.Horario;
import repository.Conexao;
import service.*;
import view.App;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SelecaoCompraController implements Initializable {

    @FXML private ToggleButton btnRomeuJulieta, btnAutoCompadecida, btnChapeuzinho;
    @FXML private ToggleButton btnHorario1, btnHorario2, btnHorario3;
    @FXML private VBox boxAreas;
    @FXML private ToggleButton btnPlateiaA, btnPlateiaB, btnFrisa, btnCamarote, btnBalcaoNobre;

    private PecaService pecaService;
    private HorarioService horarioService;
    private SessaoService sessaoService;
    private AreaService areaService;
    private UsuarioService usuarioService;
    private IngressoService ingressoService;

    private ToggleGroup grupoPecas = new ToggleGroup();
    private ToggleGroup grupoHorarios = new ToggleGroup();
    private ToggleGroup grupoAreas = new ToggleGroup();

    private int idPecaSelecionada = -1;
    private int idHorarioSelecionado = -1;
    private int idAreaSelecionada = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            var conexao = Conexao.getConexao();
            pecaService = new PecaService(conexao);
            horarioService = new HorarioService(conexao);
            sessaoService = new SessaoService(conexao, pecaService, horarioService);
            areaService = new AreaService(conexao);
            usuarioService = new UsuarioService(conexao, new EnderecoService(conexao));
            ingressoService = new IngressoService(new repository.IngressoRepository(conexao, usuarioService, sessaoService, areaService), areaService.getTodasAreas());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnRomeuJulieta.setToggleGroup(grupoPecas);
        btnAutoCompadecida.setToggleGroup(grupoPecas);
        btnChapeuzinho.setToggleGroup(grupoPecas);

        btnHorario1.setToggleGroup(grupoHorarios);
        btnHorario2.setToggleGroup(grupoHorarios);
        btnHorario3.setToggleGroup(grupoHorarios);

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
        List<Integer> horariosIds = sessaoService.getHorariosPorPeca(idPeca);
        esconderTodosHorarios();

        for (int id : horariosIds) {
            Horario h = horarioService.getHorarioPorId(id);
            if (h == null) continue;

            String turno = h.getTurno();
            String texto = turno + " " + formatarHorario(h);

            switch (turno.toLowerCase()) {
                case "manhã" -> {
                    btnHorario1.setText(texto);
                    btnHorario1.setVisible(true);
                    btnHorario1.setDisable(false);
                }
                case "tarde" -> {
                    btnHorario2.setText(texto);
                    btnHorario2.setVisible(true);
                    btnHorario2.setDisable(false);
                }
                case "noite" -> {
                    btnHorario3.setText(texto);
                    btnHorario3.setVisible(true);
                    btnHorario3.setDisable(false);
                }
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

        int horarioCorreto = (idHorarioSelecionado - 1) * 3 + idPecaSelecionada;

        System.out.printf("Compra: Peça %d, Horário %d, Área %d\n", idPecaSelecionada, horarioCorreto, idAreaSelecionada);

        SelecaoAssentoController.escolherAssento(horarioCorreto, idAreaSelecionada, idPecaSelecionada, App.rootPane,
                pecaService, horarioService, areaService, sessaoService, ingressoService);
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaInicialView.fxml"));
            javafx.scene.Parent root = loader.load();
            App.rootPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela inicial.", ButtonType.OK).showAndWait();
        }
    }
}
