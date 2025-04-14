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


}
