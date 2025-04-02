package example.day02._영속성;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/day02/persis")
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public void get(){
        System.out.println("ExamController.get");

        examService.get();
    }

}
