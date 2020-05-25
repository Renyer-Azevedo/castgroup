package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.EquipeDao;
import br.com.desafiocastgroup.castgroup.model.Equipe;

@Service
public class EquipeServiceImpl implements EquipeService{
	
	private EquipeDao equipeDao;
	
	@Autowired
	public EquipeServiceImpl(EquipeDao equipeDao) {
		this.equipeDao = equipeDao;
	}

	@Override
	public Equipe salvar(Equipe equipe) {
		return this.equipeDao.salvar(equipe);
	}

	@Override
	public List<Equipe> listarTodos() {
		return this.equipeDao.listarTodos();
	}

	@Override
	public Equipe buscarPorId(Long id) {
		return this.equipeDao.buscarPorId(id);
	}

	@Override
	public boolean existirEquipePorNome(String nome) {
		return this.equipeDao.existirEquipePorNome(nome);
	}

}
