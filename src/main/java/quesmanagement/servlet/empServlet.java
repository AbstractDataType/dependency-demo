package quesmanagement.servlet;

import quesmanagement.dao.EmpDao;
import quesmanagement.entity.Emp;
import quesmanagement.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static quesmanagement.utils.genID.genID;

@WebServlet(name = "empServlet", value = "/empServlet")
public class empServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        switch (method) {
            case "add":
                try {
                    int result = EmpDao.add(new Emp(
                            genID("ep"),
                            request.getParameter("empName"),
                            user.getUserID()
                    ));
                    if (result <= 0) {
                        request.setAttribute("empErr", "添加员工失败");
                        request.getRequestDispatcher("/emplist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/emplist.jsp");
                    }
                    break;
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("empErr", "数据不合法");
                    request.getRequestDispatcher("/emplist.jsp").forward(request, response);
                }
            case "delete":
                int result = EmpDao.delete(new Emp(
                        request.getParameter("empID"),
                        user.getUserID()
                ));
                if (result <= 0) {
                    request.setAttribute("empErr", "删除员工失败");
                    request.getRequestDispatcher("/emplist.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/emplist.jsp");
                }
                break;
            case "edit":
                try {
                    result = EmpDao.modify(new Emp(
                            request.getParameter("empID").equals("")?null:request.getParameter("empID"),
                            request.getParameter("empName").equals("")?null:request.getParameter("empName"),
                            user.getUserID()
                    ));
                    if (result <= 0) {
                        request.setAttribute("empErr", "修改员工失败");
                        request.getRequestDispatcher("/emplist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/emplist.jsp");
                    }
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("empErr", "数据不合法");
                    request.getRequestDispatcher("/emplist.jsp").forward(request, response);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
