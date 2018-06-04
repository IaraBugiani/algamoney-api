package com.example.algamaney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.algamaney.api.enums.TipoLancamentoEnum;

@Entity
@Table(name="lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	@NotNull
	private String descricao;
	
	@Column(name="data_vencimento")
	private LocalDate dataVencimento;
	
	@Column(name="data_pagamento")
	private LocalDate dataPagamento;
	
	private BigDecimal valor;
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_lancamento")
	private TipoLancamentoEnum tipoLancamento;
	
	@ManyToOne
	@JoinColumn(name="codigo_categoria")
	private Categoria codigoCategoria;
	
	@ManyToOne
	@JoinColumn(name="codigo_pessoa")
	private Pessoa codigoPessoa;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public TipoLancamentoEnum getTipoLancamento() {
		return tipoLancamento;
	}
	public void setTipoLancamento(TipoLancamentoEnum tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
	public Categoria getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(Categoria codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
	public Pessoa getCodigoPessoa() {
		return codigoPessoa;
	}
	public void setCodigoPessoa(Pessoa codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
}
