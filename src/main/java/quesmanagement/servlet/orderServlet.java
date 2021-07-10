package quesmanagement.servlet;

import quesmanagement.dao.OrderDao;
import quesmanagement.dao.QuestionnaireDao;
import quesmanagement.entity.Order;
import quesmanagement.entity.Questionnaire;
import quesmanagement.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static quesmanagement.utils.genID.genID;

@WebServlet(name = "orderServlet", value = "/orderServlet")
public class orderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        if ("delete".equals(method)) {
            int result = OrderDao.delete(new Order(
                    request.getParameter("orderID"),
                    user.getUserID()
            ));
            if (result <= 0) {
                request.setAttribute("orderErr", "删除订单失败");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
            } else {
                response.sendRedirect("/orderlist.jsp");
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
        if ("add".equals(method)) {
            //ordertime
            Date orderTime = null;
            try {
                orderTime = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("orderTime"));
            } catch (ParseException e) {
               
                request.setAttribute("orderErr", "未选择时间");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return; //别再往下走了
            }

            String empID = request.getParameter("empID");
            if (empID == null || empID.equals("")) {
                request.setAttribute("orderErr", "未选择创建员工");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return; //别再往下走了
            }

            String custID = request.getParameter("custID");
            if (custID == null || custID.equals("")) {
                request.setAttribute("orderErr", "未选择客户");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return; //别再往下走了
            }

            String payTypeS = request.getParameter("payType");
            boolean paytype = true;
            if (payTypeS == null || !(payTypeS.equals("True") || payTypeS.equals("False"))) {
                request.setAttribute("orderErr", "未选择付费方式");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return; //别再往下走了
            }
            if (payTypeS.equals("True")) {
                paytype = true;
            }
            if (payTypeS.equals("False")) {
                paytype = false;
            }

            //unitPrice
            String unitPriceS = request.getParameter("unitPrice");
            BigDecimal unitPrice = null;
            try {
                unitPrice = new BigDecimal(unitPriceS);
            } catch (NumberFormatException e) {
               
                request.setAttribute("orderErr", "未指定单价");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return; //别再往下走了
            }

            //que selected
            String selectedQue = request.getParameter("queIDList") == null ? "" : request.getParameter("queIDList");
            String[] selectedQueList = selectedQue.split("[+]");
            if (selectedQueList.length <= 0) {
                request.setAttribute("orderErr", "未选择任何问卷");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return; //别再往下走了
            }
            List<Questionnaire> questionnaireList = new ArrayList<>();
            for (String que : selectedQueList) {
                questionnaireList.add(new Questionnaire(
                        que,
                        user.getUserID()
                ));
            }


            Order ordertoAdd = new Order(
                    genID("od"),
                    orderTime,
                    unitPrice,
                    paytype,
                    custID,
                    empID,
                    user.getUserID(),
                    questionnaireList
            );
            try {
                result = OrderDao.add(ordertoAdd);
                if (result <= 0) {
                    request.setAttribute("orderErr", "添加订单失败");
                    request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/orderlist.jsp");
                }
            } catch (NumberFormatException | NullPointerException e) {
               
                request.setAttribute("orderErr", "数据不合法");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
            }
        }
    }
}

