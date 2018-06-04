package com.example.algamaney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamaney.api.model.Lancamento;
import com.example.algamaney.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
