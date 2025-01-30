package br.ufrn.tads;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        scene = new Scene(loadFXML("telaPrincipal"), 1000, 1500); // telaMedico - telaPaciente
        scene.getStylesheets().add("fxmlcss.css");
        stage.setHeight(540);
        stage.setWidth(955);
        stage.setTitle("clínicaSystem");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml, String css) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getStylesheets().add(loadCSS(css));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    private static String loadCSS (String css) {
    	return (css + ".css");
    }

    public static void main(String[] args) {
        launch();
    }

}