package com.bear.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


/*
애플리케이션의 모든 요청을 한곳에서 처리하는 Servlet
* */
@WebServlet(name = "frontControllerServlet", value = "/")  //value = "/" 모든 요청을 받겠다
public class FrontControllerServlet extends HttpServlet {

    // 요청을 처리하는 Controller
    private final HomeController homeController = new HomeController();
    private final TodoController todoController = new TodoController();

    //메뉴
    private final MenuController menuController = new MenuController();


    // WEB-INF/views/ todo/list.jsp
    private static final String VIEW_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_SUFFIX = ".jsp";

    // 전체 URI에서 어플리케이션의 Context Path를 제거
    // font-controller/ hello- -> /hello
    private String getRequestPath(HttpServletRequest req) {

        String requestURI = req.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        String contextPath = req.getContextPath();
        System.out.println("contextPath = " + contextPath);

        // substring 나누기
        // 해당 문자열에서 인자로 전달된 문자길이 만큼 자르기
        return requestURI.substring(contextPath.length());
    }

    // GET 요청 경로에 맞는 Controller 메소드를 직접 호출한다.
    private String dispatchGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // "/"
        String requestPath = getRequestPath(req);

        switch (requestPath) {
            case "/":
                return homeController.getIndex(req, resp);
            case "/todo/list":
                return todoController.getList(req, resp);
            case "/todo/create":
                return todoController.getCreate(req, resp); // "todo/create"
            case "/todo/menu":
                return menuController.getMenu(req, resp); // "todo/menu"
            case "/menu/create":
                return menuController.postCreate(req, resp);
            default:
                return null;
        }
    }

    private void render(String viewName, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (viewName == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (viewName.startsWith("redirect:")) {

            // "/todo/list"
            String redirectPath = viewName.substring("redirect:".length());
            resp.sendRedirect(req.getContextPath() + redirectPath);
            return;
        }

        // WEB-INF/viewstodo/list.jsp
        // "WEB-INF/views/todo/create.jsp"
        String viewPath = VIEW_PREFIX + viewName + VIEW_SUFFIX;

        // jsp 경로를 추가한 RequestDispatcher 객체 생성
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);

        // RequestDispatcher 를 사용해 해당 결로로 포워딩
        dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // "todo/list"
        String viewName = dispatchGet(req, resp); // "todo/create"
        render(viewName, req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // "redirect:/todo/list"
        String viewName = dispatchPost(req, resp);
        render(viewName, req, resp);
    }

    // Post 요청에 대한 처리
    private String dispatchPost(HttpServletRequest req, HttpServletResponse resp) {

        // "/todo/create"
        String requestPath = getRequestPath(req);

        switch (requestPath) {
            case "/todo/create":
                return todoController.postCreate(req,resp);
            default:
                return null;
        }
    }

}
