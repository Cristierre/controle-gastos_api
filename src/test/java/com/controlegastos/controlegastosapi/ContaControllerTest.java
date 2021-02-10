package com.controlegastos.controlegastosapi;

import com.controlegastos.controlegastosapi.controller.ContaController;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    public void deveRetornarUmaListDeContas() {
        List<Conta> contasTeste = criaContaParaTeste();
        when(contaRepositoryMocked.findAll()).thenReturn(contasTeste);

        List<Conta> contas = contaController.buscaContas();

        verify(contaRepositoryMocked).findAll();
        assertEquals(contasTeste, contas);
    }

    @Test
    public void deveInserirContaNoBanco() {
        Conta conta = new Conta(1L, "CEEE", LocalDateTime.now(), LocalDateTime.now().minusDays(2));
        when(contaRepositoryMocked.save(conta)).thenReturn(conta);

        Conta contaPersistida = contaController.novaConta(conta);

        verify(contaRepositoryMocked).save(conta);

        assertEquals(conta, contaPersistida);
    }

    @Test
    public void deveLancarUmaException() {
        Conta conta = new Conta(1L, null, LocalDateTime.now(), LocalDateTime.now().minusDays(2));
        when(contaRepositoryMocked.save(conta)).thenThrow(HandlerExeption.class);

        try {
            System.out.println(contaController.novaConta(conta).toString());
            verify(contaRepositoryMocked).save(conta);
        } catch (HandlerExeption exception) {
        }
    }

    @Test
    public void deveRetornarListaDeContasConformeData(){
        List<Conta> contasTeste = criaContaParaTeste();
        contasTeste.remove(0);

        LocalDateTime data = LocalDateTime.now();

        when(contaRepositoryMocked.findByDataEmissaoContains(data)).thenReturn(contasTeste);

        List<Conta> contasResponse = contaController.buscaContaPorDataDeEmissao(data);

        verify(contaRepositoryMocked).findByDataEmissaoContains(data);
        assertEquals(contasResponse, contasTeste);
    }

    @Test
    public void deveRetornarTodasAsContasQuandoDataNula(){
        List<Conta> contasTeste = criaContaParaTeste();

        when(contaRepositoryMocked.findByDataEmissaoContains(null)).thenReturn(contasTeste);

        List<Conta> contasResponse = contaController.buscaContaPorDataDeEmissao(null);

        verify(contaRepositoryMocked).findAll();

        assertEquals(contasResponse, contasTeste);
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
