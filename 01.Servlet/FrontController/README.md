# FrontController

## 요청 처리 흐름

서블릿을 URL마다 따로 만들지 않고, **`FrontControllerServlet` 하나(`@WebServlet(value = "/")`)가 모든 요청을 받아서** 알맞은 Controller로 나눠주는 구조입니다.

### 사용자 중심 흐름

1. 사용자가 브라우저에서 메인 주소(`/`)로 접속한다.
2. FrontControllerServlet이 요청을 받아 HomeController에 넘기고, 메인 화면(index.jsp)이 보인다.
3. 사용자가 메인 화면에서 "새 Todo" 링크를 클릭한다.
4. 브라우저가 `/todo/create`로 GET 요청을 보내고, FrontControllerServlet이 TodoController에 넘긴다.
5. 사용자에게 Todo 등록 폼(create.jsp)이 보인다.
6. 사용자가 폼의 제출 버튼을 누르면 `/todo/create`로 POST 요청이 전송된다.
7. FrontControllerServlet이 TodoController의 등록 처리로 넘기고, 처리가 끝나면 목록 페이지로 redirect를 응답한다.
8. 브라우저는 redirect 응답을 받고 자동으로 `/todo/list`에 다시 GET 요청을 보낸다.
9. TodoController가 Todo 목록 데이터를 담고, 사용자에게 Todo 목록 화면(list.jsp)이 보인다.
10. 이때 사용자가 새로고침을 해도 등록 요청은 다시 전송되지 않고 목록 조회만 반복된다.
11. 사용자가 메인 화면에서 "새 menu" 링크를 클릭하면 `/todo/menu`로 GET 요청이 전송된다.
12. FrontControllerServlet이 MenuController에 넘기고, 사용자에게 메뉴 목록 화면(menu.jsp)이 보인다.
13. 사용자가 등록되지 않은 주소로 접속하면 FrontControllerServlet이 처리할 Controller를 찾지 못해 404 에러 페이지가 보인다.

정리하면, 사용자가 어떤 주소로 요청하든 항상 FrontControllerServlet이 먼저 받고, 주소에 맞는 Controller가 처리한 뒤 화면(JSP)을 보여주거나 다른 주소로 이동시킨다.

### 전체 흐름

```
 브라우저 요청 (예: GET /todo/list)
        │
        ▼
 ┌─────────────────────────────────────────────┐
 │ FrontControllerServlet  (value = "/")       │
 │                                             │
 │ ① doGet() / doPost()                        │
 │ ② getRequestPath()                          │
 │     requestURI - contextPath → "/todo/list" │
 │ ③ dispatchGet() / dispatchPost()            │
 │     switch(requestPath)로 Controller 선택   │
 └─────────────────────────────────────────────┘
        │
        ▼
 ┌─────────────────────────────────────────────┐
 │ Controller (Home / Todo / Menu)             │
 │ ④ 필요한 데이터를 req.setAttribute()로 저장 │
 │ ⑤ viewName 문자열 반환                      │
 │     예) "todo/list", "redirect:/todo/list"  │
 └─────────────────────────────────────────────┘
        │
        ▼
 ┌─────────────────────────────────────────────┐
 │ render(viewName)                            │
 │ ⑥ viewName에 따라 처리 방식 결정            │
 │   ├ null            → 404 sendError         │
 │   ├ "redirect:경로"  → sendRedirect          │
 │   └ 그 외           → JSP로 forward          │
 │       "/WEB-INF/views/" + viewName + ".jsp" │
 └─────────────────────────────────────────────┘
        │
        ▼
 JSP에서 ${attribute}를 출력해 응답
```

---

### 단계별 설명

**① 요청 수신: `doGet()` / `doPost()`**
`value = "/"`로 매핑되어 있어서 모든 경로의 요청이 이 서블릿으로 들어옵니다.

**② 경로 추출: `getRequestPath()`**
```java
requestURI.substring(contextPath.length());
// "/front-controller/todo/list" → "/todo/list"
```
앱이 배포된 경로(contextPath)에 상관없이 같은 경로로 비교할 수 있도록 contextPath를 잘라냅니다.

**③ Controller 분배: `dispatchGet()` / `dispatchPost()`**
`switch(requestPath)`로 경로에 맞는 Controller 메서드를 직접 호출합니다. 일치하는 경로가 없으면 `null`을 반환합니다.

**④~⑤ Controller 처리**
Controller는 화면을 직접 그리지 않습니다. 데이터를 attribute에 담고 **어떤 화면으로 갈지(viewName)만 문자열로 반환**합니다.

**⑥ 화면 결정: `render()`**

| viewName | 처리 | 결과 |
|---|---|---|
| `null` | `resp.sendError(404)` | 404 페이지 |
| `"redirect:/todo/list"` | `resp.sendRedirect(contextPath + "/todo/list")` | 브라우저가 새로 GET 요청 |
| `"todo/list"` | `forward("/WEB-INF/views/todo/list.jsp")` | JSP 화면 출력 |

---

### URL 매핑표

| Method | URL | Controller 메서드 | 반환 viewName | 화면 |
|---|---|---|---|---|
| GET | `/` | `HomeController.getIndex` | `index` | 메인 (링크 목록) |
| GET | `/todo/list` | `TodoController.getList` | `todo/list` | `${todoList}` 출력 |
| GET | `/todo/create` | `TodoController.getCreate` | `todo/create` | 등록 폼 |
| POST | `/todo/create` | `TodoController.postCreate` | `redirect:/todo/list` | 목록으로 이동 |
| GET | `/todo/menu` | `MenuController.getMenu` | `todo/menu` | `${todoMenu}` 출력 |
| GET | `/menu/create` | `MenuController.postCreate` | `redirect:/todo/menu` | 메뉴로 이동 |

---

### 예시 시나리오: Todo 등록

```
1. GET  /               → index.jsp            ("새 Todo" 링크 클릭)
2. GET  /todo/create    → todo/create.jsp      (폼 제출)
3. POST /todo/create    → "redirect:/todo/list"
4. 브라우저가 자동으로 GET /todo/list 요청
5. GET  /todo/list      → todoList attribute 저장 → todo/list.jsp
```

> **POST 후 redirect를 하는 이유 (PRG 패턴: Post → Redirect → Get)**
> POST 처리 후 바로 화면을 보여주면, 새로고침할 때 등록 요청이 다시 전송됩니다.
> redirect로 목록 페이지(GET)로 보내면 새로고침해도 목록 조회만 반복됩니다.

---

### 새 기능 추가 방법 (Menu 추가 과정)

1. `MenuController` 작성: 데이터를 `req.setAttribute("todoMenu", ...)`에 담고 `"todo/menu"` 반환
2. `views/todo/menu.jsp` 작성: `${todoMenu}` 출력
3. `FrontControllerServlet`에 필드 추가: `private final MenuController menuController = new MenuController();`
4. `dispatchGet()`의 switch문에 `case "/todo/menu"` 추가

> 기능이 늘어나도 **진입점은 FrontControllerServlet 하나로 유지**됩니다. 공통 처리(경로 추출, 화면 렌더링)를 한곳에서 관리하는 것이 이 패턴의 핵심이고, Spring MVC의 `DispatcherServlet`이 같은 구조로 동작합니다.
