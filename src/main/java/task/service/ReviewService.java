package task.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import task.model.dto.BookDto;
import task.model.dto.ReviewDto;
import task.model.entity.BookEntity;
import task.model.entity.ReviewEntity;
import task.model.repository.BookRepository;
import task.model.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private  final ReviewRepository reviewRepository;
    private  final  BookService bookService;
    // [1] 리뷰 등록
    public ReviewDto saveReview ( ReviewDto reviewDto){
        System.out.println("ReviewService.saveReview");
        System.out.println("reviewDto = " + reviewDto);

        int bno = reviewDto.getBno();
        BookDto bookDto = bookService.findById(bno);

        reviewDto.setBname(bookDto.getBname());

        ReviewEntity reviewEntity = reviewDto.toEntity();

        ReviewEntity saveEntity = reviewRepository.save(reviewEntity);

        if(saveEntity.getRno() > 0){
            return  saveEntity.toDto();
        }

        return  null;
    }

    // [2] 리뷰삭제
    public boolean deleteReview ( int rno , String rpwd){
        System.out.println("ReviewService.deleteReview");
        System.out.println("rno = " + rno + ", rpwd = " + rpwd);

        int result = reviewRepository.deleteReview(rno, rpwd);

        if(result > 0){
            return  true;
        }
        return  false;
    }

    // [3] 책 추천 별 리뷰 조회
    public List<ReviewDto> findByBname(String bname){
        List<ReviewEntity> reviewEntities = reviewRepository.findByBname(bname);

        return reviewEntities.stream()
                .map(ReviewEntity::toDto)
                .collect(Collectors.toList());
    }
}
