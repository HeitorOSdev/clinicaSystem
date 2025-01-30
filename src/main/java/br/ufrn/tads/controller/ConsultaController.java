package br.ufrn.tads.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufrn.tads.App;
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
	
    @FXML
    private Button voltarButton;
	
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
    private TableColumn<Consulta, Long> colPaciente;
    
    @FXML
    private TableColumn<Consulta, Long> colMedico;
    
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
    
    @FXML
    private void switchToPrincipal() throws IOException {
        App.setRoot("telaPrincipal", null);
    }
    
    public ConsultaController() {
        consultaService = new ConsultaService();
    }
   
    
    public void initialize() throws IOException {
    	
    	colId.setCellValueFactory(new PropertyValueFactory<Consulta, Long>("id"));
    	colMedico.setCellValueFactory(new PropertyValueFactory<Consulta, Long>("fkMedico")); //referenciar aqui o nome de medico atraves de uma view... possivelmente serah aqui
    	colPaciente.setCellValueFactory(new PropertyValueFactory<Consulta, Long>("fkPaciente"));
    	colData.setCellValueFactory(new PropertyValueFactory<Consulta, Date>("data"));
    	colQueixa.setCellValueFactory(new PropertyValueFactory<Consulta, String>("queixa"));
    	colDescricao.setCellValueFactory(new PropertyValueFactory<Consulta, String>("descricao"));
    	colRelatosClinicos.setCellValueFactory(new PropertyValueFactory<Consulta, String>("relatosClinicos"));
    	
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
        tfMedico.setText(String.valueOf(colMedico.getCellData(idx)));
        tfPaciente.setText(String.valueOf(colPaciente.getCellData(idx)));
        tfData.setText(colData.getCellData(idx).toString());
        tfQueixa.setText(colQueixa.getCellData(idx));
        tfDescricao.setText(colDescricao.getCellData(idx));
        tfRelatosClinicos.setText(colRelatosClinicos.getCellData(idx));
        
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
    private void addConsulta(ActionEvent event) throws IOException, ParseException {
        if (!tfMedico.getText().isEmpty() && !tfPaciente.getText().isEmpty() && !tfData.getText().isEmpty() && !tfQueixa.getText().isEmpty() && !tfDescricao.getText().isEmpty() && !tfRelatosClinicos.getText().isEmpty()) {

        	SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

    		Long medico = Long.parseLong(tfMedico.getText());
    		Long paciente = Long.parseLong(tfPaciente.getText());
            Date data = formatador.parse(tfData.getText());
            String queixa = tfQueixa.getText();
            String descricao = tfDescricao.getText();
            String relatosClinicos = tfRelatosClinicos.getText();
            
            Consulta consulta = new Consulta(medico, paciente, data, queixa, descricao, relatosClinicos);

            if (consultaService.save(consulta)) {
            	listConsultas();
            }
        }
    }

    @FXML
    void excConsulta(ActionEvent event) {
        if (!tfMedico.getText().isEmpty() && !tfPaciente.getText().isEmpty() && !tfData.getText().isEmpty() && !tfQueixa.getText().isEmpty() && !tfDescricao.getText().isEmpty() && !tfRelatosClinicos.getText().isEmpty()) {
        	if (consultaService.delete(Long.parseLong(tfId.getText()))) {
        		
                int idx = tbvConsultas.getSelectionModel().getSelectedIndex();
                tbvConsultas.getItems().remove(idx);
                tfId.clear();
                tfMedico.clear();
                tfPaciente.clear();
                tfData.clear();
                tfQueixa.clear();
                tfDescricao.clear();
                tfRelatosClinicos.clear();
                
                addButton.setDisable(false);
                excButton.setDisable(true);
                ediButton.setDisable(true);
            }
        }
    }
    
    @FXML
    void ediConsulta(ActionEvent event) throws ParseException {
        if (!tfMedico.getText().isEmpty() && !tfPaciente.getText().isEmpty() && !tfData.getText().isEmpty() && !tfQueixa.getText().isEmpty() && !tfDescricao.getText().isEmpty() && !tfRelatosClinicos.getText().isEmpty()) {
        	
        	excButton.setDisable(true);
        	
        	SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        	
        	Long id = Long.parseLong(tfId.getText());
    		Long medico = Long.parseLong(tfMedico.getText());
    		Long paciente = Long.parseLong(tfPaciente.getText());
            Date data = formatador.parse(tfData.getText());
            String queixa = tfQueixa.getText();
            String descricao = tfDescricao.getText();
            String relatosClinicos = tfRelatosClinicos.getText();
            
            Consulta consulta = new Consulta(id, medico, paciente, data, queixa, descricao, relatosClinicos);
            
            consultaService.update(consulta, null);
            
            listConsultas();
        }
    }
    
    @FXML
    void limparForm(ActionEvent event) {
    	
        desativarBusca();
        tfId.clear();
        tfMedico.clear();
        tfPaciente.clear();
        tfData.clear();
        tfQueixa.clear();
        tfDescricao.clear();
        tfRelatosClinicos.clear();
        
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
    	
        tfMedico.setDisable(false);
        tfPaciente.setDisable(false);
        tfData.setDisable(false);
        tfQueixa.setDisable(false);
        tfDescricao.setDisable(false);
        tfRelatosClinicos.setDisable(false);

    }
    
    @FXML
    void limparFormParaBusca() {
        tfId.clear();
        tfMedico.clear();
        tfPaciente.clear();
        tfData.clear();
        tfQueixa.clear();
        tfDescricao.clear();
        tfRelatosClinicos.clear();
        
        addButton.setDisable(true);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        
        //(id, fkMedico, fkPaciente, data, queixa, descricao, relatosClinicos)
        // Criar uma view para pesquisar e mostrar diretamente os nomes dos pacientes e dos medicos
        
    	tfQueixa.setDisable(true);
    	tfDescricao.setDisable(true);
    	tfRelatosClinicos.setDisable(true);
    	
		tfMedico.setDisable(false);
    	tfPaciente.setDisable(false);
		tfData.setDisable(false);
    }
    
    @FXML
    void modoBusConsulta(ActionEvent Event) {
    	modoBuscaAtivado = true;
    	limparFormParaBusca();
    	listConsultas();
    	modoBusButton.setText("Outra busca");
    	limparFormButton.setText("Desativar busca");    	
    }
    
    @FXML
    void tfMedicoBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfPaciente.setDisable(true);
    		tfData.setDisable(true);
    		tfPaciente.clear();
    		tfData.clear();
    		
    		Long medico = Long.parseLong(tfMedico.getText());
    		
    		ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultasMedico(medico));
            tbvConsultas.setItems(list);
    	}
    }
    
    @FXML
    void tfPacienteBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfMedico.setDisable(true);
    		tfData.setDisable(true);
    		tfMedico.clear();
    		tfData.clear();
    		
    		Long paciente = Long.parseLong(tfPaciente.getText());
    		
    		ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultasPaciente(paciente));
            tbvConsultas.setItems(list);
    	}
    }
    
    @FXML
    void tfDataBuscar(ActionEvent Event) { // suspeito que aqui vai ter problema com datas
    	if(modoBuscaAtivado) {
    		tfMedico.setDisable(true);
    		tfPaciente.setDisable(true);
    		tfMedico.clear();
    		tfPaciente.clear();
    		
    		String data = tfData.getText();
    		
    		ObservableList<Consulta> list = FXCollections.observableArrayList(consultaService.getConsultasData(data));
            tbvConsultas.setItems(list);
    	}
    	

    	
    }
   
    	
    
    
    
    
    
}