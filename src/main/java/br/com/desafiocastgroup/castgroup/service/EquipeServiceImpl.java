package br.com.desafiocastgroup.castgroup.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.EquipeDao;
import br.com.desafiocastgroup.castgroup.exception.ProcessException;
import br.com.desafiocastgroup.castgroup.model.Equipe;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class EquipeServiceImpl implements EquipeService{
	
	private EquipeDao equipeDao;
	private MessageSource messageSource;
	
	@Autowired
	public EquipeServiceImpl(EquipeDao equipeDao, MessageSource messageSource) {
		this.equipeDao = equipeDao;
		this.messageSource = messageSource;
	}

	@Override
	public Equipe salvar(Equipe equipe) {
		return this.equipeDao.salvar(equipe);
	}

	@Override
	public List<Equipe> listarTodos() {
		List<Equipe> equipes = this.equipeDao.listarTodos();
		if (Util.isListaVazia(equipes)) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("equipe.notfound", null, Locale.getDefault()));
		}
		return equipes;
	}

	@Override
	public Equipe buscarPorId(Long id) {
		Equipe equipe = this.equipeDao.buscarPorId(id);
		if (equipe == null) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("equipe.notfound", null, Locale.getDefault()));
		}
		return equipe;
	}

	@Override
	public boolean existirEquipePorNome(String nome) {
		return this.equipeDao.existirEquipePorNome(nome);
	}

}
