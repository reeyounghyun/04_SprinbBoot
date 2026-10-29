package com.bear.frontcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

// Todo와 관련된 요청을 처리
public class MenuController {

    // tRequest => 요구
    // Response => 응당
    public String getMenu(HttpServletRequest req, HttpServletResponse resp) {

        List<String> todoMenu = Arrays.asList("메뉴 1", "메뉴 2", "메뉴 3");
        req.setAttribute("todoMenu", todoMenu); // 어트리뷰트에 리스트 저장

        return "todo/menu";
    }


    // create.jsp 이동용
    public String getCreate(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("Get /todo/create ");
        return "todo/create";
    }

    // todo생성이 반복되지 않게 redirect 시키기 (목록 주소로)
    public String postCreate(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Post /todo/create ");

        return "redirect:/todo/menu";
    }
}