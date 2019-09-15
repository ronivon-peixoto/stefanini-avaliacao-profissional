package com.stefanini.application.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.stefanini.application.domain.converter.CargoConverter;
import com.stefanini.application.domain.enums.Cargo;

@NamedNativeQueries(value = {
	@NamedNativeQuery(name = "Funcionario.listarDetalhesFuncionariosGerente", query = "SELECT f.id, f.nome, f.cpf, f.data_admissao, f.nivel, f.cargo, f.data_promocao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo, cc.endereco as endereco_centro_custo FROM funcionario f INNER JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id INNER JOIN funcionario g ON g.id = cc.gerente_id AND g.usuario_id <> f.usuario_id LEFT JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id WHERE g.usuario_id = ?1 ORDER BY f.nome ASC"),
	@NamedNativeQuery(name = "Funcionario.listarDetalhesFuncionariosBP", query = "SELECT f.id, f.nome, f.cpf, f.data_admissao, f.nivel, f.cargo, f.data_promocao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo, cc.endereco as endereco_centro_custo FROM funcionario f INNER JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id INNER JOIN funcionario g ON g.id = cc.gerente_id LEFT JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id INNER JOIN funcionario bp on bp.centro_custo_id = cc.id AND bp.usuario_id <> f.usuario_id WHERE bp.usuario_id = ?1 ORDER BY f.nome ASC"),
	@NamedNativeQuery(name = "Funcionario.listarDetalhesFuncionariosMentor", query = "SELECT f.id, f.nome, f.cpf, f.data_admissao, f.nivel, f.cargo, f.data_promocao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo, cc.endereco as endereco_centro_custo FROM funcionario f INNER JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id INNER JOIN funcionario g ON g.id = cc.gerente_id INNER JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id AND m.usuario_id <> f.usuario_id WHERE m.usuario_id = ?1 ORDER BY f.nome ASC"),
	@NamedNativeQuery(name = "Funcionario.obterDetalhesFuncionario", query = "SELECT u.username, u.email, f.id, f.nome, f.cpf, f.data_admissao, f.nivel, f.cargo, f.data_promocao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo, cc.endereco as endereco_centro_custo FROM funcionario f INNER JOIN user u ON u.id = f.usuario_id INNER JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id INNER JOIN funcionario g ON g.id = cc.gerente_id LEFT JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id WHERE f.id = ?1"),
	@NamedNativeQuery(name = "Funcionario.obterDetalhesFuncionarioPorUsuarioId", query = "SELECT u.username, u.email, f.id, f.nome, f.cpf, f.data_admissao, f.nivel, f.cargo, f.data_promocao, m.nome as nome_mentor, g.nome as nome_gerente, cc.nome as nome_centro_custo, cc.endereco as endereco_centro_custo FROM user u LEFT JOIN funcionario f ON f.usuario_id = u.id LEFT JOIN centro_custo cc ON cc.id = f.centro_custo_id AND cc.empresa_id = f.empresa_id LEFT JOIN funcionario g ON g.id = cc.gerente_id LEFT JOIN funcionario m ON m.id = f.mentor_id AND m.centro_custo_id = cc.id WHERE u.id = ?1"),
	@NamedNativeQuery(name = "Funcionario.obterFuncionariosParaAvaliavao", query = "SELECT DISTINCT f.* FROM funcionario f INNER JOIN user u ON u.id = f.usuario_id INNER JOIN user_role ur ON ur.user_id = u.id INNER JOIN role r ON r.id = ur.role_id LEFT JOIN avaliacao_profissional av_atual ON av_atual.funcionario_id = f.id AND av_atual.status <> 'FINALIZADO' LEFT JOIN avaliacao_profissional av ON av.funcionario_id = f.id AND av.status = 'FINALIZADO' WHERE av_atual.id IS NULL AND r.name = 'ROLE_FUNCIONARIO' AND ((av.id IS NULL AND DATEDIFF(month, f.data_admissao, DATEADD(dd, -DAY(DATEADD(m,1,GETDATE())), DATEADD(m,1,GETDATE()))) >= ?1) OR (av.id IS NOT NULL AND DATEDIFF(month, av.data_criacao, DATEADD(dd, -DAY(DATEADD(m,1,GETDATE())), DATEADD(m,1,GETDATE()))) >= ?1))", resultClass = Funcionario.class),
	@NamedNativeQuery(name = "Funcionario.obterFuncionarioPorUsuarioID", query = "SELECT f.* FROM funcionario f WHERE f.usuario_id = ?1", resultClass = Funcionario.class)
})
@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = -3782352553778378440L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataAdmissao;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
	private User usuario;

	@Column(nullable = false)
	private Integer nivel;

	@Convert(converter = CargoConverter.class)
	@Column(nullable = false, length = 32)
	private Cargo cargo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dataPromocao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
	private Empresa empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_custo_id", referencedColumnName = "id", nullable = false)
	private CentroCusto centroCusto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_id")
	private Funcionario mentor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Date getDataPromocao() {
		return dataPromocao;
	}

	public void setDataPromocao(Date dataPromocao) {
		this.dataPromocao = dataPromocao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public Funcionario getMentor() {
		return mentor;
	}

	public void setMentor(Funcionario mentor) {
		this.mentor = mentor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
