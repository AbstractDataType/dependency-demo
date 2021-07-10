package quesmanagement.servlet;

import quesmanagement.dao.DistDao;
import quesmanagement.dao.RecvDao;
import quesmanagement.entity.Dist;
import quesmanagement.entity.Questionnaire;
import quesmanagement.entity.Recv;
import quesmanagement.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static quesmanagement.utils.genID.genID;

@WebServlet(name = "recvServlet", value = "/recvServlet")
public class recvServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        String method = request.getParameter("method");
        if ("delete".equals(method)) {
            String orderID = "";
            try {
                orderID = RecvDao.search(new Recv(
                        request.getParameter("recvID"),
                        user.getUserID()
                )).get(0).getOrderID();
                if (orderID == null || orderID.equals("")) {
                    throw new NullPointerException();
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                request.setAttribute("recvErr", "未找到对应回收记录。请选择一个订单再试一次。");
                request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                return;
            }
            int result = RecvDao.delete(new Recv(
                    request.getParameter("recvID"),
                    user.getUserID()
            ));
            if (result <= 0) {
                if(result==-2){
                    request.setAttribute("otherErr", "删除分发记录失败-试卷已被分析");
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
        String method = request.getParameter("method");
        String queID=request.getParameter("queID");
        String orderID=request.getParameter("orderID");
        int result;
        if ("add".equals(method)) {
           
            Date recvTime = null;
            try {
                recvTime = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("recvTime"));
            } catch (ParseException e) {
               
                request.setAttribute("otherErr", "未选择时间");
                request.getRequestDispatcher("/recvadd.jsp?queID=" + queID+"&orderID="+orderID).forward(request, response);
                return; //别再往下走了
            }

            String empID = request.getParameter("empID");
            if (empID == null || empID.equals("")) {
                request.setAttribute("otherErr", "未选择负责回收的员工");
                request.getRequestDispatcher("/recvadd.jsp?queID=" + queID+"&orderID="+orderID).forward(request, response);
                return; //别再往下走了
            }

            String filler = request.getParameter("filler");
            if (filler == null || filler.equals("")) {
                request.setAttribute("otherErr", "未选择填写者");
                request.getRequestDispatcher("/recvadd.jsp?queID=" + queID+"&orderID="+orderID).forward(request, response);
                return; //别再往下走了
            }

            String answer = request.getParameter("answer");
            if (answer == null || answer.equals("")) {
                request.setAttribute("otherErr", "未选择问卷答案");
                request.getRequestDispatcher("/recvadd.jsp?queID=" + queID+"&orderID="+orderID).forward(request, response);
                return; //别再往下走了
            }

            Recv recvtoAdd = new Recv(
                   genID("rc"),
                    recvTime,
                    new Questionnaire(
                            queID,
                            recvTime,
                            filler,
                            null,
                            answer,
                            null,
                            null,
                            null,
                            user.getUserID(),
                            orderID),
                    empID,
                    user.getUserID(),
                    orderID
            );
            try {
                result = RecvDao.add(recvtoAdd);
                if (result <= 0) {
                    request.setAttribute("otherErr", "添加回收记录失败");
                    request.getRequestDispatcher("/recvadd.jsp?queID=" + queID).forward(request, response);
                } else {
                    response.sendRedirect("/ordershow.jsp?orderID=" + orderID);
                }
            } catch (NumberFormatException | NullPointerException e) {
               
                request.setAttribute("otherErr", "数据不合法");
                request.getRequestDispatcher("/recvadd.jsp?queID=" + queID).forward(request, response);
            }
        }
    }
}

