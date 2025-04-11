package task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.model.dto.ReviewDto;
import task.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/task/review")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    // [1] 리뷰 등록
    @PostMapping
    public ReviewDto saveReview (@RequestBody ReviewDto reviewDto){
        System.out.println("ReviewController.saveReview");
        System.out.println("reviewDto = " + reviewDto);

        return reviewService.saveReview(reviewDto);
    }

    // [2] 리뷰삭제
    @DeleteMapping
    public boolean deleteReview (@RequestParam int rno , @RequestParam String rpwd){
        System.out.println("ReviewController.deleteReview");
        System.out.println("rno = " + rno + ", rpwd = " + rpwd);

        return  reviewService.deleteReview(rno, rpwd);
    }

    // [3] 책별 리뷰 조회
    @GetMapping
    public List<ReviewDto> findByBname(@RequestParam String bname){
        System.out.println("ReviewController.findByBno");
        System.out.println("bname = " + bname);

        return reviewService.findByBname(bname);
    }
}
