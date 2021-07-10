package quesmanagement.servlet;

import quesmanagement.dao.UserDao;
import quesmanagement.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //2.调用请求对象读取请求体参数信息
        String userName = request.getParameter("userName");
        String userPass = request.getParameter("userPass");
        if (userName.equals("")||userPass.equals("")){
            request.setAttribute("loginErr","用户名或密码为空");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        User result = UserDao.login(new User(userName,userPass));
        //4.调用响应对象，根据验证结果将不同资源文件地址写入到响应头，交给浏览器
        if(result !=null){
            HttpSession httpSession=request.getSession();
            httpSession.setAttribute("user",result);
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }else{
            request.setAttribute("loginErr","登陆失败");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
