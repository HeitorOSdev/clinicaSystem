package br.ufrn.tads.controller;

import java.io.IOException;
import java.util.Date;

import br.ufrn.tads.model.Consulta;
import br.ufrn.tads.service.ConsultaService;

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

public class ConsultaController {
	
	boolean modoBuscaAtivado = false;
	
    private ConsultaService consultaService;
    
	@FXML
    private TextField tfId;
	
	@FXML
    private TextField tfPaciente;
	
	@FXML
    private TextField tfMedico;
	
	@FXML
    private TextField tfData;
	
	@FXML
    private TextField tfQueixa;
	
	@FXML
    private TextField tfDescricao;
	
	@FXML
    private TextField tfRelatosClinicos;

    @FXML
    private TableView<Consulta> tbvConsultas;
    
    @FXML
    private TableColumn<Consulta, Long> colId;
    
    @FXML
    private TableColumn<Consulta, String> colPaciente;
    
    @FXML
    private TableColumn<Consulta, String> colMedico;
    
    @FXML
    private TableColumn<Consulta, Date> colData;
    
    @FXML
    private TableColumn<Consulta, String> colQueixa;
    
    @FXML
    private TableColumn<Consulta, String> colDescricao;
    
    @FXML
    private TableColumn<Consulta, String> colRelatosClinicos;
    
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
    
    public ConsultaController() {
        consultaService = new ConsultaService();
    }
   
    
    public void initialize() throws IOException {

        //(id, fkMedico, fkPaciente, data, queixa, descricao, relatosClinicos)
        // Criar uma view para pesquisar e mostrar diretamente os nomes dos pacientes e dos medicos
    	
    	colId.setCellValueFactory(new PropertyValueFactory<Consulta, Long>("id"));
    	colNome.setCellValueFactory(new PropertyValueFactory<Consulta, String>("nome"));
    	colCrm.setCellValueFactory(new PropertyValueFactory<Consulta, String>("crm"));
    	colEspecialidade.setCellValueFactory(new PropertyValueFactory<Consulta, String>("especialidade"));
    	colEmail.setCellValueFactory(new PropertyValueFactory<Consulta, String>("email"));
    	colTelefone.setCellValueFactory(new PropertyValueFactory<Consulta, String>("telefone"));

    	listConsultas();
    	
    	modoBusButton.setDisable(false);
    	modoBusButton.setVisible(true);
    	
    	
        addButton.setVisible(true);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        
        
        
              
    }
    
    //método para obter itens da linha selecionada da tabela e copiar para o form com os text fields
    @FXML
    void getItem(MouseEvent event) {
        Integer idx = tbvConsultas.getSelectionModel().getSelectedIndex();
        if (idx <= -1) return;
        
        if (modoBuscaAtivado) desativarBusca();
        
        tfId.setText(String.valueOf(colId.getCellData(idx)));
        tfNome.setText(colNome.getCellData(idx));
        tfCrm.setText(colCrm.getCellData(idx));
        tfEspecialidade.setText(colEspecialidade.getCellData(idx));
        tfEmail.setText(colEmail.getCellData(idx));
        tfTelefone.setText(colTelefone.getCellData(idx));
        
        modoBusButton.setDisable(true);
        addButton.setDisable(true);
        ediButton.setDisable(false);
        excButton.setDisable(false);
        
        
        
    }
    
