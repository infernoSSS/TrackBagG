package servlets;

import content.makers.BugPageContentMaker;
import db.DB;
import util.TemplatesPlaceholder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bug")
public class Bug extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = (String) req.getSession().getAttribute("username");
        String bug_id = req.getParameter("bug_id");
        String projuuid = req.getParameter("uuid");
        if(bug_id == null){
            resp.getWriter().println(new BugPageContentMaker().createBugPage(userName, projuuid));
        }else{
            resp.getWriter().println(new BugPageContentMaker().viewBug(userName, Integer.parseInt(bug_id)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if((req.getParameter("post_action") != null) && (req.getParameter("post_action").equals("bug"))){
            if(req.getParameter("button").equals("post")){
                int bugId = DB.getDB().getBugsDB().addBug(req.getParameter("uuid"),
                        (String) req.getSession().getAttribute("username"),
                        req.getParameter("description"));
                resp.sendRedirect(req.getContextPath()+"/bug"+"?uuid="+req.getParameter("uuid")+"&"+"bug_id="+bugId);
            }
            if(req.getParameter("button").equals("edit")){

            }
            if(req.getParameter("button").equals("delete")){

            }
        }
    }
}
