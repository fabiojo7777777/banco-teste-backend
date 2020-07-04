//package br.com.api.repository.entity;
//
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Entity
//// @NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@ToString
//public class ApplicationUser implements UserDetails
//{
//	private static final long	serialVersionUID		= 7860076599813338991L;
//
//	@Id
//	private String				username;
//
//	@Setter
//	private String				password;
//
//	private boolean				accountNonExpired		= true;
//	private boolean				accountNonLocked		= true;
//	private boolean				credentialsNonExpired	= true;
//	private boolean				enabled					= true;
//
//	@Setter
//	private long				id;
//
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@Setter
//	private Set<Authority>		authorities;
//
//	public ApplicationUser()
//	{
//		super();
//		System.err.println("Instanciando " + this.getClass().getSimpleName());
//	}
//
//	public ApplicationUser(String username, String password)
//	{
//		this();
//		this.username = username;
//		this.password = password;
//	}
//
//	public String getStringAutorities()
//	{
//		if (getAuthorities() != null)
//		{
//			StringBuffer sb = new StringBuffer();
//			for (Authority authority : getAuthorities())
//			{
//				sb.append(authority.getAuthority());
//				sb.append(';');
//			}
//			if (sb.length() > 0)
//			{
//				sb.deleteCharAt(sb.length() - 1);
//			}
//			return sb.toString();
//		}
//		return null;
//	}
//
//}