    @FXML
    private void listConsultas(ActionEvent event) throws IOException {
                
        ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultas());
        tbvConsultas.setItems(list);
        
    }
    
    private void listConsultas() {
                
        ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultas());
        tbvConsultas.setItems(list);  
        
    }
    
    @FXML
    private void addConsulta(ActionEvent event) throws IOException {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
//        	if(!crm == crmList) {
        		String nome = tfNome.getText();
	            String crm = tfCrm.getText();
	            String especialidade = tfEspecialidade.getText();
	            String email = tfEmail.getText();
	            String telefone = tfTelefone.getText();
	            
	            Consulta consulta = new Consulta(nome, crm, especialidade, telefone, email);
	
	            if (consultaService.save(consulta)) {
	            	listConsultas();
	            }
//        	}else{
//        		alert('crm ja existe, nao foi possivel adicionar') + deixar caixa vermelha
//        	}
        }
    }

    @FXML
    void excConsulta(ActionEvent event) {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
        	if (consultaService.delete(Long.parseLong(tfId.getText()))) {
                int idx = tbvConsultas.getSelectionModel().getSelectedIndex();
                tbvConsultas.getItems().remove(idx);
                tfId.clear();
                tfNome.clear();
                tfCrm.clear();
                tfEspecialidade.clear();
                tfEmail.clear();
                tfTelefone.clear();
                
                addButton.setDisable(false);
                excButton.setDisable(true);
                ediButton.setDisable(true);
            }
        }
    }
    
    @FXML
    void ediConsulta(ActionEvent event) {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
        	
        	excButton.setDisable(true);
        	
        	Long id = Long.parseLong(tfId.getText());
        	String nome = tfNome.getText();
            String crm = tfCrm.getText();
            String especialidade = tfEspecialidade.getText();
            String email = tfEmail.getText();
            String telefone = tfTelefone.getText();
            
            Consulta consulta = new Consulta(id, nome, crm, especialidade, telefone, email);
            
            consultaService.update(consulta, null);
            
            listConsultas();
        }
    }
    
    @FXML
    void limparForm(ActionEvent event) {
    	
        desativarBusca();
    	tfId.clear();
        tfNome.clear();
        tfCrm.clear();
        tfEspecialidade.clear();
        tfEmail.clear();
        tfTelefone.clear();
        
        modoBusButton.setDisable(false);
        addButton.setDisable(false);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        listConsultas();
        
    }
    
    @FXML
    void desativarBusca() {
    	
    	modoBuscaAtivado = false;
    	modoBusButton.setText("Modo busca");
    	limparFormButton.setText("Limpar campos de pesquisa");
    	
    	tfNome.setDisable(false);
        tfCrm.setDisable(false);
        tfEspecialidade.setDisable(false);
        tfEmail.setDisable(false);
        tfTelefone.setDisable(false);

    }
    
    @FXML
    void limparFormParaBusca() {
        tfId.clear();
        tfNome.clear();
        tfCrm.clear();
        tfEspecialidade.clear();
        tfEmail.clear();
        tfTelefone.clear();
        
        addButton.setDisable(true);
        ediButton.setDisable(true);
        excButton.setDisable(true);
    	tfEmail.setDisable(true);
    	tfTelefone.setDisable(true);
    	
		tfNome.setDisable(false);
    	tfCrm.setDisable(false);
		tfEspecialidade.setDisable(false);
    }
    
    @FXML
    void modoBusConsulta(ActionEvent Event) {
    	modoBuscaAtivado = true;
    	limparFormParaBusca();
    	listConsultas();
    	modoBusButton.setText("Outra busca");
    	limparFormButton.setText("Desativar busca");
		//metodo para mudar o nome do botao de "limpar campos" para "resetar"
    	
    }
    
    @FXML
    void tfNomeBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfCrm.setDisable(true);
    		tfEspecialidade.setDisable(true);
    		tfCrm.clear();
    		tfEspecialidade.clear();
    		
    		String nome = tfNome.getText();
    		
    		ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultasNome(nome));
            tbvConsultas.setItems(list);
    	}
    }
    
    @FXML
    void tfCrmBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfNome.setDisable(true);
    		tfEspecialidade.setDisable(true);
    		tfNome.clear();
    		tfEspecialidade.clear();
    		
    		String crm = tfCrm.getText();
    		
    		ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultasCrm(crm));
            tbvConsultas.setItems(list);
    	}
    }
    
    @FXML
    void tfEspecialidadeBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfNome.setDisable(true);
    		tfCrm.setDisable(true);
    		tfNome.clear();
    		tfCrm.clear();
    		
    		String especialidade = tfEspecialidade.getText();
    		
    		ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultasEspecialidade(especialidade));
            tbvConsultas.setItems(list);
    	}
    }
   
    	
    
    
    
    
    
}