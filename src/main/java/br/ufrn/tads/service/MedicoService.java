package br.ufrn.tads.service;

import java.util.List;

import br.ufrn.tads.model.Medico;
import br.ufrn.tads.repository.MedicoDao;

public class MedicoService {
    
    private MedicoDao medicoDao;

    public MedicoService() {
    	medicoDao = new MedicoDao();
    } 
    
    public List<Medico> getMedicos() {
        return medicoDao.findAll();
    }
    

    public boolean save(Medico medico) {
        return medicoDao.save(medico);
    }

    public boolean update(Medico medico, String[] params) {
        return medicoDao.update(medico, params);
    }

    public boolean delete(Long id) {
        return medicoDao.delete(id);
    }
    
    public List<Medico> getMedicosNome(String nome) { 
    	
		String nomeSql1 = "%" + nome + "%";
		String nomeSql2 = "%" + nome;
    	
        return medicoDao.findAllNome(nomeSql1, nomeSql2);
    }
    
    public List<Medico> getMedicosCrm(String crm) { 
    	
		String crmSql1 = "%" + crm + "%";
		String crmSql2 = "%" + crm;
    	
        return medicoDao.findAllCrm(crmSql1, crmSql2);
    }
    
    public List<Medico> getMedicosEspecialidade(String especialidade) { 
    	
		String especialidadeSql1 = "%" + especialidade + "%";
		String especialidadeSql2 = "%" + especialidade;
    	
        return medicoDao.findAllEspecialidade(especialidadeSql1, especialidadeSql2);
    }

	
    
    
}