package br.com.bancotestebackend.resources;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancotestebackend.entities.Usuario;
import br.com.bancotestebackend.exception.NegocioException;
import br.com.bancotestebackend.exception.SessaoExpiradaException;
import br.com.bancotestebackend.exception.UsuarioNaoAutenticadoException;
import br.com.bancotestebackend.exception.UsuarioNaoAutorizadoException;
import br.com.bancotestebackend.infra.ServiceResponse;

@RestController
@RequestMapping("/")
public class LoginResource
{
    private static final int     TEMPO_EXPIRACAO_SESSAO_MINUTOS = 5;
    private static Usuario       usuarioLogado;
    private static List<Usuario> listaUsuario                   = new Vector<Usuario>();
    static
    {
        listaUsuario.add(new Usuario("guest", "guest", new String[] { }, null));
        listaUsuario.add(new Usuario("fulano", "fulano", new String[] { "BK1" }, null));
        listaUsuario.add(new Usuario("ciclano", "ciclano", new String[] { "BK1", "BK2" }, null));
        listaUsuario.add(new Usuario("beltrano", "beltrano", new String[] { "BK1", "BK2", "BK3" }, null));
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServiceResponse<Usuario> autenticar(@RequestBody Usuario usuario)
            throws NegocioException
    {
        for (Usuario u : listaUsuario)
        {
            if (u.getUsuario().equals(usuario.getUsuario())
                    && u.getSenha().equals(usuario.getSenha()))
            {
                usuarioLogado = u;
                usuarioLogado.setTimestampLogin(new GregorianCalendar());
                return new ServiceResponse<Usuario>(usuario);
            }
        }
        throw new UsuarioNaoAutenticadoException("Usuário ou senha inválidos");
    }

    @RequestMapping(value = "logoff", method = RequestMethod.GET)
    public ServiceResponse<Void> logoff()
    {
        usuarioLogado = null;
        return new ServiceResponse<Void>();
    }

    @RequestMapping(value = "acessos", method = RequestMethod.GET)
    public static ServiceResponse<String[]> getAcessos()
    {
        if (usuarioLogado == null || usuarioLogado.getAcessos() == null)
        {
            return new ServiceResponse<String[]>(new String[0]);
        }
        else
        {
            return new ServiceResponse<String[]>(usuarioLogado.getAcessos());
        }
    }

    public static Usuario getUsuarioLogado()
    {
        return usuarioLogado;
    }

    public static void verificarAcesso(String acesso)
            throws NegocioException
    {
        verificarSessaoExpirada();
        if (!usuarioLogado.temAcesso(acesso))
        {
            throw new UsuarioNaoAutorizadoException("Solicite acesso à transação '" + acesso + "' para acessar esta funcionalidade");
        }
    }

    public static void verificarSessaoExpirada()
            throws SessaoExpiradaException,
            UsuarioNaoAutenticadoException
    {
        if (usuarioLogado == null)
        {
            throw new UsuarioNaoAutenticadoException("Usuário não autenticado");
        }
        GregorianCalendar timestampLogin = usuarioLogado.getTimestampLogin();
        if (timestampLogin == null)
        {
            throw new SessaoExpiradaException("Sua sessão expirou, faça o login e tente novamente");
        }
        else
        {
            GregorianCalendar timestampExpiracaoLogin = (GregorianCalendar) timestampLogin.clone();
            timestampExpiracaoLogin.add(GregorianCalendar.MINUTE, TEMPO_EXPIRACAO_SESSAO_MINUTOS);
            if (new GregorianCalendar().after(timestampExpiracaoLogin))
            {
                throw new SessaoExpiradaException("Sua sessão expirou, faça o login e tente novamente (" + TEMPO_EXPIRACAO_SESSAO_MINUTOS + " MIN)");
            }
        }
    }
}
