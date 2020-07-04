package br.com.bancotestebackend.resources;

import java.util.List;
import java.util.Vector;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancotestebackend.entities.Usuario;

@RestController
@RequestMapping("/login")
public class LoginResource
{
    private static List<Usuario> listaUsuario = new Vector<Usuario>();
    static
    {
        listaUsuario.add(new Usuario("fulano", "fulano"));
        listaUsuario.add(new Usuario("ciclano", "ciclano"));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ServiceResponse<Usuario> autenticar(@RequestBody Usuario usuario)
    {
        for (Usuario u : listaUsuario)
        {
            if (u.getUsuario().equals(usuario.getUsuario())
                    && u.getSenha().equals(usuario.getSenha()))
            {
                return new ServiceResponse<Usuario>(usuario);
            }
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }
}
