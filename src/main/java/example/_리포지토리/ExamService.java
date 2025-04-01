package example._리포지토리;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    // * 조작할 엔티티 리포지토리 인터페이스 //
    private final ExamEntityRepository examEntityRepository;

    // [1] 등록
    public boolean post(ExamEntity entity) {
        System.out.println("entity = " + entity);
        System.out.println("ExamService.post");

        entity.setKor(5); // 영속적이기 때문에 값을 바꿔도 변화 X

        // 인터페이스명.save(저장할 엔티티 객체) : 존재하는 PK면 insert , 존재하지 않는 PK 는 update 됨 //
        ExamEntity 영속된엔티티 = examEntityRepository.save(entity);

        영속된엔티티.setKor(10); // 영속된 상태이기 때문에 바꾼 값이 DB 에 적용
        examEntityRepository.save(영속된엔티티);


        return true;
    }

    // [2] 전체 조회
    public List<ExamEntity> get() {
        return examEntityRepository.findAll();
    }

    // [3] 수정
    public boolean put(ExamEntity entity) {
        examEntityRepository.save(entity);

        return true;
        // 존재하지 않는 PK 일 시 무조건 insert 되는 문제점 발생 [*] 방식으로 해결 //
    }

    // [*] 일반적인 수정방법 : 존재하는 ID 를 조회 후 수정
    @Transactional // 아래 메소드 중 하나라도 SQL 문제 발생 시 전체 취소
    public boolean put2(ExamEntity entity) {
        // [1] 아이디에 해당하는 엔티티 찾기 ★ .findById() 의 반환타입은 Optional ★
        Optional<ExamEntity> optionalExamEntity =
                examEntityRepository.findById(entity.getId());

        // [2] 만약 조회한 엔티티가 있으면
        if (optionalExamEntity.isPresent()) { // .isPresent() : Optional에 내용물(엔티티) 존재여부 판단
            // [3] Optional 객체에서 영속된 엔티티 꺼내기
            ExamEntity obj = optionalExamEntity.get(); // .get() : Optional 객체에 엔티티 꺼내기

            // [4] Setter 을 통해 수정
            obj.setName(entity.getName());
            obj.setKor(entity.getKor());
            obj.setEng(entity.getEng());

            return  true;
        }
         return  false;
    }

    // [4] 삭제
    public boolean delete(String id){
        examEntityRepository.deleteById(id);

        return true;
    }

}
