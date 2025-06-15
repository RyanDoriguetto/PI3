package controller;

import factory.IngressoFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.Area;
import model.Sessao;
import model.Usuario;
import model.ingresso.Ingresso;
import service.*;
import view.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

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
    private int idHorario;
    private int idArea;
    private int idPeca;
    private String assentoSelecionado = null;

    private PecaService pecaService;
    private HorarioService horarioService;
    private AreaService areaService;
    private SessaoService sessaoService;
    private IngressoService ingressoService;
    private List<String> assentosReservados;

    // Recebe ingressoService no setDados pra evitar instanciar aqui dentro
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
        if (assentosReservados == null) {
            assentosReservados = new ArrayList<>();
        }

        Area area = areaService.getAreaPorId(idArea);

        labelInfo.setText("Peça: " + pecaService.getPecaPorId(idPeca).getNome() +
                " | Área: " + area.getNome() +
                " | Horário: " + horarioService.getHorarioPorId(idHorario).getHorario());

        gridAssentos.getChildren().clear();

        switch (area.getNome()) {
            case "Plateia A":
                gerarGridAssentos(5, 5, "A");
                break;
            case "Plateia B":
                gerarGridAssentos(10, 10, "B");
                break;
            case "Frisa":
                gerarSelecaoSubarea("Frisa", 6);
                break;
            case "Camarote":
                gerarSelecaoSubarea("Camarote", 5);
                break;
            case "Balcao Nobre":
                gerarGridAssentos(10, 5, "BN");
                break;
        }
    }

    private void gerarGridAssentos(int colunas, int linhas, String prefixo) {
        gridAssentos.getChildren().clear();
        for (int row = 0; row < linhas; row++) {
            for (int col = 0; col < colunas; col++) {
                int numero = row * colunas + col + 1;
                Button btn = new Button(prefixo + numero);
                btn.setPrefWidth(50);
                btn.setPrefHeight(40);
                if (assentosReservados.contains(btn.getText())) {
                    btn.setDisable(true);
                    btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                }


                btn.setOnAction(e -> {
                    assentoSelecionado = btn.getText();

                    // Limpa a seleção visual
                    gridAssentos.getChildren().forEach(node -> {
                        if (node instanceof Button) node.setStyle("");
                    });

                    // Destaca o botão selecionado
                    btn.setStyle("-fx-background-color: #00cc66; -fx-text-fill: white;");
                });

                gridAssentos.add(btn, col, row);
            }
        }
    }

    private void gerarSelecaoSubarea(String tipo, int totalSubareas) {
        gridAssentos.getChildren().clear();

        for (int i = 1; i <= totalSubareas; i++) {
            final int subareaSelecionada = i;

            Button btn = new Button(tipo + " " + subareaSelecionada);
            btn.setPrefWidth(100);

            btn.setOnAction(e -> {
                gridAssentos.getChildren().clear();
                gerarAssentosDeSubarea(tipo, subareaSelecionada);
            });

            gridAssentos.add(btn, (subareaSelecionada - 1) % 5, (subareaSelecionada - 1) / 5);
        }
    }

    private void gerarAssentosDeSubarea(String tipo, int subarea) {
        int assentos = tipo.equals("Frisa") ? 5 : 10;
        String prefixo = tipo.equals("Frisa") ? "F" : "C";

        for (int i = 1; i <= assentos; i++) {
            Button btn = new Button(prefixo + subarea + "-" + i);
            btn.setPrefWidth(60);
            btn.setPrefHeight(40);
            if (assentosReservados.contains(btn.getText())) {
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
            }


            btn.setOnAction(e -> {
                assentoSelecionado = btn.getText();

                // Limpa seleção visual
                gridAssentos.getChildren().forEach(node -> {
                    if (node instanceof Button) node.setStyle("");
                });

                // Destaca o selecionado
                btn.setStyle("-fx-background-color: #00cc66; -fx-text-fill: white;");
            });

            gridAssentos.add(btn, (i - 1) % 5, (i - 1) / 5);
        }

        // Botão para voltar e escolher outra subárea
        Button voltar = new Button("← Escolher outra " + tipo.toLowerCase());
        voltar.setOnAction(e -> gerarSelecaoSubarea(tipo, tipo.equals("Frisa") ? 6 : 5));
        gridAssentos.add(voltar, 0, 3, 5, 1);
    }

    @FXML
    private void confirmarSelecao() {
        if (assentoSelecionado == null || assentoSelecionado.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um assento primeiro.");
            alert.show();
            return;
        }

        try {
            int posicaoHorario = ((idHorario - 1) % 3) + 1;

            int idHorarioBanco = mapearHorarioParaBanco(idPeca, posicaoHorario);
            Sessao sessao = sessaoService.buscarSessaoPorHorarioEPeca(idHorarioBanco, idPeca);

            System.out.println("Sessao retornada: " + sessao);

            if (sessao == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhuma sessão encontrada para o horário e peça selecionados.");
                alert.show();
                return;
            }

            Ingresso ingresso = ingressoService.criarIngresso(App.getUsuarioLogado(), sessao, idArea, assentoSelecionado);

            System.out.println("Ingresso gerado: " + ingresso);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ingresso confirmado com sucesso!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao confirmar ingresso: " + e.getMessage());
            alert.show();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SelecaoCompraView.fxml"));
            Pane telaAnterior = loader.load();
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
