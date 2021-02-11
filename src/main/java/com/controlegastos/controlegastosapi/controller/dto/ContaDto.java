package com.controlegastos.controlegastosapi.controller.dto;

import com.controlegastos.controlegastosapi.model.Conta;

import java.time.LocalDateTime;

public class ContaDto {

    private String nome;
    private LocalDateTime dataEmissao;
    private LocalDateTime dataVencimento;

    public ContaDto(Conta conta){
        this.nome = conta.getNome();
        this.dataEmissao = conta.getDataEmissao();
        this.dataVencimento = conta.getDataVencimento();
    }

    public String getNome() {
        return nome;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }
}
