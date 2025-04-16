package app.controller;

import app.model.dto.MemberDto;
import app.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin("*")
public class MemberController {
    private final MemberService memberService;


    // [1] 회원가입
    @PostMapping("/signup")
    public MemberDto signup(@RequestBody MemberDto memberDto){
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);

        return  memberService.signup(memberDto);
    }

    // [2] 로그인
    @PostMapping("/login")
    public String login(@RequestBody MemberDto memberDto){
        System.out.println("MemberController.login");
        System.out.println("memberDto = " + memberDto);

        return  memberService.login(memberDto);
    }

    // [3] 로그인된 회원 인증 / 내정보 조회
    // @RequestHeader : HTTP 헤더 정보를 매핑하는 어노테이션 (HTTPS 권장)
        // ("Authorization) : 인증속성 ({Authorization : token 값})
    // @RequestParam : HTTP 헤더의 쿼리스트링 매핑 어노테이션
    // @RequestBody : HTTP 본문의 객체를 매핑 어노테이션
    // @PathUariable : HTTP 헤더의 경로 값 매핑 어노테이션
    @GetMapping("/info")
    public MemberDto info(@RequestHeader("Authorization") String token){
        System.out.println("MemberController.info");
        System.out.println("token = " + token);

        return memberService.info(token);
    }
}
