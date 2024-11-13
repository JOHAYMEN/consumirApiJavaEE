package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;

import modelo.ApiResponse;
import servicio.ApiService;

@WebServlet("/api/consumeApi")
public class ApiServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApiService apiService = new ApiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        ApiResponse apiResponse = apiService.getNombreResponse(name);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(apiResponse);

        response.getWriter().write(jsonResponse);
    }
}
