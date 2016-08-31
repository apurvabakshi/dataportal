package com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projectoperations.GetUserProjects;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;

/**
 * A Java servlet that handles report generation from client.
 * 
 */
public class ReportServlet extends HttpServlet
{

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		try
		{
		CassandraConnection connect = new CassandraConnection();
		Connection connection = connect.getConnection();

		String quarter = request.getParameter("quarter") + "_" + request.getParameter("year");
		String projectname = request.getParameter("projectName");
		String id=GetUserProjects.getProjectId(projectname);
		JasperReportBuilder report = DynamicReports.report();
			Statement stmt = connection.createStatement();

			String query = "select updates from " + quarter + " where project_id='"
					+ id + "' ALLOW FILTERING;";
			ResultSet result = stmt.executeQuery(query);
			String str = result.getString("updates");
			String[] values = str.split(",");
			int i = 0;
			query = "create table temp(project_update_value text primary key);";
			stmt.execute(query);
			while (i != values.length)
			{
				values[i] = values[i].replace('[', ' ');
				query = "insert into temp(project_update_value) values ('" + values[i] + "');";
				stmt.executeUpdate(query);
				i++;

			}
			query = "select * from temp;";
			result = stmt.executeQuery(query);

			report.columns(
					Columns.column(
							"Project Updates for " + request.getParameter("projectName") + " "
									+ request.getParameter("quarter") + " "
									+ request.getParameter("year") + " are",
							"project_update_value", DataTypes.stringType()))
					.title(Components.text("Project Updates").setHorizontalAlignment(
							HorizontalAlignment.CENTER)).pageFooter(Components.pageXofY())
					.setDataSource(result).setHighlightDetailEvenRows(true);
			query = "drop columnfamily temp;";
			stmt.executeUpdate(query);
	
			String path = getServletContext().getRealPath("") + File.separator + "Reports";
			File Dir = new File(path);
			if (!Dir.exists())
			{
				Dir.mkdir();
			} 
			path = path + File.separator + request.getParameter("projectName");
			Dir = new File(path);
			if (!Dir.exists())
			{
				Dir.mkdir();
			}
			switch(request.getParameter("format")){
			case "pdf":report.toPdf(new FileOutputStream(path + "\\" + quarter + ".pdf"));
						break;
			
			case "csv":report.toCsv(new FileOutputStream(path + "\\" + quarter + ".csv"));
						break;
			
			case "xlt":report.toExcelApiXls(new FileOutputStream(path + "\\" + quarter + ".xlt"));
						break;
			}
			System.out.println("Successful :" + path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}

}
