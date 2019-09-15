package com.stefanini.application.message.response;

import java.util.List;

import com.stefanini.application.domain.dto.DetalhesAvaliacaoProfissional;
import com.stefanini.application.domain.dto.DetalhesFuncionario;

public class InfoFuncionarioResponse {

	private DetalhesFuncionario funcionario;

	private Boolean promover;

	private List<DetalhesAvaliacaoProfissional> detalhesAvaliacoes;

	public DetalhesFuncionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(DetalhesFuncionario funcionario) {
		this.funcionario = funcionario;
	}

	public Boolean getPromover() {
		this.promover = false;
		if (funcionario != null && funcionario.getCargo() != null && funcionario.getNivel() != null) {
			this.promover = ((funcionario.getCargo().equalsIgnoreCase("Desenvolvedor Junior") && funcionario.getNivel() > 2)
					|| (funcionario.getCargo().equalsIgnoreCase("Desenvolvedor Pleno") && funcionario.getNivel() > 3)
					|| (funcionario.getCargo().equalsIgnoreCase("Desenvolvedor Senior") && funcionario.getNivel() > 4));
		}
		return promover;
	}

	public void setPromover(Boolean promover) {
		this.promover = promover;
	}

	public List<DetalhesAvaliacaoProfissional> getDetalhesAvaliacoes() {
		return detalhesAvaliacoes;
	}

	public void setDetalhesAvaliacoes(List<DetalhesAvaliacaoProfissional> detalhesAvaliacoes) {
		this.detalhesAvaliacoes = detalhesAvaliacoes;
	}

}
