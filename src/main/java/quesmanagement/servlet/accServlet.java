package quesmanagement.servlet;

import quesmanagement.dao.AccountDao;
import quesmanagement.entity.Account;
import quesmanagement.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static quesmanagement.utils.genID.genID;

@WebServlet(name = "accServlet", value = "/accServlet")
public class accServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        switch (method) {
            case "add":
                try {
                    BigDecimal balance=null;
                    try {
                        balance= BigDecimal.valueOf(Float.parseFloat(request.getParameter("balance")));
                    } catch (NullPointerException | NumberFormatException e){
                        balance=new BigDecimal(0);
                    }
                    int result = AccountDao.add(new Account(
                            genID("bk"),
                            request.getParameter("bank"),
                            Long.valueOf(request.getParameter("bankAccountID")),
                            balance,
                            user.getUserID()
                    ));
                    if (result <= 0) {
                        request.setAttribute("accErr", "添加账户失败");
                        request.getRequestDispatcher(request.getContextPath()+"/acclist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath()+"/acclist.jsp");
                    }
                    break;
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("accErr", "数据不合法");
                    request.getRequestDispatcher(request.getContextPath()+"/acclist.jsp").forward(request, response);
                }
            case "delete":
                int result = AccountDao.delete(new Account(
                        request.getParameter("accID"),
                        user.getUserID()
                ));
                if (result <= 0) {
                    request.setAttribute("accErr", "删除账户失败");
                    request.getRequestDispatcher(request.getContextPath()+"/acclist.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath()+"/acclist.jsp");
                }
                break;
            case "edit":
                try {
                    result = AccountDao.modify(new Account(
                            request.getParameter("accID").equals("")?null:request.getParameter("accID"),
                            request.getParameter("bank").equals("")?null:request.getParameter("bank"),
                            request.getParameter("bankAccountID").equals("")?null:Long.valueOf(request.getParameter("bankAccountID")),
                            user.getUserID()
                    ));
                    if (result <= 0) {
                        request.setAttribute("accErr", "修改账户失败");
                        request.getRequestDispatcher("/acclist.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/acclist.jsp");
                    }
                } catch (NumberFormatException | NullPointerException e) {
                   
                    request.setAttribute("accErr", "数据不合法");
                    request.getRequestDispatcher("/acclist.jsp").forward(request, response);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
