package br.com.bancotestebackend.resources;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancotestebackend.entities.Conta;
import br.com.bancotestebackend.entities.LancamentoExtrato;
import br.com.bancotestebackend.entities.Transferencia;
import br.com.bancotestebackend.exception.NegocioException;
import br.com.bancotestebackend.infra.ServiceResponse;

@RestController
@RequestMapping("/conta")
public class ContaResource
{
    private static final String            ACESSO_SALDO           = "BK1";
    private static final String            ACESSO_EXTRATO         = "BK2";
    private static final String            ACESSO_TRANSFERENCIA   = "BK3";
    private static List<Conta>             listaConta             = new Vector<Conta>();
    private static List<LancamentoExtrato> listaLancamentoExtrato = new ArrayList<LancamentoExtrato>();
    private static long                    sequencial             = 0;

    static
    {
        listaConta.add(new Conta(1L, 1L, "../images/Ourocard-Elo-Mais.jpg", "111111", BigDecimal.ZERO));
        listaConta.add(new Conta(2L, 2L, "../images/Ourocard-Internacional-Visa.jpg", "222222", BigDecimal.ZERO));
        listaConta.add(new Conta(3L, 3L, "../images/Ourocard-Visa-Elo.jpg", "333333", BigDecimal.ZERO));
        listaConta.add(new Conta(4L, 4L, "../images/Ourocard-Visa-Facil.jpg", "444444", BigDecimal.ZERO));
        listaConta.add(new Conta(5L, 5L, "../images/Ourocard-Visa-Gold.jpg", "555555", BigDecimal.ZERO));
        listaConta.add(new Conta(6L, 6L, "../images/Ourocard-Visa-Platinum.jpg", "666666", BigDecimal.ZERO));
        listaConta.add(new Conta(7L, 7L, "../images/OurocardEloGrafite.png", "777777", BigDecimal.ZERO));
        listaConta.add(new Conta(8L, 8L, "../images/Ourocard-Elo-Mais.jpg", "888888", BigDecimal.ZERO));
        listaConta.add(new Conta(9L, 9L, "../images/Ourocard-Internacional-Visa.jpg", "999999", BigDecimal.ZERO));
        for (Conta c : listaConta)
        {
            Long              agencia           = c.getCodigoAgencia();
            Long              conta             = c.getNumeroConta();
            GregorianCalendar dataMovimento     = new GregorianCalendar();
            String            historico         = "Saldo Anterior";
            Long              documento         = null;
            BigDecimal        valor             = null;
            BigDecimal        saldo             = BigDecimal.ZERO;
            LancamentoExtrato lancamentoExtrato = new LancamentoExtrato(agencia, conta, dataMovimento, historico, documento, valor, saldo);
            listaLancamentoExtrato.add(lancamentoExtrato);
        }
    }

    @RequestMapping(value = "/saldo", method = RequestMethod.GET)
    public ServiceResponse<List<Conta>> listarContas(
            @RequestParam("codigoAgencia") Long pCodigoAgencia,
            @RequestParam("numeroConta") Long pNumeroConta)
            throws NegocioException
    {
        LoginResource.verificarAcesso(ACESSO_SALDO);
        return saldo(pCodigoAgencia, pNumeroConta);
    }

    @RequestMapping(value = "/contas", method = RequestMethod.GET)
    public ServiceResponse<List<Conta>> listarContas()
            throws NegocioException
    {
        LoginResource.verificarAcesso(ACESSO_SALDO);
        return saldo(null, null);
    }

    public ServiceResponse<List<Conta>> saldo(Long pCodigoAgencia, Long pNumeroConta)
    {
        for (Conta conta : listaConta)
        {
            Long codigoAgencia = conta.getCodigoAgencia();
            Long numeroConta   = conta.getNumeroConta();
            if (pCodigoAgencia != null && pNumeroConta != null)
            {
                if (!pCodigoAgencia.equals(conta.getCodigoAgencia())
                        || !pNumeroConta.equals(conta.getNumeroConta()))
                {
                    continue;
                }
            }
            BigDecimal saldo = contabilizarSaldoAposTransferencia(codigoAgencia, numeroConta, BigDecimal.ZERO);
            conta.setSaldo(saldo);
        }
        return new ServiceResponse<List<Conta>>(listaConta);
    }

