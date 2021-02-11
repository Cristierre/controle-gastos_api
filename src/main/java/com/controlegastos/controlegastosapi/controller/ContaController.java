package com.controlegastos.controlegastosapi.controller;

import com.controlegastos.controlegastosapi.HandlerExeption;
import com.controlegastos.controlegastosapi.controller.dto.ContaDto;
import com.controlegastos.controlegastosapi.model.Conta;
import com.controlegastos.controlegastosapi.repository.ContaRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public List<ContaDto> buscaContas() {
        try {
             List<Conta> contas = contaRepository.findAll();

            List<ContaDto> contasDtos = new ArrayList<>();

            contas.forEach(conta -> contasDtos.add(new ContaDto(conta)));
            return contasDtos;
        } catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @RequestMapping
    public ContaDto novaConta(@RequestBody Conta conta) {
        try {
            contaRepository.save(conta);
            return new ContaDto(conta);
        } catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public List<ContaDto> buscaContaPorDataDeEmissao(@RequestParam(value = "dataEmissao") LocalDateTime dataEmissao) {
        try {
            List<Conta> contas = null;
            if (dataEmissao == null) {
               contas = contaRepository.findAll();
            }
            contas = contaRepository.findByDataEmissaoContains(dataEmissao);

            List<ContaDto> contasDtos = new ArrayList<>();

            contas.forEach(conta -> contasDtos.add(new ContaDto(conta)));
            return contasDtos;

        } catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public List<ContaDto> buscaContaPorNome(@RequestParam(name = "nome") String nome){
        try{
            if(nome == null){
                contaRepository.findAll();
            }
            List<Conta> contas = contaRepository.findByNome(nome);
            List<ContaDto> contasDtos = new ArrayList<>();

            contas.forEach(conta -> contasDtos.add(new ContaDto(conta)));

            return contasDtos;
        }catch (HandlerExeption exe) {
            throw new HandlerExeption("Erro de conexão com o banco, tente mais tarde!");
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public void deletaPorIdConta(@RequestParam(name = "id") Long id){
        try {
            contaRepository.deleteById(id);
        }catch (HandlerExeption exe){
            throw new HandlerExeption("Erro ao deletar conta!");
        }
    }
}
