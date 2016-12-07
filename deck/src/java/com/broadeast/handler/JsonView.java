package com.broadeast.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.broadeast.util.HttpServletHelper;


/**
 * @ToDoWhat 
 * @author xmm
 */
public class JsonView {
	
	public static ModelAndView Render(String jsonString, HttpServletResponse response)  
    {  
        try {  
        	HttpServletHelper.WriteHtml(response, jsonString); 
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }catch (Exception e) {
	        e.printStackTrace();
        }  
  
        return null;  
    }  
}
