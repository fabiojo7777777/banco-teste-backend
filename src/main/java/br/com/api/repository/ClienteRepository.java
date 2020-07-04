package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.resource.model.Cliente;

//@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>
{
	public abstract Cliente findByNomeAndCodigoAllIgnoringCase(String name, Integer codigo);
}
