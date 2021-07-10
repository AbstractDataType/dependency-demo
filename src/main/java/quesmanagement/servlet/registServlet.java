package quesmanagement.servlet;

import quesmanagement.dao.UserDao;
import quesmanagement.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static quesmanagement.utils.genID.genUserID;

@WebServlet(name = "registServlet", value = "/registServlet")
public class registServlet extends HttpServlet {
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
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
        int result = UserDao.add(new User(genUserID(), userName, userPass));
        //4.调用响应对象，根据验证结果将不同资源文件地址写入到响应头，交给浏览器
        if (result == -2) {
            request.setAttribute("regErr", "用户已存在");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (result < 0) {
            request.setAttribute("regErr", "注册失败");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }else{
            request.setAttribute("loginErr","注册成功，请登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
