package br.com.api.resource.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Cliente implements Serializable
{
	private static final long	serialVersionUID	= 2102630229613708719L;
	@Id
	@GeneratedValue
	private int					codigo;

	@Column
	private String				nome;

	// public Cliente()
	// {
	// super();
	// }

	// public Cliente(int codigo, String nome)
	// {
	// super();
	// this.codigo = codigo;
	// this.nome = nome;
	// }
	//
	// public int getCodigo()
	// {
	// return codigo;
	// }
	//
	// public void setCodigo(int codigo)
	// {
	// this.codigo = codigo;
	// }
	//
	// public String getNome()
	// {
	// return nome;
	// }
	//
	// public void setNome(String nome)
	// {
	// this.nome = nome;
	// }
	//
	// @Override
	// public String toString()
	// {
	// return "Cliente [codigo=" + codigo + ", nome=" + nome + "]";
	// }

}
