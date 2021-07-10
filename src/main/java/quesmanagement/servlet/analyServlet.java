package quesmanagement.servlet;

import quesmanagement.dao.AnalyDao;
import quesmanagement.dao.OrderDao;
import quesmanagement.entity.*;

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

@WebServlet(name = "analyServlet", value = "/analyServlet")
public class analyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        if ("delete".equals(method)) {
            String orderID = "";
            try {
                orderID = AnalyDao.search(new Analy(
                        request.getParameter("analyID"),
                        user.getUserID()
                )).get(0).getOrderID();
                if (orderID == null || orderID.equals("")) {
                    throw new NullPointerException();
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                request.setAttribute("orderErr", "未找到对应分析记录。请选择一个订单再试一次。");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return;
            }
            int result = AnalyDao.delete(new Analy(
                    request.getParameter("analyID"),
                    user.getUserID()
            ));
            if (result <= 0) {
                request.setAttribute("otherErr", "删除分发记录失败");
                request.getRequestDispatcher("/ordershow.jsp?orderID=" + orderID).forward(request, response);
            } else {
                response.sendRedirect("/ordershow.jsp?orderID=" + orderID);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        String queID=request.getParameter("queID");
        String orderID=request.getParameter("orderID");
        int result;
        if ("add".equals(method)) {
           
            Date analyTime = null;
            try {
                analyTime = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("analyTime"));
            } catch (ParseException | NullPointerException  e) {
               
                request.setAttribute("otherErr", "未选择时间");
                request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            String empID = request.getParameter("empID");
            if (empID == null || empID.equals("")) {
                request.setAttribute("otherErr", "未选择负责分析的员工");
                request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            BigDecimal unitPrice = null;
            String unitPriceS = "";
            try{
                boolean type= OrderDao.search(new Order(orderID,user.getUserID()))
                        .get(0).getType();
                if (type){
                    unitPriceS=null;
                }else{
                    unitPriceS=request.getParameter("unitPrice");
                    if(unitPriceS==null || unitPriceS.equals("")){
                        request.setAttribute("otherErr", "该订单为按量付费，需填写单价");
                        request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
                        return; //别再往下走了
                    }
                    unitPrice=new BigDecimal(unitPriceS);
                }
            }catch (NullPointerException e){
                request.setAttribute("otherErr", "读取订单信息失败");
                request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }catch (NumberFormatException e){
                request.setAttribute("otherErr", "读取单价信息失败");
                request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            String selectedQue = request.getParameter("queIDList") == null ? "" : request.getParameter("queIDList");
            if (selectedQue == null || selectedQue.equals("")) {
                request.setAttribute("otherErr", "未选择问卷");
                request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }
            String[] selectedQueList = selectedQue.split("[|]");
            List<Questionnaire> questionnaireList=new ArrayList<>();
            if(selectedQueList.length>=0){
                for (String que : selectedQueList){
                    String[] quepros=que.split("[+]");
                    questionnaireList.add(new Questionnaire(
                            quepros[0],
                            null,
                            null,
                            quepros[1],
                            null,
                            null,
                            null,
                            null,
                            user.getUserID(),
                            orderID
                    ));
                }
            }

            Analy analytoAdd = new Analy(
                   genID("an"),
                    analyTime,
                    questionnaireList.size(),
                    unitPrice,
                    questionnaireList,
                    orderID,
                    user.getUserID(),
                    empID
            );
            try {
                result = AnalyDao.add(analytoAdd);
                if (result <= 0) {
                    request.setAttribute("otherErr", "添加分析记录失败");
                    request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
                } else {
                    response.sendRedirect("/ordershow.jsp?orderID=" + orderID);
                }
            } catch (NumberFormatException | NullPointerException e) {
               
                request.setAttribute("otherErr", "数据不合法");
                request.getRequestDispatcher("/analyadd.jsp?orderID=" + orderID).forward(request, response);
            }
        }
    }
}

