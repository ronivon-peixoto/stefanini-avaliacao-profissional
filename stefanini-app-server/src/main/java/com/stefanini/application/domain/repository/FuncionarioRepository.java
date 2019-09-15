package com.stefanini.application.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefanini.application.domain.dto.DetalhesFuncionario;
import com.stefanini.application.domain.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	List<DetalhesFuncionario> listarDetalhesFuncionariosGerente(Long usuarioId);

	List<DetalhesFuncionario> listarDetalhesFuncionariosBP(Long usuarioId);

	List<DetalhesFuncionario> listarDetalhesFuncionariosMentor(Long usuarioId);

	Optional<DetalhesFuncionario> obterDetalhesFuncionario(Long funcionarioId);

	Optional<DetalhesFuncionario> obterDetalhesFuncionarioPorUsuarioId(Long usuarioId);

	List<Funcionario> obterFuncionariosParaAvaliavao(Integer intervaloMeses);

	Optional<Funcionario> obterFuncionarioPorUsuarioID(Long usuarioId);

}
