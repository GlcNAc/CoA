package com.glcnac.ro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ro/monster")
public class Monster {
	
	@RequestMapping({"/", ""})
    public String index(HttpServletResponse response, HttpServletRequest request) {
       
        request.setAttribute("unAudit", "");
        
        return "/monster";
        
    }
	
}
