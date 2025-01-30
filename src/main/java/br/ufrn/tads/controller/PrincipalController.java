package br.ufrn.tads.controller;

import java.io.IOException;
import br.ufrn.tads.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrincipalController {
    
    @FXML
    private Button medicoButton;   
    
    @FXML
    private Button pacienteButton; 
    
    @FXML
    private Button consultaButton; 
    
    @FXML
    private void switchToMedico() throws IOException {
        App.setRoot("telaMedico", null);
    }
    
    @FXML
    private void switchToPaciente() throws IOException {
        App.setRoot("telaPaciente", null);
    }
    
    @FXML
    private void switchToConsulta() throws IOException {
        App.setRoot("telaConsulta", null);
    }
    
    public PrincipalController() {
    	
    }
   
    
    public void initialize() throws IOException {        
              
    }   
    
}