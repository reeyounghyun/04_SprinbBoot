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


    @GetMapping("search")
    public void search() {

    }


    /*
     * @ModelAttribute: 여러 요청 파라미터를 DTO 같은 객체에 바인딩
     * -- 핸들러 메소드의 매개변수로 전달되는 동시에 Model에도 자동으로 추가된다.
     * -- Model에 담길 변수는 이름을 작성하지 않으면 클래스명의 소문자 카멜케이스로 지정된다.
     * -- Model에 담기는 이름은 매개변수 변수명(menu)과 상관없다.
     *    이름을 지정하지 않으면 항상 클래스 이름(MenuDTO)에서 첫 글자만 소문자로 바꾼 menuDTO가 된다.
     *      @ModelAttribute MenuDTO menu          → Model에 "menuDTO"로 담김
     *      @ModelAttribute("menu") MenuDTO menu  → Model에 "menu"로 담김
     * -- 이름을 지정하지 않는 방법도 있다.
     *    컨트롤러는 그대로 두고 HTML 쪽을 ${ menuDTO.name }으로 바꿔도 동작한다. (둘 중 하나로 이름만 맞추면 된다.)
     * */
    @PostMapping("search")
    public String searchMenu(@ModelAttribute("menu") MenuDTO menu) {
        return "first/searchResult";
    }
}
