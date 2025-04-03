package example.day03._day03과제;

import example.day03._day03과제.dto.CourseDto;
import example.day03._day03과제.dto.StudentDto;
import example.day03._day03과제.entity.CourseEntity;
import example.day03._day03과제.entity.StudentEntity;
import example.day03._day03과제.repository.CourseRepository;
import example.day03._day03과제.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final CourseRepository courseRepository;
    private  final StudentRepository studentRepository;


    // [1] 과정 등록
    public boolean post1(CourseDto courseDto){
        // 1) DTO -변환-> Entity
        CourseEntity entity = courseDto.toEntity();

        // 2) 영속성 부여
        CourseEntity saveEntity = courseRepository.save(entity);

        // 3) 결과확인 (.save() 메소드의 반환값이 영속된 객체이기 때문에 cno 를 통해 확인)
        if(saveEntity.getCno() > 0){
            return true;
        }

        return  false;
    }

    // [2] 과정 전체 조회
    public List<CourseDto> get1(){
        // 1) .findAll() 을 통해 영속된 모든 객체 가져오기
        List<CourseEntity> entityList = courseRepository.findAll();

        // 2) toDto() 메소드를 통해 Entity -변환-> DTO 하여 반환
        return entityList.stream()
                .map(CourseEntity::toDto)
                .collect(Collectors.toList());
    }

    // [3] 특정 과정에 수강생 등록
    public boolean post2(StudentDto studentDto){
        // 1) @Builder 어노테이션을 통해 입력받은 정보를 DTO -변환-> Entity
        StudentEntity studentEntity = StudentEntity.builder()
                .sno(studentDto.getSno())
                .sname(studentDto.getSname())
                .build();

        // 2) 등록할 CourseEntity 조회
        CourseEntity course = courseRepository.findById(studentDto.getCno())
                .orElse(null);

        if(course == null){
            return  false;
        }

        // 3) 조회한 CourseEntity 를 StudentEntity 에 대입 (FK 키 대입)
        studentEntity.setCourseEntity(course);

        // 4) 영속성 부여
        StudentEntity saveEntity = studentRepository.save(studentEntity);

        // 5) 결과확인
        if(saveEntity.getSno() < 1){
            return false;
        }

        return true;


    }

    // [4] 특정 과정의 수강생 전체 조회
    public List<StudentDto> get2(int cno){

        // 1) StudentEntity 전체 조회
        List<StudentEntity> entityList = studentRepository.findAll();

        // 2) 특정 과정의 수강 중인 StudentEntity 를 담을 List 선언
        List<StudentEntity> result1 = new ArrayList<>();

        // 3) 전체 StudentEntity 를 순회하여 특정 cno 만 조회 후 add
        for (StudentEntity student : entityList) {
            if (student.getCourseEntity() != null && student.getCourseEntity().getCno() == cno) {
                result1.add(student);
            }
        }

        // 4) toDto() 를 통해 Entity -변환-> DTO
        List<StudentDto> sList = result1.stream()
                .map(StudentEntity::toDto)
                .collect(Collectors.toList());

        // 5) DTO 반환
        return sList;

    }
}
