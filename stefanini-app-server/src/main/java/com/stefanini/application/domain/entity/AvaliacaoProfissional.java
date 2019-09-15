package com.stefanini.application.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.stefanini.application.domain.enums.StatusAvaliacao;

@NamedNativeQueries(value = {
	@NamedNativeQuery(name = "AvaliacaoProfissional.listarAvaliacoesPendentesAprovacaoGerente", query = "SELECT av.id as id_avaliacao, av.data_criacao, f.id as id_funcionario, f.nome, f.cpf, f.nivel, f.cargo, f.data_admissao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo FROM avaliacao_profissional av INNER JOIN funcionario f ON f.id = av.funcionario_id INNER JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id INNER JOIN funcionario g ON g.id = cc.gerente_id AND g.usuario_id <> f.usuario_id LEFT JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id WHERE av.status = 'AGUARDANDO_APROVACAO' AND av.bp_avaliador_id IS NOT NULL AND av.gerente_aprovador_id IS NULL AND g.usuario_id = ?1 ORDER BY f.nome ASC"),
	@NamedNativeQuery(name = "AvaliacaoProfissional.listarAvaliacoesPendentesVotacaoBP", query = "SELECT av.id as id_avaliacao, av.data_criacao, f.id as id_funcionario, f.nome, f.cpf, f.nivel, f.cargo, f.data_admissao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo FROM avaliacao_profissional av INNER JOIN funcionario f ON f.id = av.funcionario_id INNER JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id INNER JOIN funcionario g ON g.id = cc.gerente_id LEFT JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id INNER JOIN funcionario bp on bp.centro_custo_id = cc.id AND bp.usuario_id <> f.usuario_id WHERE av.status = 'AGUARDANDO_AVALIACAO' AND av.bp_avaliador_id IS NULL AND bp.usuario_id = ?1 ORDER BY f.nome ASC"),
	@NamedNativeQuery(name = "AvaliacaoProfissional.listarAvaliacoesPendentesVotacaoMentor", query = "SELECT av.id as id_avaliacao, av.data_criacao, f.id as id_funcionario, f.nome, f.cpf, f.nivel, f.cargo, f.data_admissao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo FROM avaliacao_profissional av INNER JOIN funcionario f ON f.id = av.funcionario_id INNER JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id INNER JOIN funcionario g ON g.id = cc.gerente_id INNER JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id AND m.usuario_id <> f.usuario_id WHERE av.status = 'AGUARDANDO_AVALIACAO' AND av.mentor_avaliador_id IS NULL AND m.usuario_id = ?1 ORDER BY f.nome ASC"),
	@NamedNativeQuery(name = "AvaliacaoProfissional.obterRessalvasAvaliacaoAnterior", query = "SELECT av_anterior.ressalvas_gerente FROM avaliacao_profissional av_anterior INNER JOIN avaliacao_profissional av ON av.funcionario_id = av_anterior.funcionario_id WHERE av_anterior.status = 'FINALIZADO' AND av.id = ?1 ORDER BY av_anterior.id DESC LIMIT 1"),
	@NamedNativeQuery(name = "AvaliacaoProfissional.obterDetalhesAvaliacoesFuncionario", query = "SELECT av.id, av.nota_final, m.nome as nomeMentorAvaliador, bp.nome as nomeBpAvaliador, g.nome as nomeGerenteAprovador, av.data_aprovacao_gerente, av.comentarios_gerente, av.ressalvas_gerente FROM avaliacao_profissional av LEFT JOIN funcionario  m ON  m.id = av.mentor_avaliador_id LEFT JOIN funcionario bp ON bp.id = av.bp_avaliador_id LEFT JOIN funcionario  g ON  g.id = av.gerente_aprovador_id WHERE av.status = 'FINALIZADO' AND av.funcionario_id = ?1 ORDER BY av.id DESC")
})
@Entity
@Table(name = "avaliacao_profissional")
public class AvaliacaoProfissional implements Serializable {

