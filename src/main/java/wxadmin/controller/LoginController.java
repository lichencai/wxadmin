package wxadmin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/login")
public class LoginController {
	
	@RequestMapping(value = {"/into"}, method = {RequestMethod.POST,RequestMethod.GET})
	public void into(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.getWriter().write("success");
	}
	
	
}
