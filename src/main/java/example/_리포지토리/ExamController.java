package example._리포지토리;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day01/jpa")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    // [1] 등록
    @PostMapping
    public boolean post(@RequestBody ExamEntity entity){
        // Controller 의 ExamEntity 는 영속되기 전 상태
        return examService.post(entity);
    }

    // [2] 전체 조회
    @GetMapping
    public List<ExamEntity> get(){
        return examService.get();
    }

    // [3] 수정
    @PutMapping
    public boolean put(@RequestBody ExamEntity entity){
        return examService.put(entity);
    }

    // [4] 삭제
    @DeleteMapping
    public boolean delete(@RequestParam String id){
        return examService.delete(id);
    }

}
