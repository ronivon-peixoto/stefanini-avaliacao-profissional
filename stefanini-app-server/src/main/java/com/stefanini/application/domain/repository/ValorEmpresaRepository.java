package com.stefanini.application.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefanini.application.domain.entity.Empresa;
import com.stefanini.application.domain.entity.ValorEmpresa;

@Repository
public interface ValorEmpresaRepository extends JpaRepository<ValorEmpresa, Long> {

	List<ValorEmpresa> findAllByEmpresa(Empresa empresa);

}
