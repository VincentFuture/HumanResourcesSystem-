package com.bbo.hrsys.controller.dict;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbo.hrsys.po.Dict;
import com.bbo.hrsys.service.DictService;

/**
 * Servlet implementation class DictAddController
 */
@WebServlet("/addDict")
public class DictAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DictAddController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		Dict dict = new Dict();
		//封装数据
		String title = request.getParameter("dictTitle");
		String typeid = request.getParameter("dictTypeId");
		if(title!=null&& !"".equals(title))
			dict.setTitle(title);
		if(typeid!=null && !"".equals(typeid))
			dict.setType_id(Integer.parseInt(typeid));
		
		DictService service = new DictService();
		String rs = service.add(dict);
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
		
	}

}
