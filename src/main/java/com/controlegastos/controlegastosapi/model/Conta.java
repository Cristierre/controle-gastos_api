package com.controlegastos.controlegastosapi.model;

import lombok.*;
import org.aspectj.lang.annotation.RequiredTypes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nome;
    @NonNull
    private LocalDateTime dataEmissao;

    private LocalDateTime dataVencimento;

    @Deprecated
    public Conta(){

    }

    public Conta(Long id, String nome, LocalDateTime dataEmissao, LocalDateTime dataVencimento) {
        this.id = id;
        this.nome = nome;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
    }
}
