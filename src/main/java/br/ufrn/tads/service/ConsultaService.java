package br.ufrn.tads.service;

import java.util.List;

import br.ufrn.tads.model.Consulta;
import br.ufrn.tads.repository.ConsultaDao;

public class ConsultaService {
    
    private ConsultaDao consultaDao;

    public ConsultaService() {
    	consultaDao = new ConsultaDao();
    }
   
    public List<Consulta> getConsultas() {
        return consultaDao.findAll();
    }

    public boolean save(Consulta consulta) {
        return consultaDao.save(consulta);
    }

    public boolean update(Consulta consulta, String[] params) {
        return consultaDao.update(consulta, params);
    }

    public boolean delete(Long id) {
        return consultaDao.delete(id);
    }
    
    public List<Consulta> getConsultasMedico(Long medico) { 
    	
		String idMedSql = medico.toString();
    	
        return consultaDao.findAllMedico(idMedSql);
    }
    
    public List<Consulta> getConsultasPaciente(Long paciente) { 
    	
		String idPacSql = paciente.toString();
    	
        return consultaDao.findAllPaciente(idPacSql);
    }
    
    public List<Consulta> getConsultasData(String data) { 
    	
		String dataSql1 = "%" + data + "%";
		String dataSql2 = "%" + data;
    	
        return consultaDao.findAllData(dataSql1, dataSql2);
    }
    
}
 
