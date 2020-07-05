package br.com.bancotestebackend.entities;

import java.util.GregorianCalendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario
{
    private String            usuario;
    private String            senha;
    private String[]          acessos;
    private GregorianCalendar TimestampLogin;

    public boolean temAcesso(String acesso)
    {
        if (acessos != null)
        {
            for (String a : acessos)
            {
                if (a != null && a.equals(acesso))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
