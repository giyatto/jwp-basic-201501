#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 처음 로딩될 때, 서블릿 객체가 존재하지 않기 때문에 다음을 수행한다.  
** 서블릿 클래스를 로드한다. (WEB-INF/classes)  
** 서블릿 클래스 객체를 생성한다.  
** init 메서드를 호출해서 서블릿 객체를 초기화한다.  
* 서블릿 클래스 파일을 검색한다. (WEB-INF/classes)
* Servlet Context 객체를 생성한다. (Servlet Context : 하나의 서블릿이 서블릿 컨테이너와 통신하기 위해서 사용되어지는 메서드들을 가지고 있는 클래스)
* listner(해당 소스는 next.support.context.ContextLoaderListner.java 에는 @WebListner 어노테이션 사용)

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* index.jsp가 호출된다.  
* DispatcherServlet으로 이동
* service 메서드가 실행되고, 클라이언트가 요청한 자원 주소를 얻는다. (String requestUri = req.getRequestURI();)
* RequestMapping의 findController 메서드를 이용해서 클라이언트가 요청한 주소에 대한 응답을 처리할 컨트롤러를 찾는다.  
* 현재, index.jsp에서 list.next로 sendRedirect 했으므로, RequestMapping에 의해 ListController 객체가 만들어진다.
* ListController 객체가 만들어지면서, 화면에는 list.jsp가 보여지고, 
* ModelAndView에 의해 불려진 질문들이 화면에 나타난다. 

#### 8. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 클라이언트가 서블릿 컨테이너에 요청을 보내면 init() 메소드로 초기화된 service() 메소드가 실행되는데, 각 사용자에 대하여 각각 다른 쓰레드를 생성한다.
* 그런데, 이때 RequestMapping의 initMapping() 메소드는 한번만 실행된다.
* 즉, 각각 다른 쓰레드에서 ListController와 ShowController의 필드를 공유하게 되는것이다.(이러면 안됨)
* 따라서, ListController와 ShowController에 있는 클래스 필드를 클래스 내에 있는 메서드 안으로 넣어서 인스턴스가 생성될 때 각각의 필드도 따로 생성되도록 해야한다.


