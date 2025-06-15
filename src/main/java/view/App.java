package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Usuario;

public class App extends Application {

    public static VBox rootPane;
    private static Usuario usuarioLogado;

    @Override
    public void start(Stage primaryStage) throws Exception {
        rootPane = new VBox();
        rootPane.setSpacing(10);
        rootPane.setPadding(new Insets(10));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaInicialView.fxml"));
        Parent telaInicial = loader.load();

        rootPane.getChildren().add(telaInicial);

        Scene scene = new Scene(rootPane, 600, 700);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/iconeTeatro.png")));
        primaryStage.setTitle("Sistema de Teatro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
