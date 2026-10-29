package section01.xmlconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Application {

    // Application Context 를 만들것!
    // XML 파일로 설정정보를 담아서 만들어보기
    public static void main(String[] args) {

        ApplicationContext context = new GenericXmlApplicationContext("xmlconfig/spirng-context.xml");
    }
}
