package section02.javaconfig;

import common.MemberDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*@Configuration
* 정의: 클래스가 설정파일을 의미
* 이 클래스가 spirng-context.xml 같은 설정 파일 역할을 한다*/
W
public class ContextConfiguration {

    @Bean(name = "member")
    public MemberDTO getMember() {
        return new MemberDTO(1, "user01", "pass01", "bear");
    }
}
