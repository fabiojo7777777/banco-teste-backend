package br.com.bancotestebackend.entities;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoExtrato
{
    private Long              agencia;
    private Long              conta;
    private GregorianCalendar dataMovimento;
    private String            historico;
    private Long              documento;
    private BigDecimal        valor;
    private BigDecimal        saldo;
}
