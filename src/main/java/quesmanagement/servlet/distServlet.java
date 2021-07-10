package quesmanagement.servlet;

import quesmanagement.dao.DistDao;
import quesmanagement.entity.Dist;
import quesmanagement.entity.Questionnaire;
import quesmanagement.entity.User;
import quesmanagement.utils.ZeroException;

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

@WebServlet(name = "distServlet", value = "/distServlet")
public class distServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        if ("delete".equals(method)) {
            String orderID = "";
            try {
                orderID = DistDao.search(new Dist(
                        request.getParameter("distID"),
                        user.getUserID()
                )).get(0).getOrderID();
                if (orderID == null || orderID.equals("")) {
                    throw new NullPointerException();
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                request.setAttribute("distErr", "未找到对应分发记录。请选择一个订单再试一次。");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return;
            }
            int result = DistDao.delete(new Dist(
                    request.getParameter("distID"),
                    user.getUserID()
            ));
            if (result <= 0) {
                if(result==-2){
                    request.setAttribute("otherErr", "删除分发记录失败-试卷已被回收");
                }else {
                    request.setAttribute("otherErr", "删除分发记录失败");
                }
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
        String orderID=request.getParameter("orderID");
        String method = request.getParameter("method");
        int result;
        if ("add".equals(method)) {
            //disttime
            Date distTime = null;
            try {
                distTime = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("distTime"));
            } catch (ParseException e) {
               
                request.setAttribute("otherErr", "未选择时间");
                request.getRequestDispatcher("/ordershow.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            String empID = request.getParameter("empID");
            if (empID == null || empID.equals("")) {
                request.setAttribute("otherErr", "未选择负责分发的员工");
                request.getRequestDispatcher("/ordershow.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }

            //que selected
            String selectedQue = request.getParameter("queIDList") == null ? "" : request.getParameter("queIDList");
            String[] selectedQueList = selectedQue.split("[+]");
            if (selectedQueList.length <= 0) {
                request.setAttribute("otherErr", "未选择任何问卷");
                request.getRequestDispatcher("/ordershow.jsp?orderID=" + orderID).forward(request, response);
                return; //别再往下走了
            }
            List<Questionnaire> questionnaireList = new ArrayList<>();
            for (String que : selectedQueList) {
                questionnaireList.add(new Questionnaire(
                        que,
                        user.getUserID()
                ));
            }


            Dist disttoAdd = new Dist(
                   genID("ds"),
                    distTime,
                    questionnaireList,
                    empID,
                    orderID,
                    user.getUserID()
            );
            try {
                result = DistDao.add(disttoAdd);
                if (result <= 0) {
                    request.setAttribute("otherErr", "添加分发记录失败");
                    request.getRequestDispatcher("/ordershow.jsp?orderID=" + orderID).forward(request, response);
                } else {
                    response.sendRedirect("/ordershow.jsp?orderID=" + orderID);
                }
            } catch (NumberFormatException | NullPointerException e) {
               
                request.setAttribute("otherErr", "数据不合法");
                request.getRequestDispatcher("/distadd.jsp?orderID=" + orderID).forward(request, response);
            }
        }
    }
}

