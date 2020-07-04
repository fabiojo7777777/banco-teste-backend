package br.com.bancotestebackend.resources;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(value = { Throwable.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request)
    {
        ServiceResponse error    = new ServiceResponse<Throwable>(ex);
        String          mensagem = ex.getMessage();
        if (mensagem == null)
        {
            mensagem = ex.getClass().getSimpleName();
        }
        error.getMessages().add(mensagem);
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}