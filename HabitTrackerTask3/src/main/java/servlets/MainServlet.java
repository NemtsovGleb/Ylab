package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем путь после "/app/"
        String path = request.getPathInfo();

        if ("/user".equals(path)) {
            handleUserRequest(request, response);
        } else if ("/order".equals(path)) {
            handleOrderRequest(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }

    private void handleUserRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("User page handled by MainServlet");
    }

    private void handleOrderRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("Order page handled by MainServlet");
    }
}
