package org.example.javafxteste;
//author Matheus Leite

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VerTelaCadastro extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        HelloController controller = new HelloController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene tela = new Scene(root);
        stage.setTitle("Cadastro de Pessoas");
        stage.setScene(tela);
        stage.show();
    }
}
