package quesmanagement.servlet;

import quesmanagement.dao.QuestionnaireDao;
import quesmanagement.entity.Questionnaire;
import quesmanagement.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static quesmanagement.utils.genID.genID;

@WebServlet(name = "queServlet", value = "/queServlet")
public class queServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        if ("delete".equals(method)) {
            int result = QuestionnaireDao.delete(new Questionnaire(
                    request.getParameter("queID"),
                    user.getUserID()
            ));
            if (result <= 0) {
                request.setAttribute("queErr", "删除问卷失败");
                request.getRequestDispatcher("/quelist.jsp").forward(request, response);
            } else {
                response.sendRedirect("/quelist.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        int result;
        switch (method) {
            case "add":
                try {
                   result = QuestionnaireDao.add(new Questionnaire(
                            genID("qe"),
                            request.getParameter("queTitle"),
                            request.getParameter("questionTitle"),
                            user.getUserID()
                    ));
                    if (result <= 0) {
                        request.setAttribute("queErr", "添加问卷失败");
                        request.getRequestDispatcher("/quelist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/quelist.jsp");
                    }
                    break;
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("queErr", "数据不合法");
                    request.getRequestDispatcher("/quelist.jsp").forward(request, response);
                }

            case "edit":
                try {
                    result = QuestionnaireDao.modify(new Questionnaire(
                            request.getParameter("queID").equals("")?null:request.getParameter("queID"),
                            request.getParameter("queTitle").equals("")?null:request.getParameter("queTitle"),
                            request.getParameter("questionTitle").equals("")?null:request.getParameter("questionTitle"),
                            user.getUserID()
                    ));
                    if (result <=0) {
                        request.setAttribute("queErr", "修改问卷失败");
                        request.getRequestDispatcher("/quelist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/quelist.jsp");
                    }
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("queErr", "数据不合法");
                    request.getRequestDispatcher("/quelist.jsp").forward(request, response);
                }
                break;
        }
    }
}

