package br.com.bancotestebackend.infra;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceResponse<T>
{
    private int          status;
    private List<String> messages = new ArrayList<String>();
    private T            data;

    public ServiceResponse(T data)
    {
        super();
        this.data = data;
    }
}
