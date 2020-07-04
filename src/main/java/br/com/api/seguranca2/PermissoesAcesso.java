package br.com.api.seguranca2;
//package br.com.api.seguranca.regras;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//public enum PermissoesAcesso
//{
//	CREATE_CLIENTE, READ_CLIENTE, UPDATE_CLIENTE, DELETE_CLIENTE;
//
//	private final GrantedAuthority						GRANTED_AUTHORITY		= new SimpleGrantedAuthority(this.name());
//	private final static Map<String, GrantedAuthority>	MAPA_PERMISSOES_ACESSO	= new ConcurrentHashMap<String, GrantedAuthority>();
//	static
//	{
//		for (PermissoesAcesso permissaoAcesso : PermissoesAcesso.values())
//		{
//			MAPA_PERMISSOES_ACESSO.put(permissaoAcesso.GRANTED_AUTHORITY.getAuthority(), permissaoAcesso.GRANTED_AUTHORITY);
//		}
//	}
//
//	PermissoesAcesso()
//	{
//		System.err.println("Instanciando " + this.getClass().getSimpleName());
//	}
//
//	public static GrantedAuthority getAuthorityByName(String name)
//	{
//		return MAPA_PERMISSOES_ACESSO.get(name);
//	}
//}
