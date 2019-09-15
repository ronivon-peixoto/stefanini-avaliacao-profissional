package com.stefanini.application.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefanini.application.domain.entity.CentroCusto;
import com.stefanini.application.domain.entity.Empresa;

@Repository
public interface CentroCustoRepository extends JpaRepository<CentroCusto, Long> {

	List<CentroCusto> findByEmpresa(Empresa empresa);

}
