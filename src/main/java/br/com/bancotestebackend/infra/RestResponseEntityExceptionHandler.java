package br.com.bancotestebackend.infra;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.bancotestebackend.exception.NegocioException;
import br.com.bancotestebackend.exception.SessaoExpiradaException;
import br.com.bancotestebackend.exception.UsuarioNaoAutenticadoException;
import br.com.bancotestebackend.exception.UsuarioNaoAutorizadoException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(value = { Throwable.class })
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request)
    {
        ServiceResponse<Throwable> error    = new ServiceResponse<Throwable>(ex);
        String                     mensagem = ex.getMessage();
        if (mensagem == null)
        {
            mensagem = ex.getClass().getSimpleName();
        }
        error.getMessages().add(mensagem);
        HttpStatus statusHttp = getStatusHttp(ex);
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), statusHttp, request);
    }

    private HttpStatus getStatusHttp(Throwable ex)
    {
        if (ex instanceof UsuarioNaoAutenticadoException || ex instanceof SessaoExpiradaException)
        {
            return HttpStatus.UNAUTHORIZED;
        }
        else if (ex instanceof UsuarioNaoAutorizadoException)
        {
            return HttpStatus.FORBIDDEN;
        }
        else if (ex instanceof NegocioException)
        {
            return HttpStatus.BAD_REQUEST;
        }
        else
        {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}