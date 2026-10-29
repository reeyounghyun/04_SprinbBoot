package section01;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl  implements MemberService {

    @Override
    public String processMember(boolean error) {

        System.out.println("[Target] 회원처리");

        if(error) {
            // 예외 발생
            throw new IllegalStateException("회원처리중 오류 발생");
        }

        return "회원 처리 완료";
    }
}
