package app.util;

// *** JWT 토큰 생성 / 검증 을 위한 클래스 *** //

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component // Spring 컨테이너에 빈 등록 어노테이션
public class JwtUtil {

    // 비밀키 생성 시 주로 사용하는 알고리즘 : HS256알고리즘 / HS512알고리즘 //
    // [*] 비밀키 변수 생성(개발자가 임의로 지정한 키 사용) : private String 변수명 = "인코딩된 HS512 비트키";
    // private String securityKey = "qP9sLxV3tRzWn8vMbKjUyHdGcTfEeXcZwAoLpNjMqRsTuVyBxCmZkYhGjFlDnEpQzFgXt9pMwX8Sx7CtQ5VtBvKmA2QwE3D";
    // [*] 비밀키 변수 생성(라이브러리를 이용) : private Key securityKey = Keys.secretKeyFor(SignatureAlgorithm.알고리즘명);
    private Key securityKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // [*] Redis 객체 선언 (Redis 를 조작하기 위한 객체)
    @Autowired
    private StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();


    // [1] JWT 토큰 발급
    // 1) 사용자의 이메일을 매개변수로 받기
    public String createToken(String email) {
        // 2) 토큰 처리 후 변수에 저장
        String token = Jwts.builder()
                // [*] .setSubject(토큰에 넣을 내용물) : 토큰에 넣을 내용물 지정
                .setSubject(email) // 로그인 성공한 회원의 이메일
                // [*] .setIssuedAt(토큰이 발급된 날짜) : 토큰 발급날짜 지정
                .setIssuedAt(new Date()) // new Date() : 자바에서 제공하는 현재 날짜 클래스
                // [*] .setExpiration(토큰 만료 시간(밀리초단위)) : 토큰 만료시간 지정
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24))) // 현재 시간을 밀리초로 가져온 후 + 만료시간
                // [*] .signWith(지정할 비밀키) : 지정한 비밀키를 암호화
                .signWith(securityKey)
                // [*] .compact() : 위 정보를 기준으로 JWT 토큰을 생성하여 return
                .compact();

        // + 중복 로그인 방지를 위해 웹서버가 아닌 Redis 에 토큰 저장 + //
        // [*] Redis 에 토큰 저장하기
        // (#) Redis 객체에 토큰 저장 : 레디스객체명.opsForValue().set(키,값, 생명주기값, TimeUnit.XXX);
        stringRedisTemplate.opsForValue().set("JWT:"+email, token, 24 , TimeUnit.HOURS);
        // (#) 확인 : 레디스객체명.keys("*") => 현재 redis 에 저장된 모든 key 반환
        System.out.println(stringRedisTemplate.keys("*"));
        // (#) 확인 : 레디스객체명.opsForValue().get("JWT"+email) => 현재 redis 에 저장된 특정 key 반환
        System.out.println(stringRedisTemplate.opsForValue().get("JWT"+email));


        return  token;
    }

    // [2] JWT 토큰 검증
    public String validateToken(String token){
        try{
            // 검증 성공시
            // Jwts.parser() : JWT 토큰 검증 메소드
            Claims claims = Jwts.parser()
            // .setSigningKey(검증하기 위한 비밀키)
            .setSigningKey(securityKey)
            // .build() : 검증 실행 -> 실패 시 예외 발생
            .build()
            // .parseClaimsJws(토큰) : 검증할 토큰 해석 -> 실패 시 예외 발생
            .parseClaimsJws(token)
            // .getBody() : 검증된 claims 객체 생성 후 반환 -> claims 객체 안에는 다양한 토큰 정보가 들어있음
            .getBody();

            // .getSubject() : 검증된 토큰 객체의 subject(내용물) 반환
            System.out.println(claims.getSubject());

            // + 중복 로그인 방지를 위해 Redis 에서 최근 로그인 토큰 확인 + //
            // (#) 현재 전달받은 토큰에 저장된 회원정보(이메일)
            String email = claims.getSubject();
            // (#) 레디스에서 최신 토큰 가져오기
            String redisToken = stringRedisTemplate.opsForValue().get("JWT:"+email);
            // (#) 현재 전달받은 토큰과 레디스에 저장된 토큰과 검증
            if(token.equals(redisToken)){
                return email; // 검증 완료 시 로그인 상태 정상(중복 로그인이 아님)
            }else {
                System.out.println(">> 중복 로그인 /// 이전 토큰 만료");
            }
            // (#) 다르면(토큰이 불일치 또는 중복 로그인 감지) 메소드 하단에 null 반환

        }catch (ExpiredJwtException e){ // ExpiredJwtException : 토큰이 만료되었을 때 예외 클래스
            System.out.println(">> JWT 토큰 기한 만료 : "+ e);
        }catch (JwtException e){ // JwtException : 그 외 모든 토큰 예외 클래스
            System.out.println(">> JWT 예외 : " + e);
        }

        // 유효하지 않은 토큰 또는 오류 발생 시 null 반환
        return  null;
    }

    // [3] 로그아웃 시 Redis 에 저장된 토큰 삭제
    public void  deleteToken (String email) {

        stringRedisTemplate.delete(email);
    }
}
