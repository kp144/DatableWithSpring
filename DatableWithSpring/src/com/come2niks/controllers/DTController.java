package com.come2niks.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.come2niks.service.DTServiceImpl;


@Controller
public class DTController {
	
	@Autowired
	private DTServiceImpl idtService;
	
	/**
	 * @author come2niks
	 * This method will be called when user clicks on link showed on welcome.jsp page
	 * The request is mapped using @RequestMapping annotation. 
	 * @return
	 */
	@RequestMapping(value = "/loadDatatable")
	public ModelAndView loadDatatable()
	{
		/* This will load the myDatatable.jsp page */
		return new ModelAndView("myDatatable");
		
	}
	
	/**
	 * This method will be called from AJAX callback of Data Tables 
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadServerSideData")
	public String loadServerSideData(HttpServletResponse response, HttpServletRequest request)
	{
		/* getting the JSON response to load in data table*/
		String jsonResponse = idtService.getDataTableResponse(request);
		
		/* Setting the response type as JSON */
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");

		return jsonResponse;
	}

}
