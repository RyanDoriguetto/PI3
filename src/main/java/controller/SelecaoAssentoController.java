package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.Area;
import model.Sessao;
import service.*;
import view.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelecaoAssentoController {

    @FXML
    private Label labelInfo;
    @FXML
    private GridPane gridAssentos;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnVoltar;
    @FXML
    private BorderPane rootPane;

    private VBox rootPanePai;
    private int idHorario, idArea, idPeca;
    private String assentoSelecionado = null;

    private PecaService pecaService;
    private HorarioService horarioService;
    private AreaService areaService;
    private SessaoService sessaoService;
    private IngressoService ingressoService;
    private List<String> assentosReservados;

    public void setDados(int idHorario, int idArea, int idPeca, VBox rootPanePai,
                         PecaService pecaService, HorarioService horarioService,
                         AreaService areaService, SessaoService sessaoService,
                         IngressoService ingressoService) {
        this.idHorario = idHorario;
        this.idArea = idArea;
        this.idPeca = idPeca;
        this.rootPanePai = rootPanePai;
        this.pecaService = pecaService;
        this.horarioService = horarioService;
        this.areaService = areaService;
        this.sessaoService = sessaoService;
        this.ingressoService = ingressoService;

        Sessao sessao = sessaoService.buscarSessaoPorHorarioEPeca(idHorario, idPeca);
        assentosReservados = ingressoService.getPoltronasReservadas(sessao.getIdSessao(), idArea);
        if (assentosReservados == null) assentosReservados = new ArrayList<>();

        Area area = areaService.getAreaPorId(idArea);
        labelInfo.setText("Peça: " + pecaService.getPecaPorId(idPeca).getNome() +
                " | Área: " + area.getNome() +
                " | Horário: " + horarioService.getHorarioPorId(idHorario).getHorario());

        gridAssentos.getChildren().clear();
        switch (area.getNome()) {
            case "Plateia A" -> gerarGridAssentos(5, 5, "A");
            case "Plateia B" -> gerarGridAssentos(10, 10, "B");
            case "Frisa" -> gerarSelecaoSubarea("Frisa", 6);
            case "Camarote" -> gerarSelecaoSubarea("Camarote", 5);
            case "Balcao Nobre" -> gerarGridAssentos(10, 5, "BN");
        }
    }

    private void gerarGridAssentos(int colunas, int linhas, String prefixo) {
        gridAssentos.getChildren().clear();
        for (int row = 0; row < linhas; row++) {
            for (int col = 0; col < colunas; col++) {
                int numero = row * colunas + col + 1;
                Button btn = new Button(prefixo + numero);
                btn.setPrefSize(50, 40);
                if (assentosReservados.contains(btn.getText())) {
                    btn.setDisable(true);
                    btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                }
                btn.setOnAction(e -> selecionarAssento(btn));
                gridAssentos.add(btn, col, row);
            }
        }
    }

    private void gerarSelecaoSubarea(String tipo, int totalSubareas) {
        gridAssentos.getChildren().clear();
        for (int i = 1; i <= totalSubareas; i++) {
            int sub = i;
            Button btn = new Button(tipo + " " + sub);
            btn.setPrefWidth(100);
            btn.setOnAction(e -> {
                gridAssentos.getChildren().clear();
                gerarAssentosDeSubarea(tipo, sub);
            });
            gridAssentos.add(btn, (sub - 1) % 5, (sub - 1) / 5);
        }
    }

    private void gerarAssentosDeSubarea(String tipo, int subarea) {
        gridAssentos.getChildren().clear();
        int qtdAssentos = tipo.equals("Frisa") ? 5 : 10;
        String prefixo = tipo.equals("Frisa") ? "F" : "C";

        for (int i = 1; i <= qtdAssentos; i++) {
            Button btn = new Button(prefixo + subarea + "-" + i);
            btn.setPrefSize(60, 40);
            if (assentosReservados.contains(btn.getText())) {
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
            }
            btn.setOnAction(e -> selecionarAssento(btn));
            gridAssentos.add(btn, (i - 1) % 5, (i - 1) / 5);
        }

        Button voltar = new Button("← Escolher outra " + tipo.toLowerCase());
        voltar.setOnAction(e -> gerarSelecaoSubarea(tipo, tipo.equals("Frisa") ? 6 : 5));
        gridAssentos.add(voltar, 0, 3, 5, 1);
    }

    private void selecionarAssento(Button btn) {
        assentoSelecionado = btn.getText();
        gridAssentos.getChildren().forEach(node -> {
            if (node instanceof Button) node.setStyle("");
        });
        btn.setStyle("-fx-background-color: #00cc66; -fx-text-fill: white;");
    }

    @FXML
    private void confirmarSelecao() {
        if (assentoSelecionado == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um assento primeiro.").show();
            return;
        }

        try {
            int posicaoHorario = ((idHorario - 1) % 3) + 1;
            int idHorarioBanco = mapearHorarioParaBanco(idPeca, posicaoHorario);
            Sessao sessao = sessaoService.buscarSessaoPorHorarioEPeca(idHorarioBanco, idPeca);

            if (sessao == null) {
                new Alert(Alert.AlertType.ERROR, "Nenhuma sessão encontrada.").show();
                return;
            }

            ingressoService.criarIngresso(App.getUsuarioLogado(), sessao, idArea, assentoSelecionado);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ingresso Confirmado");
            alert.setHeaderText("Ingresso confirmado com sucesso!");
            alert.setContentText("Deseja comprar outro ingresso?");

            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");
            alert.getButtonTypes().setAll(btnSim, btnNao);

            alert.showAndWait().ifPresent(resposta -> {
                if (resposta == btnSim) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SelecaoCompraView.fxml"));
                        Pane telaCompra = loader.load();
                        rootPanePai.getChildren().clear();
                        rootPanePai.getChildren().add(telaCompra);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaInicialView.fxml"));
                        Pane telaInicial = loader.load();
                        rootPanePai.getChildren().clear();
                        rootPanePai.getChildren().add(telaInicial);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao confirmar ingresso: " + e.getMessage()).show();
        }
    }

    public int mapearHorarioParaBanco(int idPeca, int posicaoHorario) {
        if (idPeca == 1) {
            if (posicaoHorario == 1) return 1;
            else if (posicaoHorario == 2) return 4;
            else if (posicaoHorario == 3) return 7;
        } else if (idPeca == 2) {
            if (posicaoHorario == 1) return 2;
            else if (posicaoHorario == 2) return 5;
            else if (posicaoHorario == 3) return 8;
        } else if (idPeca == 3) {
            if (posicaoHorario == 1) return 3;
            else if (posicaoHorario == 2) return 6;
            else if (posicaoHorario == 3) return 9;
        }
        throw new IllegalArgumentException("idPeca ou posicaoHorario inválidos");
    }


    @FXML
    private void voltarTela() {
        rootPanePai.getChildren().clear();
        try {
            Pane telaAnterior = FXMLLoader.load(getClass().getResource("/view/SelecaoCompraView.fxml"));
            rootPanePai.getChildren().add(telaAnterior);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escolherAssento(int idHorario, int idArea, int idPeca, VBox rootPanePai,
                                       PecaService pecaService, HorarioService horarioService,
                                       AreaService areaService, SessaoService sessaoService,
                                       IngressoService ingressoService) {
        try {
            FXMLLoader loader = new FXMLLoader(SelecaoAssentoController.class.getResource("/view/SelecaoAssentoView.fxml"));
            Pane root = loader.load();
            SelecaoAssentoController controller = loader.getController();
            controller.setDados(idHorario, idArea, idPeca, rootPanePai, pecaService, horarioService, areaService, sessaoService, ingressoService);

            rootPanePai.getChildren().clear();
            rootPanePai.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
