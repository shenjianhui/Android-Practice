package esd.scos.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

public class LoginValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String strRegex = "^[0-9A-Za-z]+$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher1 = pattern.matcher(name);
        Matcher matcher2 = pattern.matcher(password);
        boolean mt1 = matcher1.find();
        boolean mt2 = matcher2.find();
        int resultCode = -1;
        if (mt1 && mt2) {
        	//验证成功
        	resultCode = 1;
        }else{
        	//验证失败
        	if (!mt1) {
        		resultCode = 2;
        	}
        	if (!mt2) {
        		resultCode = 3;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RESULTCODE", resultCode);
        response.getWriter().print(jsonObject);
		//System.out.println("jsonObject");
	}

}
