package com.controlegastos.controlegastosapi.repository;

import com.controlegastos.controlegastosapi.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
     List<Conta> findAll();
     List<Conta> findByDataEmissaoContains(LocalDateTime dataVencimento);
     List<Conta> findByNome(String nome);
}
