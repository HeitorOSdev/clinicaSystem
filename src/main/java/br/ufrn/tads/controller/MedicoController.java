package br.ufrn.tads.controller;

import java.io.IOException;

import br.ufrn.tads.model.Medico;
import br.ufrn.tads.service.MedicoService;

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

public class MedicoController {
	
	boolean modoBuscaAtivado = false;
	
    private MedicoService medicoService;
	
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
    
//    @FXML
//    private ObservableList<Medico> ObListMedico; ---> estah feito mais abaixo, depois de instanciar o service
    
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
    private Button addButton;
    
    @FXML
    private Button ediButton;
    
    @FXML
    private Button excButton;   
    
    @FXML
    private Button modoBusButton; 
    
    @FXML
    private Button limparFormButton; 
    
    public MedicoController() {
        medicoService = new MedicoService();
    }
   
    
    public void initialize() throws IOException { //ja ja volto
    	
        
//        Image img = new Image("listicon.png");
//        ImageView imgView = new ImageView(img);
//        listButton.setGraphic(imgView);
//        img = new Image("nometads.png");
//        imgvLogo.setImage(img);
//        tbvMedicos.getColumns().addAll(colId, colNome, colCrm, colEspecialidade, colEmail, colTelefone);
    	
    	colId.setCellValueFactory(new PropertyValueFactory<Medico, Long>("id"));
    	colNome.setCellValueFactory(new PropertyValueFactory<Medico, String>("nome"));
    	colCrm.setCellValueFactory(new PropertyValueFactory<Medico, String>("crm"));
    	colEspecialidade.setCellValueFactory(new PropertyValueFactory<Medico, String>("especialidade"));
    	colEmail.setCellValueFactory(new PropertyValueFactory<Medico, String>("email"));
    	colTelefone.setCellValueFactory(new PropertyValueFactory<Medico, String>("telefone"));

    	listMedicos();
    	
    	modoBusButton.setDisable(false);
    	modoBusButton.setVisible(true);
    	
    	
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
        
        modoBusButton.setDisable(true);
        addButton.setDisable(true);
        ediButton.setDisable(false);
        excButton.setDisable(false);
    }
    
    @FXML
    private void listMedicos(ActionEvent event) throws IOException {
                
        ObservableList<Medico> list = FXCollections.observableArrayList(medicoService.getMedicos());
        tbvMedicos.setItems(list);     
        
    }
    
    private void listMedicos() {
                
        ObservableList<Medico> list = FXCollections.observableArrayList(medicoService.getMedicos());
        tbvMedicos.setItems(list);     
        
    }
    
    @FXML
    private void addMedico(ActionEvent event) throws IOException {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
//        	if(!crm == crmList) {
        		String nome = tfNome.getText();
	            String crm = tfCrm.getText();
	            String especialidade = tfEspecialidade.getText();
	            String email = tfEmail.getText();
	            String telefone = tfTelefone.getText();
	            
	            Medico medico = new Medico(nome, crm, especialidade, telefone, email);
	
	            if (medicoService.save(medico)) {
	            	listMedicos();
	            }
//        	}else{
//        		alert('crm ja existe digite outro') + deixar caixa vermelha
//        	}
        }
    }

    @FXML
    void excMedico(ActionEvent event) {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
        	if (medicoService.delete(Long.parseLong(tfId.getText()))) {
                int idx = tbvMedicos.getSelectionModel().getSelectedIndex();
                tbvMedicos.getItems().remove(idx);
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
    void ediMedico(ActionEvent event) {
        if (!tfNome.getText().isEmpty() && !tfCrm.getText().isEmpty() && !tfEspecialidade.getText().isEmpty() && !tfEmail.getText().isEmpty() && !tfTelefone.getText().isEmpty()) {
        	
        	excButton.setDisable(true);
        	
        	Long id = Long.parseLong(tfId.getText());
        	String nome = tfNome.getText();
            String crm = tfCrm.getText();
            String especialidade = tfEspecialidade.getText();
            String email = tfEmail.getText();
            String telefone = tfTelefone.getText();
            
            Medico medico = new Medico(id, nome, crm, especialidade, telefone, email);
            
            medicoService.update(medico, null);
            
            listMedicos();
        }
    }
    
    @FXML
    void limparForm(ActionEvent event) {
       
    	modoBuscaAtivado = false;
    	modoBusButton.setText("Modo busca");
    	limparFormButton.setText("Limpar campos de pesquisa");
    	
    	tfId.clear();
        tfNome.clear();
        tfCrm.clear();
        tfEspecialidade.clear();
        tfEmail.clear();
        tfTelefone.clear();
        
        tfNome.setDisable(false);
        tfCrm.setDisable(false);
        tfEspecialidade.setDisable(false);
        tfEmail.setDisable(false);
        tfTelefone.setDisable(false);
        
        modoBusButton.setDisable(false);
        addButton.setDisable(false);
        ediButton.setDisable(true);
        excButton.setDisable(true);
        
        listMedicos();
        
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
    void modoBusMedico(ActionEvent Event) {
    	modoBuscaAtivado = true;
    	limparFormParaBusca();
    	modoBusButton.setText("Outra busca");
    	limparFormButton.setText("Desativar busca");
		//metodo para mudar o nome do botao de "limpar campos" para "resetar"
    	
    }
    
    /////////////////////////////////////////////
//    @FXML void busMedico(ActionEvent Event) {} // remover e mudar o nome do outro para o que esse tem
    ////////////////////////////////////////////  
    
    @FXML
    void tfNomeBuscar(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfCrm.setDisable(true);
    		tfEspecialidade.setDisable(true);
    		tfCrm.clear();
    		tfEspecialidade.clear();
    		
    		String nome = tfNome.getText();
    		
    		ObservableList<Medico> list = FXCollections.observableArrayList(medicoService.getMedicosNome(nome));
            tbvMedicos.setItems(list);
    	}
    }
    
    @FXML
    void tfCrmBusca(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfNome.setDisable(true);
    		tfEspecialidade.setDisable(true);
    	}
    	
    }
    
    @FXML
    void tfEspecialidadeBusca(ActionEvent Event) {
    	if(modoBuscaAtivado) {
    		tfCrm.setDisable(true);
    		tfNome.setDisable(true);
    	}
    	
    }
   
    	
    
    
    
    
    
}