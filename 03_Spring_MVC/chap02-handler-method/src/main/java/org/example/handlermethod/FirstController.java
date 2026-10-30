package org.example.handlermethod;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/first/*")
public class FirstController {


    // 반환형이 Void일 경우, 요청경로(Path) 경로를 논리적인 View 이름으로 사용
    @GetMapping("regist")
    public String regist() {

        return "first/regist";
    }

    @PostMapping("regist")
    public String registMenu(Model model, HttpServletRequest request) {

        // HttpServletRequest : 서블릿이 제공하는 요청 객체로, 요청 방식·URL 등 HTTP 요청 정보를 직접 꺼낼 수 있다.
        System.out.println(request.getMethod());        // 요청 방식 (GET, POST 등)
        System.out.println(request.getRequestURL());    // 요청 전체 URL (http://localhost:8080/first/regist)

        System.out.println(request.getHeaderNames());

        String name = request.getParameter("name");
        System.out.println("name = " + name);
        int price = Integer.parseInt(request.getParameter("price"));
        System.out.println("price = " + price);
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));
        System.out.println("categoryCode = " + categoryCode);

      /*  String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " + price + "원으로 등록 하셨습니다!";
        model.addAttribute("message", message);*/

        model.addAttribute("message", name + "을 신규메뉴목록의 " + categoryCode + " 번, 가격은 " + price + "원으로 추가했습니다.");

        return "first/messagePrinter";
    }
}
