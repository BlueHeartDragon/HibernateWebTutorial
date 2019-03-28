package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.google.gson.Gson;

import entity.Employee;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		if (util.Helper.factory == null) {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
			Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
			util.Helper.factory = meta.getSessionFactoryBuilder().build();
		}

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		if (!util.Helper.factory.isClosed()) {
			util.Helper.factory.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fname = request.getParameter("fname");
		List<Employee> resultSet = util.Helper.listEmployees(fname);
		if (resultSet != null || resultSet.size() != 0) {
			String[] result = new String[resultSet.size()];
			int i = 0;
			for (Employee emp : resultSet) {
				result[i++] = emp.getFname() + "\t" + emp.getLname() + "\t" + emp.getSalary() + "\t"
						+ emp.getDecryptedSecretKey();
			}
			response.setContentType("application/json");
			new Gson().toJson(result, response.getWriter());
		}
	}

}
