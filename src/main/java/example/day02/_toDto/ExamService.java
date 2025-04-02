package example.day02._toDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamService {

    private final ExamEntity1Repository examEntity1Repository;

    // [1] 등록 : DTO -변환-> Entity 하여 영속성 부여하기
    public boolean post(ExamDto examDto){
        // 1) DTO 를 Entity 로 변환하기
        // => DTO 에는 영속성(객체 <-> DB 매핑상태) 부여 불가하기 때문에
        ExamEntity1 examEntity1 = examDto.toEntity();

        // 2) 영속성 부여
        examEntity1Repository.save(examEntity1);

        return true;

    }

    // [2] 전체 조회 : Entity -변환-> DTO 하여 Controller 로 반환하기
    public List<ExamDto> get(){ // DTO 를 반환할거기 때문에 List 타입은 Entity 가 아닌 DTO
        // 1) 영속된 엔티티 가져오기
        List<ExamEntity1> entityList = examEntity1Repository.findAll();

        // 2) 가져온 엔티티를 DTO 로 변환하여 Controller 로 반환하기
        return entityList.stream() // 스트림 생성
                .map(ExamEntity1::toDto) // 메소드 레퍼런스를 통해 Entity 클래스에 있는 DTO 변환 메소드 사용
                .collect(Collectors.toList()); // 리스트 형식으로 반환
    }

}
