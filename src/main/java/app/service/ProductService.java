package app.service;

import app.model.dto.CategoryDto;
import app.model.dto.ProductDto;
import app.model.entity.CategoryEntity;
import app.model.entity.ImgEntity;
import app.model.entity.MemberEntity;
import app.model.entity.ProductEntity;
import app.model.repository.CategoryRepository;
import app.model.repository.ImgRepository;
import app.model.repository.MemberRepository;
import app.model.repository.ProductRepository;
import app.util.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private  final ProductRepository productRepository;
    private  final  MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ImgRepository imgRepository;

    private  final FileUtil fileUtil;

    // [1] 제품 등록
    public  boolean registerProduct(ProductDto productDto,int loginMno){
        // 1) 현재 회원번호의 엔티티 조회(FK)
        // Optional : null 값 제어 기능 제공
        Optional<MemberEntity> memberEntity = memberRepository.findById(loginMno);
        if(memberEntity.isEmpty()){ // 조회된 결과가 없으면 false 반환
            return  false;
        }

        // 2) 현재 카테고리번호의 엔티티 조회(FK)
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(productDto.getCno());
        if(categoryEntity.isEmpty()){ // 조회된 결과가 없으면 false 반환
            return  false;
        }

        // 3) ProductDto 를 ProductEntity 로 변환
        ProductEntity productEntity = productDto.toEntity();

        // 4) 단방향 관계 주입
        productEntity.setCategoryEntity(categoryEntity.get());
        productEntity.setMemberEntity(memberEntity.get());

        // 5) 영속성 연결
        ProductEntity saveEntity = productRepository.save(productEntity);

        // 6) 실패 시 false 반환
        if(saveEntity.getPno() <= 0){
            return  false;
        }
        // 7) 파일 처리
        if(productDto.getFiles() != null & !productDto.getFiles().isEmpty()){ // 첨부파일이 존재하면
             // 7-1) 첨부파일 타입 List 로 여러개이므로 반복문 사용
            for(MultipartFile file : productDto.getFiles()){
                // 7-2) FileUtil 클래스의 업로드 메소드 호출하여 첨부파일 업로드
                String saveFileName = fileUtil.fileUpload(file);
                // 7-3) 만약 업로드 실패 시 트랜잭션 롤백
                if(saveFileName == null){
                    throw  new RuntimeException("파일 업로드 오류 발생"); // 강제 예외를 발생 시켜 롤백하기
                }
                // 7-4) 만약 업로드 성공 시 ImgEntity 생성 후 업로드 할 파일명 Setter
                ImgEntity imgEntity = ImgEntity.builder()
                        .iname(saveFileName)
                        .build();
                // 7-5) 단방향 관계 주입 (FK)
                imgEntity.setProductEntity(saveEntity);
                // 7-6) 영속성 연결
                imgRepository.save(imgEntity);

            }
        }

        return true;

    }

    // [2] 전체 조회
