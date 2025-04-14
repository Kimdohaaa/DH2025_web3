package app.service;

import app.model.dto.MemberDto;
import app.model.entity.MemberEntity;
import app.model.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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


}
