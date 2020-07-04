package br.com.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.repository.ClienteRepository;
import br.com.api.resource.model.Cliente;

@RestController
@RequestMapping("/clientes")
public class ClienteResource
{
	@Autowired
	private ClienteRepository repository;

	public ClienteResource()
	{
		super();
		System.err.println("Instanciando " + this.getClass().getSimpleName());
	}

	// org.springframework.security.access.expression.method.MethodSecurityExpressionRoot
	// @PreAuthorize("hasRole('ROLE_READ_Cliente')")
	@PreAuthorize("hasAuthority('READ_Cliente')")
	@GetMapping("")
	public Page<Cliente> listar(@RequestParam(value = "pagina", required = false) Integer pagina)
	{
		// defaultRolePrefix
		System.err.println("GET /clientes");
		int paginaInicial = 0;
		if (pagina != null)
		{
			paginaInicial = pagina;
		}
		Pageable pageable = PageRequest.of(paginaInicial, 3, Sort.by(Sort.Direction.ASC, "codigo").and(Sort.by(Sort.Direction.ASC, "codigo")));
		// Pageable pageable = Page.empty().getPageable();
		return repository.findAll(pageable);
	}

	// @PreAuthorize("hasRole('ROLE_INSERT_CLIENTE')")
	@PreAuthorize("hasAuthority('INSERT_CLIENTE')")
	@PostMapping("/")
	public Cliente incluir(@RequestBody Cliente cliente)
	{
		System.err.println("POST /clientes");
		return repository.save(cliente);
	}
}