    @RequestMapping(value = "/extrato", method = RequestMethod.GET)
    public ServiceResponse<List<LancamentoExtrato>> extrato(
            @RequestParam("codigoAgencia") Long codigoAgencia,
            @RequestParam("numeroConta") Long numeroConta,
            @RequestParam("dataMovimento") String txtData)
            throws ParseException,
            NegocioException
    {
        LoginResource.verificarAcesso(ACESSO_SALDO);
        LoginResource.verificarAcesso(ACESSO_EXTRATO);
        SimpleDateFormat  sdf           = new SimpleDateFormat("yyyy-MM");
        Date              data          = sdf.parse(txtData);
        GregorianCalendar dataMovimento = new GregorianCalendar();
        dataMovimento.setTime(data);
        dataMovimento.set(GregorianCalendar.DATE, 0);
        dataMovimento.set(GregorianCalendar.HOUR, 0);
        dataMovimento.set(GregorianCalendar.MINUTE, 0);
        dataMovimento.set(GregorianCalendar.SECOND, 0);
        dataMovimento.set(GregorianCalendar.MILLISECOND, 0);
        List<LancamentoExtrato> lista = new ArrayList<LancamentoExtrato>();
        for (LancamentoExtrato lancamento : listaLancamentoExtrato)
        {
            GregorianCalendar dataMovimentoGravada = lancamento.getDataMovimento();
            if (dataMovimentoGravada != null
                    && dataMovimento != null
                    && dataMovimentoGravada.get(GregorianCalendar.YEAR) == dataMovimento.get(GregorianCalendar.YEAR)
                    && dataMovimentoGravada.get(GregorianCalendar.MONTH) == dataMovimento.get(GregorianCalendar.MONTH) + 1
                    && lancamento.getAgencia().equals(codigoAgencia)
                    && lancamento.getConta().equals(numeroConta))
            {
                lista.add(lancamento);
            }
        }
        return new ServiceResponse<List<LancamentoExtrato>>(lista);
    }

    @RequestMapping(value = "/transferencia", method = RequestMethod.POST)
    public ServiceResponse<Void> extrato(@RequestBody Transferencia transferencia)
            throws ParseException,
            NegocioException
    {
        LoginResource.verificarAcesso(ACESSO_SALDO);
        LoginResource.verificarAcesso(ACESSO_EXTRATO);
        LoginResource.verificarAcesso(ACESSO_TRANSFERENCIA);
        synchronized (ContaResource.class)
        {
            Long              codigoAgenciaOrigem = transferencia.getContaOrigem().getCodigoAgencia();
            Long              numeroContaOrigem   = transferencia.getContaOrigem().getNumeroConta();
            GregorianCalendar dataMovimentoOrigem = new GregorianCalendar();
            String            historicoOrigem     = "Transferência entre contas";
            long              documentoOrigem     = sequencial + 1;
            sequencial++;
            BigDecimal        valorOrigem             = transferencia.getValor().negate();
            BigDecimal        saldoOrigem             = contabilizarSaldoAposTransferencia(codigoAgenciaOrigem, numeroContaOrigem, valorOrigem);
            LancamentoExtrato lancamentoExtratoOrigem = new LancamentoExtrato(codigoAgenciaOrigem, numeroContaOrigem, dataMovimentoOrigem, historicoOrigem, documentoOrigem, valorOrigem, saldoOrigem);
            if (senhaCorreta(codigoAgenciaOrigem, numeroContaOrigem, transferencia.getContaOrigem().getSenha()))
            {
                listaLancamentoExtrato.add(lancamentoExtratoOrigem);

                Long              codigoAgenciaDestino     = transferencia.getContaDestino().getCodigoAgencia();
                Long              numeroContaDestino       = transferencia.getContaDestino().getNumeroConta();
                GregorianCalendar dataMovimentoDestino     = dataMovimentoOrigem;
                String            historicoDestino         = "Crédito de transferência entre contas";
                long              documentoDestino         = sequencial + 1;
                BigDecimal        valorDestino             = transferencia.getValor();
                BigDecimal        saldoDestino             = contabilizarSaldoAposTransferencia(codigoAgenciaDestino, numeroContaDestino, valorDestino);
                LancamentoExtrato lancamentoExtratoDestino = new LancamentoExtrato(codigoAgenciaDestino, numeroContaDestino, dataMovimentoDestino, historicoDestino, documentoDestino, valorDestino, saldoDestino);
                listaLancamentoExtrato.add(lancamentoExtratoDestino);
            }
            else
            {
                throw new RuntimeException("Senha inválida");
            }
        }
        return new ServiceResponse<Void>(null);
    }

    private boolean senhaCorreta(Long codigoAgencia, Long numeroConta, String senha)
    {
        for (Conta conta : listaConta)
        {
            if (conta.getCodigoAgencia().equals(codigoAgencia)
                    && conta.getNumeroConta().equals(numeroConta)
                    && conta.getSenha().equals(senha))
            {
                return true;
            }
        }
        return false;
    }

    private BigDecimal contabilizarSaldoAposTransferencia(Long agencia, Long conta, BigDecimal valor)
    {
        BigDecimal saldoFinal = BigDecimal.ZERO;
        for (LancamentoExtrato lancamento : listaLancamentoExtrato)
        {
            if (lancamento.getAgencia().equals(agencia)
                    && lancamento.getConta().equals(conta))
            {
                if (lancamento.getValor() != null)
                {
                    saldoFinal = saldoFinal.add(lancamento.getValor(), MathContext.DECIMAL128);
                }
            }
        }
        saldoFinal = saldoFinal.add(valor, MathContext.DECIMAL128);
        return saldoFinal;
    }
}
