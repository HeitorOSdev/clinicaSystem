package br.ufrn.tads.controller;

import java.io.IOException;
//import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.ufrn.tads.model.Medico;
import br.ufrn.tads.service.MedicoService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;

public class MedicoController {
    private MedicoService medicoService;
    
    @FXML
    private Label lblDateTime;
	
	@FXML
    private TextField tfId;
	
	@FXML
    private TextField tfNome;
	
	@FXML
    private TextField tfCrm;
	
	@FXML
    private TextField tfEspecialidade;
	
	@FXML
    private TextField tfTelefone;
	
	@FXML
    private TextField tfEmail;

    @FXML
    private TableView<Medico> tbvMedicos;
    
    @FXML
    private TableColumn<Medico, Long> colId;
    
    @FXML
    private TableColumn<Medico, String> colNome;
    
    @FXML
    private TableColumn<Medico, String> colCrm;
    
    @FXML
    private TableColumn<Medico, String> colEspecialidade;
    
    @FXML
    private TableColumn<Medico, String> colEmail;
    
    @FXML
    private TableColumn<Medico, String> colTelefone;
    
    @FXML
    private Button busButton;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button ediButton;
    
    @FXML
    private Button excButton;
    
//    Connection conn = null;
//    ResultSet rs = null;
//    
    
    public MedicoController() {
        // EXECUTADO PRIMEIRO
        medicoService = new MedicoService();
    }
    
    
    
    @FXML
    public void initialize() {
    	
        
//        Image img = new Image("listicon.png");
//        ImageView imgView = new ImageView(img);
//        listButton.setGraphic(imgView);
//
//        img = new Image("nometads.png");
//        imgvLogo.setImage(img);
        
        animateTimeLabel();
    	
    	colId.setCellValueFactory(new PropertyValueFactory<Medico, Long>("id"));
    	colNome.setCellValueFactory(new PropertyValueFactory<Medico, String>("nome"));
    	colCrm.setCellValueFactory(new PropertyValueFactory<Medico, String>("crm"));
    	colEspecialidade.setCellValueFactory(new PropertyValueFactory<Medico, String>("especialidade"));
    	colEmail.setCellValueFactory(new PropertyValueFactory<Medico, String>("email"));
    	colTelefone.setCellValueFactory(new PropertyValueFactory<Medico, String>("telefone"));
    	
//    	tbvMedicos.getColumns().addAll(medicoService.getMedicos());
    	
    	busButton.setVisible(true);
        addButton.setVisible(true);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        
        
              
    }
    
    //método para obter itens da linha selecionada da tabela e copiar para o form com os text fields
    @FXML
    void getItem(MouseEvent event) {
        Integer idx = tbvMedicos.getSelectionModel().getSelectedIndex();
        if (idx <= -1) return;
        tfId.setText(String.valueOf(colId.getCellData(idx)));
        tfNome.setText(colNome.getCellData(idx));
        tfCrm.setText(colCrm.getCellData(idx));
        tfEspecialidade.setText(colEspecialidade.getCellData(idx));
        tfEmail.setText(colEmail.getCellData(idx));
        tfTelefone.setText(colTelefone.getCellData(idx));
        
        busButton.setDisable(true);
        addButton.setDisable(true);
        ediButton.setDisable(false);
        excButton.setDisable(false);
    }
    
    @FXML
    private void listMedicos(ActionEvent event) throws IOException {
                
        ObservableList<Medico> list = FXCollections.observableArrayList(medicoService.getMedicos());
        tbvMedicos.setItems(list);     
        
    }
    
    @FXML
    private void addMedico(ActionEvent event) throws IOException {
//    	medicoService.createTable();
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
            String nome = tfNome.getText();
            String crm = tfCrm.getText();
            String especialidade = tfEspecialidade.getText();
            String email = tfEmail.getText();
            String telefone = tfTelefone.getText();
            
            Medico medico = new Medico(nome, crm, especialidade, telefone, email);

            if (medicoService.save(medico)) {
                tbvMedicos.getItems().add(medico); //inclui na tableview, mas sem novo ID, pois primeiro precisa persistir no BD
            }
        }
    }

    @FXML
    void excMedico(ActionEvent event) {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
        	if (medicoService.delete(Long.parseLong(tfId.getText()))) {
                int idx = tbvMedicos.getSelectionModel().getSelectedIndex();
                tbvMedicos.getItems().remove(idx); //inclui na tableview, mas sem novo ID, pois primeiro precisa persistir no BD
                tfId.clear();
                tfNome.clear();
                tfCrm.clear();
                tfEspecialidade.clear();
                tfEmail.clear();
                tfTelefone.clear();
            }
        }
    }
    
    @FXML
    void ediMedico(ActionEvent event) {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
        	String nome = tfNome.getText();
            String crm = tfCrm.getText();
            String especialidade = tfEspecialidade.getText();
            String email = tfEmail.getText();
            String telefone = tfTelefone.getText();
            
            Medico medico = new Medico(nome, crm, especialidade, telefone, email);
            
            medicoService.update(medico, null);
        }
    }
    
    void animateTimeLabel() {
        DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            lblDateTime.setText(dtFmt.format(LocalDateTime.now()));
        }));
        timeline.setCycleCount(-1); //Animation.INDEFINITE (-1)
        timeline.play();
    }

// Declaracao completa de uma funcao em com JavaFX
//    @FXML
//    void nomeFuncao (ActionEvent event) throws IOException {
//
//    }
    	
    
    
    
    
    
}