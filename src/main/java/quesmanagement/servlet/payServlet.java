package quesmanagement.servlet;

import quesmanagement.dao.PayDao;
import quesmanagement.entity.Pay;
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

@WebServlet(name = "payServlet", value = "/payServlet")
public class payServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        if ("delete".equals(method)) {
            String orderID = "";
            try {
               orderID= PayDao.search(new Pay(
                        request.getParameter("PayID"),
                        null,
                        user.getUserID()
                )).get(0).getOrderID();
                if (orderID == null || orderID.equals("")) {
                    throw new NullPointerException();
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                request.setAttribute("PayErr", "未找到对应付款记录。请选择一个订单再试一次。");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return;
            }
            int result = PayDao.delete(new Pay(
                    request.getParameter("payID"),
                    orderID,
                    user.getUserID()
            ));
            if (result <= 0) {
                request.setAttribute("otherErr", "取消付款失败");
                request.getRequestDispatcher("/payadd.jsp?orderID=" + orderID).forward(request, response);
            } else {
                response.sendRedirect("/payadd.jsp?orderID=" + orderID);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        float[] result;
        if ("add".equals(method)) {

            String orderID = request.getParameter("orderID");
            if (orderID == null || orderID.equals("")) {
                request.setAttribute("otherErr", "未选择需支付的订单");
                request.getRequestDispatcher("/payadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            //Paytime
            Date payTime = null;
            try {
                payTime = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("payTime"));
            } catch (ParseException e) {
               
                request.setAttribute("otherErr", "未选择时间");
                request.getRequestDispatcher("/payadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            String empID = request.getParameter("empID");
            if (empID == null || empID.equals("")) {
                request.setAttribute("otherErr", "未选择收款员工");
                request.getRequestDispatcher("/payadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            String accountID = request.getParameter("accountID");
            if (accountID == null || accountID.equals("")) {
                request.setAttribute("otherErr", "未选择收款账户");
                request.getRequestDispatcher("/payadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            String amountS = request.getParameter("amount");
            BigDecimal amount=null;
            try {
                amount=new BigDecimal(amountS);
            }catch (NumberFormatException e){
                request.setAttribute("otherErr", "未正确填写支付金额");
                request.getRequestDispatcher("/payadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            Pay PaytoAdd = new Pay(
                   genID("py"),
                    amount,
                   payTime,
                    accountID,
                   empID,
                    orderID,
                    user.getUserID()
            );

            try {
                result = PayDao.add(PaytoAdd);
                if (result[0] <= 0) {
                    if(result[0]==-2){
                        request.setAttribute("otherErr", "支付金额大于待支付金额。本订单尚可支付$"+result[1]+"。");
                        request.getRequestDispatcher("/ordershow.jsp?orderID=" + orderID).forward(request, response);
                        return;
                    }
                    request.setAttribute("otherErr", "添加支付记录失败");
                    request.getRequestDispatcher("/ordershow.jsp?orderID=" + orderID).forward(request, response);
                } else {
                    response.sendRedirect("/ordershow.jsp?orderID=" + orderID);
                }
            } catch (NumberFormatException | NullPointerException e) {
               
                request.setAttribute("otherErr", "数据不合法");
                request.getRequestDispatcher("/payadd.jsp?orderID=" + orderID).forward(request, response);
            }
        }
    }
}

