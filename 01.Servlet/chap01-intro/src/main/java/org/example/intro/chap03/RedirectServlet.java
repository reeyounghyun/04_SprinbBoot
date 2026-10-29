package org.example.intro.chap03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/chap03/result")
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 쿼리스트링 담을 수 있다.
        String message = req.getParameter("message");

        // 새요청이 만들어지므로 지정한 값이 사라진다.
        req.setAttribute("moveType", "FORWARD");
        req.setAttribute("message", message);

        // 리다이렉트 할 URL 생성
        String redirectUrl = req.getContextPath() + "/chap03/result?message=" + message;

        resp.sendRedirect(redirectUrl);
    }
}
