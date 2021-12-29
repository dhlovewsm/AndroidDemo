package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/***
 *  用于验证登陆的controller
 */
public class Login extends HttpServlet {
    private static final long serialVersionID = 1L;

    /***
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        Connection connection;
        Statement sql;
        String logname = request.getParameter("logname").trim();
        String password = request.getParameter("password").trim();
        String url = "jdbc:mysql://localhost:3306/MakeFriend";
        try {
            //password是自己数据库密码，保密起见隐藏
            connection = DriverManager.getConnection(url,"root","******");
            String condition = "select * from member where logname = '"+logname+"'and password = '"+password+"'";
            sql = connection.prepareStatement(condition);
            ResultSet resultSet = ((PreparedStatement) sql).executeQuery(condition);
            if (resultSet.next()){
                writer.println("login successfully!");
                //用于后端显示验证是否交互
                System.out.println("succeed");
            }else {
                writer.println("can not login!");
            }
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        doGet(request,response);
    }
}