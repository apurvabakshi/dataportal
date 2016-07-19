package com.login;

import java.util.Hashtable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;



@Path("/helloworld")
public class AuthenticateUser {

	@Path("/test")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String sayPlainTextHello() {
			return "Hello world!";
		}

	@Path("/{user}/{pass}")
	@GET
	@Produces(
		{ MediaType.TEXT_PLAIN })
	public String authenticate(@PathParam("user") String name, @PathParam("pass") String pass1)
	{
		Hashtable<String, String> authEnv = new Hashtable<String, String>(11);
		String userName = name;
		String passWord = pass1;
		String dn = "cn=" + userName + "," + Constants.LDAP_BASE;
		String id;
		String role="";
		authEnv.put(Context.INITIAL_CONTEXT_FACTORY, Constants.LDAP_FACTORY);
		authEnv.put(Context.PROVIDER_URL, Constants.LDAP_URL);
		authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		authEnv.put(Context.SECURITY_PRINCIPAL, dn);
		authEnv.put(Context.SECURITY_CREDENTIALS, passWord);

		try
		{
			DirContext dctx = new InitialDirContext(authEnv);
		
		    SearchControls sc = new SearchControls();
		    String[] attributeFilter = {"cn","sn"};
		    sc.setReturningAttributes(attributeFilter);
		    sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		 
		    String filter = "cn="+name;
		    NamingEnumeration<SearchResult> results = dctx.search(Constants.LDAP_BASE,filter, sc);
		      
		   while (results.hasMore()) {
		   
			   SearchResult sr = (SearchResult) results.next();
		      Attributes attrs = sr.getAttributes();
		      Attribute temp=attrs.get("sn");
		    	  role=(String)temp.get();
		    	  System.out.print(role + " this is  " );
		  
		      } 
			System.out.println("Authentication Success!");
			SessionMap s = new SessionMap();
			String check = s.checkIfLoggedIn(name);
			if (check.equalsIgnoreCase("false"))
			{
				id = s.createSession(name);
			}
			else
			{
				String givenId[] = check.split(",");
				id = givenId[0];
				System.out.println("already present =" + id);
			}
			return "True=" + id + "=" + role;
		}
		catch (AuthenticationException authEx)
		{

			System.out.println("Authentication failed!");
			return "False";

		}
		catch (NamingException namEx)
		{

			System.out.println("Something went wrong!");

			namEx.printStackTrace();
			return "False";
		}

	}
	
}
