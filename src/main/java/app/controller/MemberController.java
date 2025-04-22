package app.controller;

import app.model.dto.MemberDto;
import app.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Boolean> signup(@RequestBody MemberDto memberDto){
        // 제네릭 타입으로 boolean 불가 / Boolean 만 가능
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);

        boolean result =  memberService.signup(memberDto);

        // 결과에 따라 개발자 마음대로 원하는 응답코드 반환
        if(result){
            // 201(Create OK) : true 반환
            return ResponseEntity.status(201).body(true);

        }else {
            // 400(bad request) : false 반환
            return ResponseEntity.status(400).body(false);
        }
    }

    // [2] 로그인
    @PostMapping("/login")
    public ResponseEntity< String > login( @RequestBody MemberDto memberDto ){
        String token = memberService.login( memberDto );
        if( token != null ){   return ResponseEntity.status( 200 ).body( token ); // 만약에 토큰이 존재하면(로그인성공)
        }else{   return ResponseEntity.status( 401 ).body( "로그인성공" );   } // 인증 실패 : 401
    }

    // [3] 로그인된 회원 인증 / 내정보 조회
    // @RequestHeader : HTTP 헤더 정보를 매핑하는 어노테이션 (HTTPS 권장)
        // ("Authorization) : 인증속성 ({Authorization : token 값})
    // @RequestParam : HTTP 헤더의 쿼리스트링 매핑 어노테이션
    // @RequestBody : HTTP 본문의 객체를 매핑 어노테이션
    // @PathUariable : HTTP 헤더의 경로 값 매핑 어노테이션
    @GetMapping("/info")
    public ResponseEntity<MemberDto> info(@RequestHeader("Authorization") String token){
        System.out.println("MemberController.info");
        System.out.println("token = " + token);

        MemberDto memberDto = memberService.info(token);
        if(memberDto != null){
            return  ResponseEntity.status(200).body(memberDto);
        }else {
            //  403 상태코드와 NoContent(권한없음) qksghks
            return  ResponseEntity.status(403).build();
        }
    }

    // [4] 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(
            // 로그아웃할 토큰 가져오기
            @RequestHeader("Authorization") String token){
        System.out.println("MemberController.logout");
        System.out.println("token = " + token);

        memberService.logout(token);

        // 204 : 성공했지만 반환할 값이 없다.
        return  ResponseEntity.status(204).build();
    }

    // [5] 최근 24시간 로그인한 접속자 수 조회
    @GetMapping("/login/count")
    public ResponseEntity<Integer> loginCount(){
        System.out.println("MemberController.loginCount");
        System.out.println();

        int result = memberService.loginCount();

        // 200 : 요청 성공
        return  ResponseEntity.status(200).body(hashCode());
    }
}
