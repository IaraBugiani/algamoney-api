package com.example.algamaney.api.rest.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamaney.api.event.RecursoCriadoEvent;
import com.example.algamaney.api.model.Pessoa;
import com.example.algamaney.api.repository.PessoaRepository;
import com.example.algamaney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private PessoaService service;
	
	@Autowired 
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> listaPessoas(){
		return repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody Pessoa novaPessoa,HttpServletResponse response){
		
		Pessoa pessoaCadastrada = repository.save(novaPessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaCadastrada.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCadastrada);
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscaPessoaPeloCodigo(@PathVariable Long codigo) {
		Pessoa pessoa =  repository.findOne(codigo);
		return pessoa !=null ? ResponseEntity.ok(pessoa) : ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void removerPessoa(@PathVariable Long codigo) {
		repository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualiza(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = service.atualiza(codigo,pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo,@RequestBody Boolean ativo) {
		service.atualizarPropriedadeAtivo(codigo, ativo);
	}
	

}
