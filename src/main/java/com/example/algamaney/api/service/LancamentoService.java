package com.example.algamaney.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamaney.api.model.Lancamento;
import com.example.algamaney.api.model.Pessoa;
import com.example.algamaney.api.repository.LancamentoRepository;
import com.example.algamaney.api.repository.PessoaRepository;
import com.example.algamaney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository repository;

	public Lancamento salvarLancamento(Lancamento novoLancamento) {
		Pessoa pessoa = pessoaRepository.findOne((novoLancamento.getCodigoPessoa().getCodigo()));
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return repository.save(novoLancamento);
	}

}