	private static final long serialVersionUID = -2708851911261004458L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 32)
	private StatusAvaliacao status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataAvaliacaoMentor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataAvaliacaoBp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataAprovacaoGerente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = true)
	private Funcionario mentorAvaliador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = true)
	private Funcionario bpAvaliador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", nullable = true)
	private Funcionario gerenteAprovador;

	@OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CriterioAvaliacao> criteriosAvaliacao = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", referencedColumnName = "id", nullable = false)
	private Funcionario funcionario;

	@Column(nullable = false, precision = 16, scale = 2)
	private Double notaFinal;

	@Column(length = 600, nullable = true)
	private String comentariosMentor;

	@Column(length = 600, nullable = true)
	private String comentariosBp;

	@Column(length = 600, nullable = true)
	private String comentariosGerente;

	@Column(length = 600, nullable = true)
	private String ressalvasGerente;

	@PrePersist
	public void prePersist() {
		this.dataCriacao = new Date();
		if (this.notaFinal == null) {
			this.notaFinal = 0.0;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusAvaliacao getStatus() {
		return status;
	}

	public void setStatus(StatusAvaliacao status) {
		this.status = status;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAvaliacaoMentor() {
		return dataAvaliacaoMentor;
	}

	public void setDataAvaliacaoMentor(Date dataAvaliacaoMentor) {
		this.dataAvaliacaoMentor = dataAvaliacaoMentor;
	}

	public Date getDataAvaliacaoBp() {
		return dataAvaliacaoBp;
	}

	public void setDataAvaliacaoBp(Date dataAvaliacaoBp) {
		this.dataAvaliacaoBp = dataAvaliacaoBp;
	}

	public Date getDataAprovacaoGerente() {
		return dataAprovacaoGerente;
	}

	public void setDataAprovacaoGerente(Date dataAprovacaoGerente) {
		this.dataAprovacaoGerente = dataAprovacaoGerente;
	}

	public Funcionario getMentorAvaliador() {
		return mentorAvaliador;
	}

	public void setMentorAvaliador(Funcionario mentorAvaliador) {
		this.mentorAvaliador = mentorAvaliador;
	}

	public Funcionario getBpAvaliador() {
		return bpAvaliador;
	}

	public void setBpAvaliador(Funcionario bpAvaliador) {
		this.bpAvaliador = bpAvaliador;
	}

	public Funcionario getGerenteAprovador() {
		return gerenteAprovador;
	}

	public void setGerenteAprovador(Funcionario gerenteAprovador) {
		this.gerenteAprovador = gerenteAprovador;
	}

	public List<CriterioAvaliacao> getCriteriosAvaliacao() {
		return criteriosAvaliacao;
	}

	public void setCriteriosAvaliacao(List<CriterioAvaliacao> criteriosAvaliacao) {
		this.criteriosAvaliacao = criteriosAvaliacao;
	}

	public void addCriterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
		this.criteriosAvaliacao.add(criterioAvaliacao);
		criterioAvaliacao.setAvaliacao(this);
	}

	public void removeCriterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
		this.criteriosAvaliacao.remove(criterioAvaliacao);
		criterioAvaliacao.setAvaliacao(null);
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Double getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(Double notaFinal) {
		this.notaFinal = notaFinal;
	}

	public String getComentariosMentor() {
		return comentariosMentor;
	}

	public void setComentariosMentor(String comentariosMentor) {
		this.comentariosMentor = comentariosMentor;
	}

	public String getComentariosBp() {
		return comentariosBp;
	}

	public void setComentariosBp(String comentariosBp) {
		this.comentariosBp = comentariosBp;
	}

	public String getComentariosGerente() {
		return comentariosGerente;
	}

	public void setComentariosGerente(String comentariosGerente) {
		this.comentariosGerente = comentariosGerente;
	}

	public String getRessalvasGerente() {
		return ressalvasGerente;
	}

	public void setRessalvasGerente(String ressalvasGerente) {
		this.ressalvasGerente = ressalvasGerente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AvaliacaoProfissional other = (AvaliacaoProfissional) obj;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
