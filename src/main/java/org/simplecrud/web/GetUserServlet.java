package org.simplecrud.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.simplecrud.repository.Dao;
import org.simplecrud.repository.UserDaoImpl;
import org.simplecrud.entity.User;

import java.io.IOException;

@WebServlet("/user/**")
public class GetUserServlet extends HttpServlet {
    private Dao<User> userDao;

    @Override
    public void init() {
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] pathParts = pathInfo.split("/");
        long userId = Integer.parseInt(pathParts[1]);

        User user = userDao.get(userId).get();

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType("application/json");
        response.getWriter().write("{\"id\":" + user.getId() + ",\"name\":\"" + user.getEmail() + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"" + user.getPassword() + "\"}");
    }

}
