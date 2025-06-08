package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class TelaInicial extends Application {

    private String cpf;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Menu Principal");

        Label textoBoasVindas = new Label("Bem-vindo ao Teatro ABC!");

        ImageView imagemTeatro;
        try {
            Image imagem = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/layoutTeatro.jpg")));
            imagemTeatro = new ImageView(imagem);
            imagemTeatro.setFitWidth(400);
            imagemTeatro.setFitHeight(200);
            imagemTeatro.setPreserveRatio(true);
        } catch (Exception e) {
            imagemTeatro = null;
        }

        Button btnCadastro = new Button("Cadastrar usuário");
        Button btnComprar = new Button("Comprar ingresso");
        Button btnImprimir = new Button("Imprimir ingresso");
        Button btnEstatisticas = new Button("Estatística de Vendas");

        btnCadastro.setOnAction(e -> CadastroUsuarioView.exibir());
        btnComprar.setOnAction(e -> System.out.println("Comprar ingresso clicado"));
        btnImprimir.setOnAction(e -> System.out.println("Imprimir ingresso clicado"));
        btnEstatisticas.setOnAction(e -> System.out.println("Estatística de Vendas clicado"));

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(textoBoasVindas);
        if (imagemTeatro != null) {
            root.getChildren().add(imagemTeatro);
        } else {
            root.getChildren().add(new Label("Imagem não encontrada"));
        }
        root.getChildren().addAll(btnCadastro, btnComprar, btnImprimir, btnEstatisticas);

        Scene cena = new Scene(root, 600, 500);
        stage.setScene(cena);

        try {
            Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/iconeTeatro.png")));
            stage.getIcons().add(icone);
        } catch (Exception e) {
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
