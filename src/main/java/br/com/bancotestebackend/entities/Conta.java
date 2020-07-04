package br.com.bancotestebackend.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conta
{
    private Long       codigoAgencia;
    private Long       numeroConta;
    private String     fotoCartao;
    private String     senha;
    private BigDecimal saldo;
}