//    public List<ProductDto> allProducts(long cno){ // cno 가 필수가 아님을 지정){
//
//        List<ProductEntity> productEntityList; // 조회된 결과를 저장할 변수 선언
//        // 1) cno가 존재한다면 카테고리 별 조회
//        if(cno > 0 ){
//            productEntityList = productRepository.findByCategoryEntityCno(cno);
//        }
//        // 2) cno가 존재하지 않는다면 전체조회
//        else {
//            productEntityList = productRepository.findAll();
//        }
//
//        // 3) Entity -변환-> Dto 후 반환
//        return  productEntityList.stream()
//                .map(ProductDto::toDto)
//                .collect(Collectors.toList());
//    }

    // [3] 개별 조회
    public ProductDto viewProduct(long pno  ){// pno 가 필수임을 지정
        // 1) pno 에 해당하는 엔티티 조회
        Optional<ProductEntity> productEntity =productRepository.findById(pno);

        // 2) 만약 존재하지 않을 시 null 반환
        if(productEntity.isEmpty()){
            return  null;
        }
        // 3) 만약 존재할 시 엔티티 꺼내기
        ProductEntity findProductEntity = productEntity.get();

        // 4) 조회수 증가 (기존 조회 수 호출 후 + 1 한 값을 Setter)
        findProductEntity.setPview(findProductEntity.getPview() + 1);

        // 5) Entity -변환-> DTO 후 반환
        return  ProductDto.toDto(findProductEntity);
    }

    // [4] 제품 삭제 (해당 제품에 포함된 이미지도 함께 삭제)
    public boolean deleteProduct(long pno, int loginMno){
        // 1) pno 에 해당되는 엔티티 조회
        Optional<ProductEntity> productEntity = productRepository.findById(pno);

        // 2) 엔티티가 존재하지 않으면 false 반환
        if(productEntity.isEmpty()){
            return  false;
        }

        // 3) 조회된 엔티티 가져오기
        ProductEntity findProductEntity = productEntity.get();

        // 4) 엔티티가 존재하면 요청한 loginMno 가 등록한 제품인지 확인
        if(findProductEntity.getMemberEntity().getMno() != loginMno){
            return false;
        }

        // 5) 해당 제품에 업로드된 이미지 리스트 꺼내기
        List<ImgEntity> imgEntityList = productEntity.get().getImgEntityList();;

        // 6) 꺼낸 이미지 리스트 삭제
        for(ImgEntity imgEntity : imgEntityList){
            boolean result = fileUtil.fileDelete(imgEntity.getIname());

            // *) 만약 오류 발생 시 강제 예외 발생시키기
            if(result == false){
                throw  new RuntimeException("파일 삭제 중 오류 발생");
            }
        }

        // 7) 해당 제품 삭제
        productRepository.deleteById(pno);

        return true;

        // cascade 조건으로 CASCADE.ALL 이 지정되어 있기 때문에 이미지 엔티티는 자동 삭제됨 //

    }

    // [5] 제품 수정 + 이미지 추가
    public boolean updateProduct(ProductDto productDto, int loginMno){
        System.out.println("ProductService.updateProduct");
        System.out.println("productDto = " + productDto + ", loginMno = " + loginMno);

        // 1) 수정할 제품 엔티티 조회
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productDto.getPno());
            // null 은 참조불가 -> .(참조연산자) 사용 시 nullPointException 오류 발생
            // Optional 타입은 null 지원 -> .isEmpty() 를 통해 null 검사 가능
        if(productEntityOptional.isEmpty()){
            return false;
        }
        ProductEntity productEntity = productEntityOptional.get();

        // 2) 현재 로그인한 사람이 등록한 제품인지 유효성 검사
        if(productEntity.getMemberEntity().getMno() != loginMno){
            return false;
        }

        // 3) 수정할 카테고리 엔티티 조회
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(productDto.getCno());
        if(categoryEntityOptional.isEmpty()){
            return false;
        }
        CategoryEntity categoryEntity = categoryEntityOptional.get();

        // 4) 제품정보를 수정
            // 조회한 제품정보(productEntity) 에 Setter 을 통해 수정
        productEntity.setPname(productDto.getPname());
        productEntity.setPcontent(productDto.getPcontent());
        productEntity.setPprcie(productDto.getPprcie());
        productEntity.setCategoryEntity(categoryEntity); // 찾은 카테고리엔티티 Setter

        // 5) 새로운 이미지 존재 시 FileUtil 에서 업로드 메소드를 이용하여 업로드
        List<MultipartFile> newFile = productDto.getFiles();
        if(newFile != null && !newFile.isEmpty()){ // 새로운 이미지가 존재하면
            for(MultipartFile file : newFile){
                String saveFileName = fileUtil.fileUpload(file);
                // 예외 발생 시 롤백
                if(saveFileName == null){
                    throw  new RuntimeException("파일 업로드 오류 발생");
                }
                // 새 이미지 처리
                ImgEntity imgEntity = ImgEntity.builder()
                        .iname(saveFileName) // 업로드된 파일명
                        .productEntity(productEntity)
                        .build();
                // 영속성 부여
                imgRepository.save(imgEntity);
            }
        }

        return  true;
    }

    // [6] 이미지 개별 삭제
    public boolean deleteImage(long ino , int loginMno){
        System.out.println("ProductService.deleteImage");
        System.out.println("ino = " + ino + ", loginMno = " + loginMno);

        // 1) 이미지 엔티티 조회
        Optional<ImgEntity> optionalImgEntity = imgRepository.findById(ino);
        if(optionalImgEntity.isEmpty()){
            return  false;
        }
        ImgEntity imgEntity = optionalImgEntity.get();

        // 2) 검증
        if(imgEntity.getProductEntity().getMemberEntity().getMno() != loginMno){
            return  false;
        }

        String deleteFileName = imgEntity.getIname();
        boolean result = fileUtil.fileDelete(deleteFileName);
        if(result == false){
            throw new RuntimeException("이미지 삭제 오류");
        }

        imgRepository.deleteById(ino);

        return true;
    }
    // [7] 카테고리 조회
    public List<CategoryDto> getCategory(){
        System.out.println("ProductService.getCategory");

        // 1) 전체 카테고리 엔티티 조회
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        // 2) Entity -변환-> DTO
        List<CategoryDto> categoryDtos = categoryRepository.findAll().stream()
                .map(CategoryDto::toDto)
                .collect(Collectors.toList());

        return categoryDtos;
    }
    // [8] 검색 + 페이징 처리 기능 ([2] 조회 기능 업그레이드 버전)
    public Page<ProductDto> allProduct( Long cno , int page , int size , String keyword ){
        System.out.println("ProductService.allProduct");
        System.out.println("cno = " + cno + ", page = " + page + ", size = " + size + ", keyword = " + keyword);

        // 1) 페이징 처리 설정 (~.domain 으로 import)
        Pageable pageable = PageRequest.of( page-1 , size , Sort.by(  Sort.Direction.DESC , "pno")  );
            // Pageable : 인터페이스
            // PageRequest : 클래스(구현체)
                // .of(페이지번호(0부터), 페이지 별 자료 수 , 정렬(생략가능))

        // 2) 엔티티 조회
        Page<ProductEntity> productEntities = productRepository.findBySearch( cno , keyword , pageable );
        // 3) Entity -변환-> DTO
        Page<ProductDto> productDtoList = productEntities.map(ProductDto::toDto);
        return productDtoList;
    }
}
