package com.come2niks.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.come2niks.dao.DataTableDAO;

@Service("idtService")
public class DTServiceImpl implements IDTService{

	@Autowired
	private DataTableDAO dataTableDao;

	@Override
	public String getDataTableResponse(HttpServletRequest request) {

		String[] cols = { "id", "name", "email", "city", "country" };
		String table = "students";

		/* Response will be a String of JSONObject type */
		JSONObject result = new JSONObject();

		/* JSON Array to store each row of the data table */
		JSONArray array = new JSONArray();

		int amount = 5; /* Amount in Show Entry drop down */
		int start = 0; /* Counter for Paging, initially 0 */
		int echo = 0; /* Maintains the request number, initially 0 */
		int col = 0; /* Column number in the datatable */

		String id = "";
		String name = "";
		String email = "";
		String city = "";
		String country = "";

		/* Below variables store the options to create the Query */
		String dir = "asc";
		String sStart = request.getParameter("iDisplayStart");
		String sAmount = request.getParameter("iDisplayLength");
		String sEcho = request.getParameter("sEcho");
		String sCol = request.getParameter("iSortCol_0");
		String sdir = request.getParameter("sSortDir_0");

		/* Below will be used when Search per column is used. In this example we are using common search. */
		id = request.getParameter("sSearch_0");
		name = request.getParameter("sSearch_1");
		email = request.getParameter("sSearch_2");
		city = request.getParameter("sSearch_3");
		country = request.getParameter("sSearch_4");

		List<String> sArray = new ArrayList<String>();
		if (!id.equals("")) {
			String sId = " id like '%" + id + "%'";
			sArray.add(sId);
		}
		if (!name.isEmpty()) {
			String sName = " name like '%" + name + "%'";
			sArray.add(sName);
		}
		if (!email.isEmpty()) {
			String sEmail = " email like '%" + email + "%'";
			sArray.add(sEmail);
		}
		if (!city.isEmpty()) {
			String sCity = " city like '%" + city + "%'";
			sArray.add(sCity);
		}
		if (!country.isEmpty()) {
			String sCountry = " country like '%" + country + "%'";
			sArray.add(sCountry);
		}

		String individualSearch = "";
		if(sArray.size()==1)
		{
			individualSearch = sArray.get(0);
		}
		else if(sArray.size()>1)
		{
			for(int i=0;i<sArray.size()-1;i++)
			{
				individualSearch += sArray.get(i)+ " and ";
			}
			individualSearch += sArray.get(sArray.size()-1);
		}

		/* Start value from which the records need to be fetched */
		if (sStart != null) {
			start = Integer.parseInt(sStart);
			if (start < 0)
				start = 0;
		}

		/* Total number of records to be fetched */
		if (sAmount != null) {
			amount = Integer.parseInt(sAmount);
			if (amount < 5 || amount > 100)
				amount = 5;
		}

		/* Counter of the request sent from Data table */
		if (sEcho != null) {
			echo = Integer.parseInt(sEcho);
		}

		/* Column number */
		if (sCol != null) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 5)
				col = 0;
		}

		/* Sorting order */
		if (sdir != null) {
			if (!sdir.equals("asc"))
				dir = "desc";
		}
		String colName = cols[col];

		/* This is show the total count of records in Data base table */
		int total = 0;
		Connection conn = dataTableDao.getConnection();
		try 
		{
			String sql = "SELECT count(*) FROM "+ table;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				total = rs.getInt("count(*)");
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		/* This is total number of records that is available for the specific search query */
		int totalAfterFilter = total;

		try {
			String searchSQL = "";
			String sql = "SELECT * FROM "+table;
			String searchTerm = request.getParameter("sSearch");
			String globeSearch =  " where (id like '%"+searchTerm+"%'"
					+ " or name like '%"+searchTerm+"%'"
					+ " or email like '%"+searchTerm+"%'"
					+ " or city like '%"+searchTerm+"%'"
					+ " or country like '%"+searchTerm+"%')";
			if(searchTerm!=""&&individualSearch!=""){
				searchSQL = globeSearch + " and " + individualSearch;
			}
			else if(individualSearch!=""){
				searchSQL = " where " + individualSearch;
			}else if(searchTerm!=""){
				searchSQL=globeSearch;
			}
			sql += searchSQL;
			sql += " order by " + colName + " " + dir;
			sql += " limit " + start + ", " + amount;

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				JSONArray ja = new JSONArray();
				ja.put(rs.getString("id"));
				ja.put(rs.getString("name"));
				ja.put(rs.getString("email"));
				ja.put(rs.getString("city"));
				ja.put(rs.getString("country"));
				array.put(ja);
			}
			String sql2 = "SELECT count(*) FROM "+table;
			if (searchTerm != "") {
				sql2 += searchSQL;
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					totalAfterFilter = rs2.getInt("count(*)");
				}
			}
			result.put("iTotalRecords", total);
			result.put("iTotalDisplayRecords", totalAfterFilter);
			result.put("aaData", array);
			result.put("sEcho", echo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

}
