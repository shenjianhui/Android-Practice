package esd.scos.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class FoodUpdateService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodUpdateService() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String isUpdate = request.getParameter("isUpdate");
		if(isUpdate.equals("yes"))
		{
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("name", "东北家拌凉菜");
//			map.put("price",15);
//			map.put("style","冷菜");
//			map.put("inventory",10);
//			JSONObject obj = JSONObject.fromObject(map);
//			response.getWriter().print(obj);
//			System.out.println(obj);
					
			StringBuilder builder = new StringBuilder();  
	        builder.append("<dish>");  
	        builder.append("<name>").append("东北家拌凉菜").append("</name>");  
	        builder.append("<price>").append(15).append("</price>");
	        builder.append("<style>").append("冷菜").append("</style>");  
	        builder.append("<inventory>").append(10).append("</inventory>");
	        builder.append("</dish>"); 
			response.getWriter().print(builder.toString());
			System.out.println(builder.toString());
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}

}
