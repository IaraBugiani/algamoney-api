package com.example.algamaney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamaney.api.model.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa, Long>{

}
