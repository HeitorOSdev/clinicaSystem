package br.ufrn.tads.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufrn.tads.App;
import br.ufrn.tads.model.Paciente;
import br.ufrn.tads.service.PacienteService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PacienteController {
	
    @FXML
    private Button voltarButton;
	
	boolean modoBuscaAtivado = false;
	
    private PacienteService pacienteService;
	
	@FXML
    private TextField tfId;
	
	@FXML
    private TextField tfCpf;
	
	@FXML
    private TextField tfNome;
	
	@FXML
    private TextField tfDataNascimento;
	
	@FXML
    private TextField tfTelefone;
	
	@FXML
    private TextField tfEmail;
	
	@FXML
    private TextField tfGenero;

    @FXML
    private TableView<Paciente> tbvPacientes;
    
    @FXML
    private TableColumn<Paciente, Long> colId;
    
    @FXML
    private TableColumn<Paciente, String> colCpf;
    
    @FXML
    private TableColumn<Paciente, String> colNome;
    
    @FXML
    private TableColumn<Paciente, Date> colDataNascimento;
    
    @FXML
    private TableColumn<Paciente, String> colTelefone;
    
    @FXML
    private TableColumn<Paciente, String> colEmail;
    
    @FXML
    private TableColumn<Paciente, String> colGenero;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button ediButton;
    
    @FXML
    private Button excButton;   
    
    @FXML
    private Button modoBusButton; 
    
    @FXML
    private Button limparFormButton; 
    
    @FXML
    private void switchToPrincipal() throws IOException {
        App.setRoot("telaPrincipal", null);
    }
    
    public PacienteController() {
        pacienteService = new PacienteService();
    }
   
    
    public void initialize() throws IOException {
    	
    	//(cpf, nome, dataNascimento, telefone, email, genero)
    	
    	colId.setCellValueFactory(new PropertyValueFactory<Paciente, Long>("id"));
    	colNome.setCellValueFactory(new PropertyValueFactory<Paciente, String>("nome"));
    	colCpf.setCellValueFactory(new PropertyValueFactory<Paciente, String>("cpf"));
    	colDataNascimento.setCellValueFactory(new PropertyValueFactory<Paciente, Date>("dataNascimento"));
    	colTelefone.setCellValueFactory(new PropertyValueFactory<Paciente, String>("telefone"));
    	colGenero.setCellValueFactory(new PropertyValueFactory<Paciente, String>("genero"));
    	colEmail.setCellValueFactory(new PropertyValueFactory<Paciente, String>("email"));

    	listPacientes();
    	
    	modoBusButton.setDisable(false);
    	modoBusButton.setVisible(true);
    	
    	
        addButton.setVisible(true);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        
        
        
              
    }
    
    @FXML
    void getItem(MouseEvent event) {
        Integer idx = tbvPacientes.getSelectionModel().getSelectedIndex();
        if (idx <= -1) return;
        
        if (modoBuscaAtivado) desativarBusca();
        
        tfId.setText(String.valueOf(colId.getCellData(idx)));
        tfNome.setText(colNome.getCellData(idx));
        tfCpf.setText(colCpf.getCellData(idx));
        tfDataNascimento.setText(colDataNascimento.getCellData(idx).toString());
        tfTelefone.setText(colTelefone.getCellData(idx));
        tfGenero.setText(colGenero.getCellData(idx));     
        tfEmail.setText(colEmail.getCellData(idx));
        
        modoBusButton.setDisable(true);
        addButton.setDisable(true);
        ediButton.setDisable(false);
        excButton.setDisable(false);
   
    }
    
    @FXML
    private void listPacientes(ActionEvent event) throws IOException {
                
        ObservableList<Paciente> list = FXCollections.observableArrayList(pacienteService.getPacientes());
        tbvPacientes.setItems(list);
        
    }
    
    private void listPacientes() {
                
        ObservableList<Paciente> list = FXCollections.observableArrayList(pacienteService.getPacientes());
        tbvPacientes.setItems(list);  
        
    }
    
    @FXML
    private void addPaciente(ActionEvent event) throws IOException, ParseException {
        if (!tfNome.getText().isEmpty() && !tfCpf.getText().isEmpty() && !tfDataNascimento.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty() && !tfGenero.getText().isEmpty()) {     		
	            
        	SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        	
    		String cpf = tfCpf.getText();
            String nome = tfNome.getText();
            Date dataNascimento = formatador.parse(tfDataNascimento.getText());
            String telefone = tfTelefone.getText();
            String email = tfEmail.getText();
            String genero = tfGenero.getText();    
            
            Paciente paciente = new Paciente(cpf, nome, dataNascimento, telefone, email, genero);

            if (pacienteService.save(paciente)) {
            	listPacientes();
            }
        }
    }

    @FXML
    void excPaciente(ActionEvent event) {
        if (!tfNome.getText().isEmpty() && !tfCpf.getText().isEmpty() && !tfDataNascimento.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty() && !tfGenero.getText().isEmpty()) {
        	if (pacienteService.delete(Long.parseLong(tfId.getText()))) {
                int idx = tbvPacientes.getSelectionModel().getSelectedIndex();
                tbvPacientes.getItems().remove(idx);
                tfId.clear();
                tfNome.clear();
                tfCpf.clear();
                tfDataNascimento.clear();
                tfEmail.clear();
                tfTelefone.clear();
                tfGenero.clear();
                
                addButton.setDisable(false);
                excButton.setDisable(true);
                ediButton.setDisable(true);
            }
        }
    }
    
    @FXML
    void ediPaciente(ActionEvent event) throws ParseException {
        if (!tfNome.getText().isEmpty() && !tfCpf.getText().isEmpty() && !tfDataNascimento.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty() && !tfGenero.getText().isEmpty()) {
        	
        	excButton.setDisable(true);
        	
        	SimpleDateFormat formatador = new SimpleDateFormat("yyyy-[m]m-[d]d");

        	
        	Long id = Long.parseLong(tfId.getText());
        	String nome = tfNome.getText();
            String cpf = tfCpf.getText();
        	Date dataNascimento = formatador.parse(tfDataNascimento.getText());
            String email = tfEmail.getText();
            String telefone = tfTelefone.getText();
            String genero = tfGenero.getText();
            
            Paciente paciente = new Paciente(id, cpf, nome, dataNascimento, telefone, email, genero);
            
            pacienteService.update(paciente, null);
            
            listPacientes();
        }
    }
    
    @FXML
    void limparForm(ActionEvent event) {
    	
        desativarBusca();
    	tfId.clear();
        tfNome.clear();
        tfCpf.clear();
        tfDataNascimento.clear();
        tfEmail.clear();
        tfTelefone.clear();
        tfGenero.clear();
        
        modoBusButton.setDisable(false);
        addButton.setDisable(false);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        listPacientes();
        
    }
    
    @FXML
    void desativarBusca() {
    	
    	modoBuscaAtivado = false;
    	modoBusButton.setText("Modo busca");
    	limparFormButton.setText("Limpar campos de pesquisa");
    	
    	tfNome.setDisable(false);
        tfCpf.setDisable(false);
        tfDataNascimento.setDisable(false);
        tfEmail.setDisable(false);
        tfTelefone.setDisable(false);
        tfGenero.setDisable(false);

    }
    
    @FXML
    void limparFormParaBusca() {
    	tfId.clear();
        tfNome.clear();
        tfCpf.clear();
        tfDataNascimento.clear();
        tfEmail.clear();
        tfTelefone.clear();
        tfGenero.clear();
        
        addButton.setDisable(true);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        
        tfDataNascimento.setDisable(true);
    	tfEmail.setDisable(true);
    	tfTelefone.setDisable(true);
    	
		tfNome.setDisable(false);
    	tfCpf.setDisable(false);
		tfGenero.setDisable(false);
    }
    
    @FXML
    void modoBusPaciente(ActionEvent Event) {
    	modoBuscaAtivado = true;
    	limparFormParaBusca();
    	listPacientes();
    	modoBusButton.setText("Outra busca");
    	limparFormButton.setText("Desativar busca");
    	
    }
    
    @FXML
    void tfNomeBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfCpf.setDisable(true);
    		tfGenero.setDisable(true);
    		tfCpf.clear();
    		tfGenero.clear();
    		
    		String nome = tfNome.getText();
    		
    		ObservableList<Paciente> list = FXCollections.observableArrayList(pacienteService.getPacientesNome(nome));
            tbvPacientes.setItems(list);
    	}
    }
    
    @FXML
    void tfCpfBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfNome.setDisable(true);
    		tfGenero.setDisable(true);
    		tfNome.clear();
    		tfGenero.clear();
    		
    		String cpf = tfCpf.getText();
    		
    		ObservableList<Paciente> list = FXCollections.observableArrayList(pacienteService.getPacientesCpf(cpf));
            tbvPacientes.setItems(list);
    	}
    }
    
    @FXML
    void tfGeneroBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfNome.setDisable(true);
    		tfCpf.setDisable(true);
    		tfNome.clear();
    		tfCpf.clear();
    		
    		String genero = tfGenero.getText();
    		
    		ObservableList<Paciente> list = FXCollections.observableArrayList(pacienteService.getPacientesGenero(genero));
            tbvPacientes.setItems(list);
    	}
    }
   
    	
    
    
    
    
    
}