package org.example.javafxteste;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private TextField txtIdade;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    private final ArrayList<Pessoa> pessoas = new ArrayList<>();

    @FXML
    void cadastrar() {
        if(txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtIdade.getText().isEmpty()){
            mostrarAlerta("ERROR", "Preencha todos os campos!");
            return;
        }
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        int idade;
        try{
            idade = Integer.parseInt(txtIdade.getText());
        }catch (NumberFormatException e){
            mostrarAlerta("ERROR", "Insira uma idade válida!");
            return;
        }

        Pessoa novaPessoa = new Pessoa(nome, email, idade);

        pessoas.add(novaPessoa);

        limparCampos();

        System.out.println("Pessoa cadastrada: "+novaPessoa.getNome());
        System.out.println("Total de pessoa cadastradas: "+ pessoas.size());

    }

    @FXML
    void gerarLista(){
        if(pessoas.isEmpty()){
            mostrarAlerta("ERROR", "A lista está vazia, não foi possível gerar o relatório.");
            return;
        }

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(gerarTextoRelatorio());

        Button btnSalvar = new Button("Salvar em Arquivo");
        btnSalvar.setOnAction(_ -> salvarRelatorioArquivo());

        VBox vbox = new VBox(10, textArea, btnSalvar);
        vbox.setStyle("-fx-padding: 10;");

        Scene scene = new Scene(vbox, 400, 300);
        Stage stage = new Stage();
        stage.setTitle("Relatório de Pessoas Cadastradas");
        stage.setScene(scene);
        stage.show();
    }

    private String gerarTextoRelatorio(){
        StringBuilder sb = new StringBuilder();
        sb.append("=== Relatório de Cadastros ===\n\n");

        for(Pessoa pessoa : pessoas){
            sb.append(String.format(
                    "Nome: %s\nEmail: %s\nIdade: %d\n--------------------\n",
                    pessoa.getNome(), pessoa.getEmail(), pessoa.getIdade()));
        }

        sb.append("\nTotal de pessoas: ").append(pessoas.size());
        return sb.toString();
    }

    private void salvarRelatorioArquivo(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos de Texto",  "*.txt"));
        fileChooser.setInitialFileName("relatorio_pessoas.txt");

        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
                writer.write(gerarTextoRelatorio());
                mostrarAlerta("SUCESSO","Relatório salvo em:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                mostrarAlerta("ERROR","Não foi possível salvar o arquivo.");
            }
        }
    }

    private void limparCampos(){
        txtNome.clear();
        txtEmail.clear();
        txtIdade.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
