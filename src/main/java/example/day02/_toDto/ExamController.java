package example.day02._toDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/day02/todto")
public class ExamController {

    private final ExamService examService;


    // [1] 등록
    @PostMapping
    public boolean post(@RequestBody ExamDto examDto){ // Entity 가 아닌 DTO 또는 Map 으로 받기
        return examService.post(examDto);
    }

    // [2] 전체 조회
    @GetMapping
    public List<ExamDto> get(){
        System.out.println("ExamController.get");
        System.out.println();

        return examService.get();
    }
}
