package com.login;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.projectoperations.Project;

import com.utils.CassandraConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



@Path("/user")
public class AuthenticateUser {

	Connection con=null;
	CassandraConnection connection=new CassandraConnection();
	
	@Path("/authenticate/{username}/{password}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User authenticate(@PathParam("username") String uname, @PathParam("password") String pwd) {
		if(uname.equalsIgnoreCase("gs-077") && pwd.equalsIgnoreCase("pass")){
			User user=new User();
			user.setId(uname);
			user.setSessionId("123");
			user.setUsername(getName(uname));
			return user;
		}
		return null;
	}
	
	public String getName(String id){
		try{
		con=connection.getConnection();
		Statement stmt = con.createStatement();
		String query = "select name from employees where id='"+id+"';";
		ResultSet result = stmt.executeQuery(query);
		String username=result.getString("name");
		return username;
		}catch(Exception e){
			
		}
		return null;
	}
	
	@Path("/test")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String sayPlainTextHello() {
			return "Hello world!";
		}
	
	@Path("/newtest")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Project[] sayPlainTextHello1() {
		Project[] p=new Project[2];
		Project p0 = new Project();
		p0.setId(1);
		p0.setName("rsa");
		p[0] = p0;
		
		Project p1 = new Project();
		p1.setId(1);
		p1.setName("rsa");
		p[1] = p1;
		return p;
	}
	
	
}