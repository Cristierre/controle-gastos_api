package com.controlegastos.controlegastosapi;

import com.controlegastos.controlegastosapi.controller.ContaController;
import com.controlegastos.controlegastosapi.controller.dto.ContaDto;
import com.controlegastos.controlegastosapi.model.Conta;
import com.controlegastos.controlegastosapi.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContaControllerTest {

    @Mock
    private ContaRepository contaRepositoryMocked;

    @InjectMocks
    private ContaController contaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.contaController = new ContaController(contaRepositoryMocked);
    }

    //TODO resolver validção de insteancias de listas diferentes com JUNIT
    @Test
    public void deveRetornarUmaListDeContas() {
        List<Conta> contasTeste = criaContaParaTeste();
        List<ContaDto> contasDtosTeste = new ArrayList<>();

        contasTeste.forEach(conta -> contasDtosTeste.add(new ContaDto(conta)));

        when(contaRepositoryMocked.findAll()).thenReturn(contasTeste);

        List<ContaDto> contasDto = contaController.buscaContas();

        verify(contaRepositoryMocked).findAll();
        assertEquals(contasDtosTeste, contasDto);
    }

    @Test
    public void deveInserirContaNoBanco() {
        Conta conta = new Conta(1L, "CEEE", LocalDateTime.now(), LocalDateTime.now().minusDays(2));
        ContaDto contaDto = new ContaDto(conta);
        when(contaRepositoryMocked.save(conta)).thenReturn(conta);

        ContaDto contaPersistida = contaController.novaConta(conta);

        verify(contaRepositoryMocked).save(conta);

        assertEquals(contaDto.getNome(), contaPersistida.getNome());
        assertEquals(contaDto.getDataEmissao(), contaPersistida.getDataEmissao());
        assertEquals(contaDto.getDataVencimento(), contaPersistida.getDataVencimento());
    }

    @Test
    public void deveLancarUmaExceptionCasoBancoFora() {
        Conta conta = new Conta(1L, "CEEE", LocalDateTime.now(), LocalDateTime.now().minusDays(2));
        when(contaRepositoryMocked.save(conta)).thenThrow(HandlerExeption.class);

        try {
            contaController.novaConta(conta);
            verify(contaRepositoryMocked).save(conta);
        } catch (HandlerExeption exception) {
        }
    }

    @Test
    public void deveRetornarListaDeContasConformeData(){
        List<Conta> contasTeste = criaContaParaTeste();
        contasTeste.remove(0);

        List<ContaDto> contasDtosTeste = new ArrayList<>();

        contasTeste.forEach(conta -> contasDtosTeste.add(new ContaDto(conta)));

        LocalDateTime data = LocalDateTime.now();

        when(contaRepositoryMocked.findByDataEmissaoContains(data)).thenReturn(contasTeste);

        List<ContaDto> contasResponse = contaController.buscaContaPorDataDeEmissao(data);

        verify(contaRepositoryMocked).findByDataEmissaoContains(data);
        assertEquals(contasResponse, contasDtosTeste);
    }

    @Test
    public void deveRetornarTodasAsContasQuandoDataNula(){
        List<Conta> contasTeste = criaContaParaTeste();

        List<ContaDto> contasDtosTeste = new ArrayList<>();

        contasTeste.forEach(conta -> contasDtosTeste.add(new ContaDto(conta)));
        when(contaRepositoryMocked.findByDataEmissaoContains(null)).thenReturn(contasTeste);

        List<ContaDto> contasResponse = contaController.buscaContaPorDataDeEmissao(null);

        verify(contaRepositoryMocked).findAll();

        assertEquals(contasResponse, contasDtosTeste);
    }

    @Test
    public void deveRetornarListaDeContasConformeNome(){
        List<Conta> contasTeste = criaContaParaTeste();

        String nome = "CEEE";
        List<Conta> contasContainsNome = contasTeste.stream().filter(conta -> conta.getNome().equals(nome)).collect(Collectors.toList());

        when(contaRepositoryMocked.findByNome(nome)).thenReturn(contasContainsNome);

        List<ContaDto> contasResponse = contaController.buscaContaPorNome(nome);

        verify(contaRepositoryMocked).findByNome(nome);
        assertEquals(contasResponse, contasContainsNome);
    }

    @Test
    public void deveRetornarListaDeContasCasoNomeNulo(){
        List<Conta> contasTeste = criaContaParaTeste();
        String nome = null;

        List<ContaDto> contasDtosTeste = new ArrayList<>();

        contasTeste.forEach(conta -> contasDtosTeste.add(new ContaDto(conta)));
        when(contaRepositoryMocked.findByNome(nome)).thenReturn(contasTeste);

        List<ContaDto> contasResponse = contaController.buscaContaPorNome(nome);

        verify(contaRepositoryMocked).findByNome(nome);
        assertEquals(contasResponse, contasDtosTeste);
    }

    @Test
    public void deveDeletarUmElementoDoBancoDeDados(){
        List<Conta> contasTeste = criaContaParaTeste();
        contasTeste.remove(0);
        contaController.deletaPorIdConta(1L);
        verify(contaRepositoryMocked).deleteById(1L);
    }
    public List<Conta> criaContaParaTeste() {
        List<Conta> contas = new ArrayList<>();

        Conta conta1 = new Conta(1L, "Condomínio", LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(6));
        Conta conta2 = new Conta(2L, "CEEE", LocalDateTime.now(), LocalDateTime.now().minusDays(2));
        Conta conta3 = new Conta(3L, "OI", LocalDateTime.now(), LocalDateTime.now().minusDays(2));

        contas.add(conta1);
        contas.add(conta2);
        contas.add(conta3);

        return contas;

    }
}
