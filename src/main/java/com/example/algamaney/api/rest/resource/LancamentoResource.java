package com.example.algamaney.api.rest.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamaney.api.event.RecursoCriadoEvent;
import com.example.algamaney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.example.algamaney.api.model.Lancamento;
import com.example.algamaney.api.repository.LancamentoRepository;
import com.example.algamaney.api.repository.filter.LancamentoFilter;
import com.example.algamaney.api.service.LancamentoService;
import com.example.algamaney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	@Autowired 
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LancamentoService service;
	
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public Page<Lancamento> pesquisarLancamento(LancamentoFilter lancamentoFilter,Pageable pageable){
		return repository.filtrar(lancamentoFilter,pageable);
	}
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public ResponseEntity<Lancamento> novoLancamento(@Valid @RequestBody Lancamento novoLancamento,HttpServletResponse response){
		
		
		Lancamento lancamentoCadastrado = service.salvarLancamento(novoLancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoCadastrado.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoCadastrado);
		
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscaLancamentoPeloCodigo(@PathVariable Long codigo) {
		Lancamento lancamento =  repository.findOne(codigo);
		return lancamento !=null ? ResponseEntity.ok(lancamento) : ResponseEntity.noContent().build();
	}
	
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void removerLancamento(@PathVariable Long codigo) {
		repository.delete(codigo);
	}
	
	
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
		
		String mensagemUsuario = messageSource.getMessage("mensagem.pessoa.inixistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenv = ex.getCause() !=null ? ex.getCause().toString() : ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenv));
		
		return ResponseEntity.badRequest().body(erros);
		
		
	}
	
	
}
