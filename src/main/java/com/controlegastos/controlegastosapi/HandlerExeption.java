package com.controlegastos.controlegastosapi;

public class HandlerExeption extends RuntimeException{
    public HandlerExeption(String mensagemErro) {
        super(mensagemErro);
    }
}
