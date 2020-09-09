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
 * Servlet implementation class DictSearchController
 */
@WebServlet("/searchDict")
public class DictSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DictSearchController() {
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
		String typeId = request.getParameter("dictTypeId");
		String id = request.getParameter("dictId");
		
		if(title!=null&& !"".equals(title))
			dict.setTitle(title);
		
//		if(typeId!=null && !"".equals(typeId)) {
//			switch (typeId) {
//			case "性别":
//			case "1":
//				dict.setType_id(1);//1性别
//				break;
//			case "城市":
//			case "2":
//				dict.setType_id(2);//2城市
//			default:
//				break;
//			}
//		}
		if(typeId!=null && !"".equals(typeId)) {
			dict.setType_id(Integer.parseInt(typeId));
		}
		if(id!=null && !"".equals(id))
			dict.setDct_id(Integer.parseInt(id));
		
		DictService service = new DictService();
		String rs = service.query(dict);
		PrintWriter out = response.getWriter();
		out.println(rs);
		out.flush();
		
		
	}

}
