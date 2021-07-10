package quesmanagement.servlet;

import quesmanagement.dao.CustomerDao;
import quesmanagement.entity.Customer;
import quesmanagement.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static quesmanagement.utils.genID.genID;

@WebServlet(name = "custServlet", value = "/custServlet")
public class custServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        switch (method) {
            case "add":
                try {
                    int result = CustomerDao.add(new Customer(
                            genID("cu"),
                            request.getParameter("custName"),
                            request.getParameter("custAddress"),
                            Integer.valueOf(request.getParameter("custZip")),
                            request.getParameter("custEmail"),
                            user.getUserID()
                    ));
                    if (result <= 0) {
                        request.setAttribute("custErr", "添加客户失败");
                        request.getRequestDispatcher("/custlist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/custlist.jsp");
                    }
                    break;
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("custErr", "数据不合法");
                    request.getRequestDispatcher("/custlist.jsp").forward(request, response);
                }
            case "delete":
                int result = CustomerDao.delete(new Customer(
                        request.getParameter("custID"),
                        user.getUserID()
                ));
                if (result <= 0) {
                    request.setAttribute("custErr", "删除客户失败");
                    request.getRequestDispatcher("/custlist.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/custlist.jsp");
                }
                break;
            case "edit":
                try {
                    result = CustomerDao.modify(new Customer(
                            request.getParameter("custID").equals("")?null:request.getParameter("custID"),
                            request.getParameter("custName").equals("")?null:request.getParameter("custName"),
                            request.getParameter("custAddress").equals("") ?null:request.getParameter("custAddress"),
                            request.getParameter("custZip").equals("") ?null:Integer.valueOf(request.getParameter("custZip")),
                            request.getParameter("custEmail").equals("")?null:request.getParameter("custEmail"),
                            user.getUserID()
                    ));
                    if (result <= 0) {
                        request.setAttribute("custErr", "修改客户失败");
                        request.getRequestDispatcher("/custlist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/custlist.jsp");
                    }
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("custErr", "数据不合法");
                    request.getRequestDispatcher("/custlist.jsp").forward(request, response);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
