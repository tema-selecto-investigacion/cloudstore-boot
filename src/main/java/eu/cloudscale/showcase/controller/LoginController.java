/*******************************************************************************
*  Copyright (c) 2015 XLAB d.o.o.
*  All rights reserved. This program and the accompanying materials
*  are made available under the terms of the Eclipse Public License v1.0
*  which accompanies this distribution, and is available at
*  http://www.eclipse.org/legal/epl-v10.html
*  
*  @author XLAB d.o.o.
*******************************************************************************/
package eu.cloudscale.showcase.controller;

import eu.cloudscale.showcase.db.model.ICustomer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/login")
public class LoginController extends AController
{
	
	@GetMapping(value="")
	public String get(HttpServletRequest request, HttpSession session, Model model)
	{		
		String referer = request.getParameter("next");
		if( referer.isEmpty() )
			referer = request.getHeader( "referer" );
		
		if( session.getAttribute( "customer" ) != null)
			return "redirect:" + referer == null ? "/" : referer;
		
		model.addAttribute("referer", referer);
		return "login";
	}
	
	@PostMapping(value="")
	public String post(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("referer") String referer,
                       HttpSession session, Model model)
	{
		ICustomer customer = null;
		
		if( (customer = service.getUserBy(username, password)) != null )
		{
			Date currDate = new Date(); 
			
			
			Calendar c = Calendar.getInstance();
			c.setTime( currDate );
			c.add( Calendar.HOUR, -1 );
			
			customer.setCLogin( currDate );
			customer.setCExpiration( c.getTime() );
			service.saveCustomer(customer);
			session.setAttribute( "customer", customer );
			return "redirect:" + referer;
		}
		
		model.addAttribute("errors", "Login failed. Check username and password");
		return "login";
	}
}
