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
        CourseEntity entity = courseDto.toEntity();

        courseRepository.save(entity);

        return  true;
    }

    // [2] 과정 전체 조회
    public List<CourseDto> get1(){
        List<CourseEntity> entityList = courseRepository.findAll();

        return entityList.stream()
                .map(CourseEntity::toDto)
                .collect(Collectors.toList());
    }

    // [3] 특정 과정에 수강생 등록
    public boolean post2(StudentDto studentDto){
       // DTO -> Entity
        StudentEntity studentEntity = StudentEntity.builder()
                .sno(studentDto.getSno())
                .sname(studentDto.getSname())
                .cno(studentDto.getCno())
                .build();

        CourseEntity course = courseRepository.findById(studentDto.getCno())
                .orElse(null);

        studentEntity.setCourseEntity(course);

        studentRepository.save(studentEntity);

        return true;


    }

    // [4] 특정 과정에 수강생 전체 조회
    public List<StudentDto> get2(int cno){

        List<StudentEntity> entityList = studentRepository.findAll();

        List<StudentEntity> result1 = new ArrayList<>();

        for (StudentEntity student : entityList) {
            // CourseEntity가 null이 아니고, cno가 일치하는 경우만 추가
            if (student.getCourseEntity() != null && student.getCourseEntity().getCno() == cno) {
                result1.add(student);
            }
        }


        List<StudentDto> sList = result1.stream()
                .map(StudentEntity::toDto)
                .collect(Collectors.toList());

        return sList;

    }
}
