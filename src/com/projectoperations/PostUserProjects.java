package com.projectoperations;

import java.sql.Connection;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.utils.CassandraConnection;
import com.utils.SessionMapper;

@Path("/addprojectdetails")
public class PostUserProjects {
	@Path("/adddocs/{month}/{year}/{projectName}")
	@POST
	@Consumes(
		{ MediaType.APPLICATION_JSON })
	@Produces(
		{ MediaType.TEXT_PLAIN })
	public String addDocs(@PathParam("id") String id, @PathParam("month") String month,
			@PathParam("year") String year, @PathParam("projectName") String projectName, String obj)
	{
		try
		{
			//if (m.isValid(id))
			{
		//		if (m.checkRole(id, "Manager"))
				{
					CassandraConnection connect = new CassandraConnection();
					Connection con = connect.getConnection();
					int r=SessionMapper.getRandomNumberInRange(100,999);
					String pid=GetUserProjects.getProjectId(projectName);
					
					Statement stmt = con.createStatement();
					String query = "insert into project_documents(id,documents,month,project_id,year) values ('"+r+"','"+obj+"','"+month+"',"+pid+",'"+year+"');";
					stmt.executeUpdate(query);
				//	log1.debug("User " + m.getUname(id) + " added document for " + projectName);
					return "Uploaded successfully";
				}
			//	else
		//			return "Access Denied";
			}
		//	else
		//		return "Expired";

		}
		catch (Exception e)
		{
	//		log1.error("Add docs entry for " + projectName + " failed for user " + m.getUname(id),e);
			e.printStackTrace();
		}

		return "Error while adding";
	}// timeline post

}
