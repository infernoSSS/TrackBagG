package servlets;

import db.DB;
import db.Status;
import util.TemplatesPlaceholder;
import util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class Register extends HttpServlet {
    private String massage = "";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> vals= new HashMap();
        vals.put("massage", massage);
        massage = "";
        String res = TemplatesPlaceholder.getInstance().fillTemplateFromFile("register", vals);
        resp.getWriter().append(res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.getWriter().write(req.getParameter("login") + req.getParameter("password") +
//                DB.getDB().getUsersDB().register(req.getParameter("login"), req.getParameter("password")));
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        boolean access =
                DB.getDB().getUsersDB().register(req.getParameter("login"), req.getParameter("password")) ==
                Status.CREATED;
        if(!access){
            massage = "The selected login is already taken";
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }
        HttpSession session = req.getSession();
        session.setAttribute("username", req.getParameter("login"));
        session.setAttribute("password", req.getParameter("password"));
        resp.sendRedirect(req.getContextPath() + "/usersarea");
    }
}
