package app.service;

import app.model.dto.MemberDto;
import app.model.entity.MemberEntity;
import app.model.repository.MemberRepository;
import app.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // [1] 회원가입
    public MemberDto signup(MemberDto memberDto){

        System.out.println("MemberService.signup");
        System.out.println("memberDto = " + memberDto);

        // 암호화를 위해 비클립트 객체 생성
        BCryptPasswordEncoder bPwd = new BCryptPasswordEncoder();
        // 입력받은 비밀번호를 비클립트 객체를 통해 암호화
        String hashPwd = bPwd.encode(memberDto.getPwd());
        // Dto 에 Setter
        memberDto.setPwd(hashPwd);

        MemberEntity memberEntity = memberDto.toEntity();
        MemberEntity saveEntity = memberRepository.save(memberEntity);

        if(saveEntity.getMno() > 0){
            return saveEntity.toDto();
        }
        return  null;
    }

    // [*] 토큰 발급을 위해 JWT 객체 주입 받기
    private  final JwtUtil jwtUtil;

    // [2] 로그인
    public String login(MemberDto memberDto){
        System.out.println("MemberService.login");
        System.out.println("memberDto = " + memberDto);

        // 1) email 을 DB 에서 조회하여 해당하는 엔티티 찾기
        MemberEntity memberEntity = memberRepository.findByEmail(memberDto.getEmail());

        // 2) 엔티티가 존재하지 않을 시
        if(memberEntity == null){
            return  null; // 로그인 실패;
        }

        // 3) 조회된 엔티티의 비밀번호 검증
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 비클립트 객체 생성
        boolean inMatches =
            passwordEncoder.matches(memberDto.getPwd(), memberEntity.getPwd()); // .matches(입력받은데이터, 암호된데이터) : 비밀번호 검증

        // 4) 비밀번호 검증이 실패 일 시
        if(inMatches == false){
            return  null; // 로그인 실패
        }

        // 5) 비밀번호 검증이 성공일 시 토큰 발급
        String token = jwtUtil.createToken(memberEntity.getEmail()); // 로그인 성공한 이메일을 매개변수로 전달
        System.out.println(">> 발급된 토큰 : " + token);

        // 6) 토큰 반환
        return  token;
    }

    // [3] 로그인된 회원 인증 / 내정보 조회
    // 전달받은 token 으로 검증 후 유효할 경우 token 에 해당하는 회원정보 반화 , 유효하지 않으면 null 반환
    public MemberDto info(String token){
        System.out.println("MemberService.info");
        System.out.println("token = " + token);

        // 1) 토큰 검증
        String email = jwtUtil.validateToken(token);

        // 2) 검증 실패 시(비로그인중 / 유효기간 만료 시) null 반환
        if(email == null){
            return  null;

        }
        // 3) 검증 성공 시 토큰에 저장된 이메일을 통해 엔티티 조회
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        // 4) 조회된 엔티티가 없으면 null 반환
        if(memberEntity == null){
            return  null;
        }
        // 5) 조회 성공 시 조회된 엔티티를 DTO 로 변환하여 반환
        return  memberEntity.toDto();
    }

}
