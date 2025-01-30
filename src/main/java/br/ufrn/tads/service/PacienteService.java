package br.ufrn.tads.service;

import java.util.List;

import br.ufrn.tads.model.Paciente;
import br.ufrn.tads.repository.PacienteDao;

public class PacienteService {
    
    private PacienteDao pacienteDao;

    public PacienteService() {
        pacienteDao = new PacienteDao();
    }
   
    public List<Paciente> getPacientes() {
        return pacienteDao.findAll();
    }

    public boolean save(Paciente paciente) {
        return pacienteDao.save(paciente);
    }

    public boolean update(Paciente paciente, String[] params) {
        return pacienteDao.update(paciente, params);
    }

    public boolean delete(Long id) {
        return pacienteDao.delete(id);
    }
    
    public List<Paciente> getPacientesNome(String nome) { 
    	
		String nomeSql1 = "%" + nome + "%";
		String nomeSql2 = "%" + nome;
    	
        return pacienteDao.findAllNome(nomeSql1, nomeSql2);
    }
    
    public List<Paciente> getPacientesCpf(String cpf) { 
    	
		String cpfSql1 = "%" + cpf + "%";
		String cpfSql2 = "%" + cpf;
    	
        return pacienteDao.findAllCpf(cpfSql1, cpfSql2);
    }
    
    public List<Paciente> getPacientesGenero(String genero) { 
    	
		String generoSql1 = "%" + genero + "%";
		String generoSql2 = "%" + genero;
    	
        return pacienteDao.findAllGenero(generoSql1, generoSql2);
    }
    
    
    
    
    
    
}