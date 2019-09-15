package com.stefanini.application.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.stefanini.application.domain.converter.ValorCriterioConverter;
import com.stefanini.application.domain.enums.ValorCriterio;

@Entity
@Table(name = "criterio_avaliacao")
public class CriterioAvaliacao implements Serializable {

	private static final long serialVersionUID = 895799488929516486L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avaliacao_id", referencedColumnName = "id", nullable = false)
	private AvaliacaoProfissional avaliacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "valor_empresa_id", referencedColumnName = "id", nullable = false)
	private ValorEmpresa valorEmpresa;

	@Convert(converter = ValorCriterioConverter.class)
	@Column(nullable = true)
	private ValorCriterio notaMentor;

	@Convert(converter = ValorCriterioConverter.class)
	@Column(nullable = true)
	private ValorCriterio notaBp;

	public CriterioAvaliacao() {
		super();
	}

	public CriterioAvaliacao(ValorEmpresa valorEmpresa) {
		super();
		this.valorEmpresa = valorEmpresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AvaliacaoProfissional getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(AvaliacaoProfissional avaliacao) {
		this.avaliacao = avaliacao;
	}

	public ValorEmpresa getValorEmpresa() {
		return valorEmpresa;
	}

	public void setValorEmpresa(ValorEmpresa valorEmpresa) {
		this.valorEmpresa = valorEmpresa;
	}

	public ValorCriterio getNotaMentor() {
		return notaMentor;
	}

	public void setNotaMentor(ValorCriterio notaMentor) {
		this.notaMentor = notaMentor;
	}

	public ValorCriterio getNotaBp() {
		return notaBp;
	}

	public void setNotaBp(ValorCriterio notaBp) {
		this.notaBp = notaBp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avaliacao == null) ? 0 : avaliacao.hashCode());
		result = prime * result + ((valorEmpresa == null) ? 0 : valorEmpresa.hashCode());
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
		CriterioAvaliacao other = (CriterioAvaliacao) obj;
		if (avaliacao == null) {
			if (other.avaliacao != null)
				return false;
		} else if (!avaliacao.equals(other.avaliacao))
			return false;
		if (valorEmpresa == null) {
			if (other.valorEmpresa != null)
				return false;
		} else if (!valorEmpresa.equals(other.valorEmpresa))
			return false;
		return true;
	}

	@Transient
	public Double calcularNotaMentor() {
		Double peso = this.valorEmpresa.getPeso();
		Double sum = 0.0;
		if (this.notaMentor != null && this.notaMentor.getValor() > 0) {
			sum += ((peso * this.notaMentor.getValor()) / 100);
		}
		return sum;
	}

	@Transient
	public Double calcularNotaBp() {
		Double peso = this.valorEmpresa.getPeso();
		Double sum = 0.0;
		if (this.notaBp != null && this.notaBp.getValor() > 0) {
			sum += ((peso * this.notaBp.getValor()) / 100);
		}
		return sum;
	}

	@Transient
	public Double calcularNota() {
		Double somaNotas = Double.sum(this.calcularNotaMentor(), this.calcularNotaBp());
		if (this.notaMentor != null && this.notaBp != null) {
			return ((somaNotas > 0) ? (somaNotas / 2) : somaNotas);
		} else {
			return somaNotas;
		}
	}

}
