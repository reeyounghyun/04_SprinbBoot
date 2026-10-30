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

    // view 이동용
    @GetMapping("modify")
    public void modify() {

    }

    // @RequestParam : 요청 파라미터를 매개변수에 바로 바인딩한다. (매개변수 이름 = 폼 input의 name)
    // - 생략 가능하다.

    // required: 기본값은 true, 필수 파라미터가 없으면 400 예외 발생
    // defaultValue: 값이 없거나 비어 있을 때 사용할 기본값 지정
    @PostMapping("modify")
    public String modifyMenu(
            Model model,
            @RequestParam String modifyName,
            @RequestParam String modifyPrice
    ) {
        String message = modifyName + "을 " + modifyPrice + "원으로 수정했습니다.";
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }
}
