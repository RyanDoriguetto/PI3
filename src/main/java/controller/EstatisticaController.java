package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.EstatisticaService;
import repository.EstatisticaRepository.*;
import view.App;

import java.sql.SQLException;
import java.util.List;

public class EstatisticaController {

    @FXML
    private Label lblPecaMaisVendida, lblPecaMenosVendida;
    @FXML
    private Label lblSessaoMaisOcupada, lblSessaoMenosOcupada;
    @FXML
    private Label lblSessaoMaisLucrativa, lblSessaoMenosLucrativa;
    @FXML
    private VBox vboxFaturamento;

    private EstatisticaService service = new EstatisticaService();

    @FXML
    public void initialize() {
        carregarEstatisticas();
    }

    private void carregarEstatisticas() {
        try {
            PecaVenda pecaMais = service.getPecaMaisVendida();
            PecaVenda pecaMenos = service.getPecaMenosVendida();
            SessaoOcupacao sessaoMais = service.getSessaoMaisOcupada();
            SessaoOcupacao sessaoMenos = service.getSessaoMenosOcupada();
            LucroSessao lucroMais = service.getSessaoMaisLucrativa();
            LucroSessao lucroMenos = service.getSessaoMenosLucrativa();
            List<FaturamentoMedio> faturamento = service.getFaturamentoMedioPorPeca();

            lblPecaMaisVendida.setText(formatarPecaVenda(pecaMais));
            lblPecaMenosVendida.setText(formatarPecaVenda(pecaMenos));
            lblSessaoMaisOcupada.setText(formatarSessaoOcupacao(sessaoMais));
            lblSessaoMenosOcupada.setText(formatarSessaoOcupacao(sessaoMenos));
            lblSessaoMaisLucrativa.setText(formatarLucroSessao(lucroMais));
            lblSessaoMenosLucrativa.setText(formatarLucroSessao(lucroMenos));

            vboxFaturamento.getChildren().clear();
            vboxFaturamento.setStyle("-fx-alignment: center;");

            if (faturamento == null || faturamento.isEmpty()) {
                Label lbl = new Label("Nenhum dado disponível.");
                lbl.setStyle("-fx-font-weight: normal;");
                vboxFaturamento.getChildren().add(lbl);
            } else {
                for (FaturamentoMedio f : faturamento) {
                    Label lblNome = new Label(f.nomePeca + ": ");
                    lblNome.setStyle("-fx-font-weight: bold;");
                    Label lblValor = new Label("R$ " + String.format("%.2f", f.faturamentoMedio));
                    lblValor.setStyle("-fx-font-weight: normal;");

                    HBox linha = new HBox(5, lblNome, lblValor);
                    linha.setAlignment(Pos.CENTER);
                    vboxFaturamento.getChildren().add(linha);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblPecaMaisVendida.setText("Erro ao carregar dados");
        }
    }

    private String formatarPecaVenda(PecaVenda p) {
        if (p == null) return "Nenhuma";
        return p.nomePeca + " (" + p.totalIngressos + " ingressos)";
    }

    private String formatarSessaoOcupacao(SessaoOcupacao s) {
        if (s == null) return "Nenhuma";
        return s.nomePeca + " (Sessão " + s.idSessao + ") - " + String.format("%.2f%%", s.ocupacaoPercentual);
    }

    private String formatarLucroSessao(LucroSessao l) {
        if (l == null) return "Nenhuma";
        return l.nomePeca + " (Sessão " + l.idSessao + ") - R$ " + String.format("%.2f", l.totalLucro);
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

    private void showAlert(Alert.AlertType tipo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
