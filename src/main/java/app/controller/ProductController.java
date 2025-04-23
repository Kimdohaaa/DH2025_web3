package app.controller;

import app.model.dto.ProductDto;
import app.model.entity.ProductEntity;
import app.service.MemberService;
import app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("")
public class ProductController {
    private final ProductService productService;
    private final MemberService memberService;

    // [1] 제품 등록
    @PostMapping("/register")
    public ResponseEntity<Boolean> registerProduct(
            @RequestHeader("Authorization") String token,
            @ModelAttribute  ProductDto productDto // multipart/form(첨부파일) 을 받기 위해 @ModelAttribute 어노테이션 사용
    ){
        System.out.println("ProductController.registerProduct");
        System.out.println("token = " + token + ", productDto = " + productDto);

        // 1) 현재 토큰의 회원번호 구하기
        int loginMno; // 전역 변수로 사용
        try { // 토큰 오류 시 예외처리
            loginMno = memberService.info(token).getMno();
            System.out.println("로그인된 번호 : " + loginMno);
        }catch (Exception e){
            return ResponseEntity.status(401).body(false); // 401 : 인증 실패
        }
        // 2) 회원번호 존재 시 Service로 이동
        boolean result = productService.registerProduct(productDto, loginMno);

        // 4) 결과에 따른 반환
        if(result == false){
            return ResponseEntity.status(400).body(false); // 400(Bad Request) : 잘못된 요청
        }
        return ResponseEntity.status(200).body(true); // 200(OK) : 요청 성공
    }

    // [2] 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> allProducts(
            @RequestParam(required = false) long cno // cno 가 필수가 아님을 지정
    ){
        System.out.println("ProductController.allProducts");
        System.out.println("cno = " + cno);

        List<ProductDto> productDtoList = productService.allProducts(cno);

        return ResponseEntity.status(200).body(productDtoList);
    }

    // [3] 개별 조회
    @GetMapping("/view")
    public  ResponseEntity<ProductDto> viewProduct(
            @RequestParam(required = true) long pno // pno 가 필수임을 지정
    ){
        System.out.println("ProductController.viewProduct");
        System.out.println("pno = " + pno);

        ProductDto productDto = productService.viewProduct(pno);

        if(productDto == null){
            return  ResponseEntity.status(404).body(null); // 404(not found) : 값이 없음
        }

        return ResponseEntity.status(200).body(productDto);
    }

    // [4] 제품 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> deleteProduct(
            @RequestHeader("Authorization") String token, // 검증을 위한 토큰
            @RequestParam long pno){
        System.out.println("ProductController.deleteProduct");
        System.out.println("token = " + token + ", pno = " + pno);

        // 1) 토큰을 통해 권한 확인
        int loginMno;
        try {
            loginMno = memberService.info(token).getMno() ;
        }catch (Exception e){
            return  ResponseEntity.status(401).body(false); // 401 : 권한없음
        }

        // 2) 권한이 있을 경우 Service 에서 삭제 처리
        boolean result = productService.deleteProduct(pno, loginMno); // 작성자인지 확인을 위해 loginMno 전달

        if(result == false){
            return  ResponseEntity.status(400).body(false); // 400 : Bad Request
        }

        return  ResponseEntity.status(200).body(true); // 200 : OK
    }
}
