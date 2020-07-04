package br.com.bancotestebackend.entities;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Transferencia
{
    private Conta      contaOrigem;
    private Conta      contaDestino;
    private BigDecimal valor;
}
