package com.controlegastos.controlegastosapi.controller;

import com.controlegastos.controlegastosapi.HandlerExeption;
import com.controlegastos.controlegastosapi.model.Conta;
import com.controlegastos.controlegastosapi.repository.ContaRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RestController
@RequestMapping("/contas")
public class ContaController {

    private ContaRepository contaRepository;

    @Autowired
    public ContaController(ContaRepository contaRepository) {
        try {
            this.contaRepository = contaRepository;
        } catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @GetMapping
    @ResponseBody
    public List<Conta> buscaContas() {
        try {
            return contaRepository.findAll();
        } catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @PostMapping
    @RequestMapping
    public Conta novaConta(@RequestBody Conta conta) {
        try {
            contaRepository.save(conta);
            return conta;
        } catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @GetMapping
    @ResponseBody
    public List<Conta> buscaContaPorDataDeEmissao(@RequestParam(value = "dataEmissao") LocalDateTime dataEmissao) {
        try {
            if (dataEmissao == null) {
                contaRepository.findAll();
            }
            List<Conta> contas = contaRepository.findByDataEmissaoContains(dataEmissao);
            return contas;

        } catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @GetMapping
    @ResponseBody
    public List<Conta> buscaContaPorNome(@RequestParam(name = "nome") String nome){
        try{
            if(nome == null){
                contaRepository.findAll();
            }
            List<Conta> contas = contaRepository.findByNome(nome);
            return contas;
        }catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }
}
