package com.projectoperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.utils.CassandraConnection;

@Path("/userprojects")
public class GetUserProjects
{
	
	/*@Path("/getprojectid/{name}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getprojectid(@PathParam("name") String name) {
		try
		{
		//	if (m.isValid(id))
			{
				CassandraConnection connect = new CassandraConnection();
				Connection con = connect.getConnection();

				Statement stmt = con.createStatement();
				String query = "select id from projects where name='" + name + "' ALLOW FILTERING;";
				ResultSet result = stmt.executeQuery(query);
	                     while (result.next()) {
	                    	  String projectId=result.getString("id");
	                    	 String id= String.valueOf(projectId.charAt(0));
	                    	 return id;
	                    	  }
			}// valid if
		//	else
				//return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}*/

	@Path("/test")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello world!";
	}

	
/**
 * getting list of projects
 * @param id specifies session ID
 * @return returns projects list
 */
	@Path("/getprojects/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjects(@PathParam("id") String id)
	{
		List<Project> project = new ArrayList<Project>();
		
	//	SessionMap m = new SessionMap();
		try
		{
		//	if (m.isValid(id))
			{
				CassandraConnection connect = new CassandraConnection();
				Connection con = connect.getConnection();

				Statement stmt = con.createStatement();
				String query = "select project_id from employees where id='" + id + "';";
				ResultSet result = stmt.executeQuery(query);
	                     while (result.next()) {
	                    	  String[] projects=result.getString("project_id").split(",");
	                    	  int noOfProj=projects.length;
	                    	  while(noOfProj>0){
	                    		query = "select name from projects where id=" + projects[noOfProj-1] + ";";
	                    		ResultSet result1 = stmt.executeQuery(query);
	              				Project p=new Project();
	              				p.setId(Integer.parseInt(projects[noOfProj-1]));
	              				p.setName(result1.getString("name"));
	              				project.add(p);
	              				//assign project object id and name and return json
	              				noOfProj--;
	                    	  }
	                       }   
					 return project;
			}// valid if
		//	else
				//return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}// GET

	
	@Path("/getskillsets/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Skillset> getskillsets(@PathParam("name") String name) {
		List<Skillset> skillsets = new ArrayList<Skillset>();
		try
		{
		//	if (m.isValid(id))
			{
				String id=getProjectId(name);
				CassandraConnection connect = new CassandraConnection();
				Connection con = connect.getConnection();

				Statement stmt = con.createStatement();
				String query = "select databases,development_env,languages,os from project_skillsets where id='" + id + "' ALLOW FILTERING;";
				ResultSet result = stmt.executeQuery(query);
				String[] skillsetNames={"databases","development_env","languages","os"};
				   for (int i=0;i<4;i++) {
					   Skillset s=new Skillset();
					   s.setName(skillsetNames[i]);
					   s.setValue(result.getString(skillsetNames[i]));
					   skillsets.add(s);
                 	  }
                 	  return skillsets;
			}
				}catch(Exception e){System.out.println(e);}
				return null;
			}// valid if
		//	else
				//return null;

	@Path("/gettimelines/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Timeline> getTimelines(@PathParam("name") String name)
	{
		List<Timeline> timelines = new ArrayList<Timeline>();
	//	SessionMap m = new SessionMap();
		try
		{
		//	if (m.isValid(id))
			{
				String id=getProjectId(name);
				CassandraConnection connect = new CassandraConnection();
				Connection con = connect.getConnection();

				Statement stmt = con.createStatement();
				String query = "select features,release_version,release_date from timelines where project_id=" + id + "  ALLOW FILTERING;";
				ResultSet result = stmt.executeQuery(query);
				
	                     while (result.next()) {
	                    	 Timeline t=new Timeline();
	                    	 t.setDate(result.getString("release_date"));
	                    	 t.setRelease(result.getString("release_version"));
	                    	 t.setFeatures(result.getString("features"));
	                    	 timelines.add(t);
	                     }
					 return timelines;
			}// valid if
		//	else
				//return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}// GET

	
	@Path("/getdocs/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Document> getDocuments(@PathParam("name") String name,@PathParam("month") String month,@PathParam("year") String year)
	{
		List<Document> documents = new ArrayList<Document>();
	//	SessionMap m = new SessionMap();
		try
		{
		//	if (m.isValid(id))
			{
				String id=getProjectId(name);
				CassandraConnection connect = new CassandraConnection();
				Connection con = connect.getConnection();
//+ " AND month='"+month+"' AND year='"+year+
				Statement stmt = con.createStatement();
				String query = "select documents from project_documents where project_id=" + id +" 	ALLOW FILTERING;";
				ResultSet result = stmt.executeQuery(query);
				while(result.next()){
	                    	 Document d=new Document();
	                    	 String[] docs=result.getString("documents").split(",");
	                         for(int i=0;i<docs.length;i++){ 	
	                    	 d.setName(docs[i]);
	                         }
		                    	documents.add(d);
				}
					 return documents;
			}// valid if
		//	else
				//return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Path("/getupdates/{name}/{month}/{year}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectUpdate> getskillsets(@PathParam("name") String name,@PathParam("month") String month,@PathParam("year") String year) {
		List<ProjectUpdate> updates = new ArrayList<ProjectUpdate>();
		try
		{
		//	if (m.isValid(id))
			{
				String id=getProjectId(name);
				CassandraConnection connect = new CassandraConnection();
				Connection con = connect.getConnection();

				Statement stmt = con.createStatement();
				String tablename=month+"_"+year;
				String query = "select updates from "+tablename+" where project_id='" + id + "' ALLOW FILTERING;";
				ResultSet result = stmt.executeQuery(query);
				while(result.next()){
				
               	 String[] allupdates=result.getString("updates").split(",");
                    for(int i=0;i<allupdates.length;i++){ 	
                    	ProjectUpdate p=new ProjectUpdate();
                    	p.setUpdate(allupdates[i]);
                    	  updates.add(p);
                    	}
                  
				}
                 	  return updates;
			}
				}catch(Exception e){System.out.println(e);}
				return null;
			}// valid if
	
	
	public static String getProjectId(String name){
		try{
		CassandraConnection connect = new CassandraConnection();
		Connection con = connect.getConnection();

		Statement stmt = con.createStatement();
		String query = "select id from projects where name='" + name + "' ALLOW FILTERING;";
		ResultSet result = stmt.executeQuery(query);
        String projectId=result.getString("id");
        String id= String.valueOf(projectId.charAt(0));
        return id;
		}catch(Exception e){System.out.println(e);}
		return null;
	}
}

