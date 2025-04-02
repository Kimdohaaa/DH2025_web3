package example.day02._영속성;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    // 영속성 매니저 선언 //
    @PersistenceContext // 영속성 컨텍스트의 객체를 주입 받는 어노테이션
    private EntityManager entityManager;

    public void get(){
        System.out.println("ExamService.get");

        // 1) 비영속 상태(Transient) : 단순한 자바객체(DB와 매핑되지 않은 상태)
        ExamEntity examEntity1 = new ExamEntity(); // 비영속 상태
        examEntity1.setName("유재석"); // 비영속 상태이기 때문에 DB 에 레코드가 추가되지 않음
        System.out.println(">> 비영속 상태 : " + examEntity1);

        // 3) 영속 상태(Persistent) : 자바 객체와 DB 가 매핑된 상태
        // 영속성매니저객체명.persist(객체) : 해당 객체에 영속상태 부여
        // => 일반적으로는 .persist() 메소드 대신 .save() 를 더 많이 사용함
        entityManager.persist(examEntity1); // 영속 상태 부여
        System.out.println(">> 영속 상태 : " + examEntity1); // 영속 상태이기 때문에 DB 에 레코드가 추가됨
        // * 영속된 상태에서의 수정 * //
        examEntity1.setName("강호동"); // 수정
        entityManager.flush(); // 영속성매니저객체명.flush() :트랜잭션 중간에 commit(완료) 하는 메소드 (테스트 용)
        System.out.println(">> 영속 상태에서의 수정 : " + examEntity1); // 자바객체와 DB 모두 수정됨 (영속상태이기 때문)

        // 3) 준영속 상태(Detached) : 영속 상태를 해제하는 것(레코드는 사라지지 않음)
        // 영속성매니저객체명.detach(객체) : 해당 객체의 영속상태를 해제함
        entityManager.detach(examEntity1); // 영속 상태를 해제
        System.out.println(">> 준영속 상태 : " + examEntity1); // 영속이 해제된 상태이기 때문에 DB 에 레코드가 추가되지 않음
        // * 준영속 상태에서의 수정 * //
        examEntity1.setName("신동엽"); // 수정
        System.out.println(">> 준영속 상태에서의 수정 : " + examEntity1); // 자바객체만 수정되고 DB 는 수정되지 않음 (준영속 상태이기 때문에)

        // 4) 삭제 상태(remove) : 영속 삭제 (레코드도 사라짐)
        // 영속성매니저객체명.remove(객체) : 해당 객체의 영속을 삭제함
//        entityManager.remove(examEntity1); // 영속 삭제
//        System.out.println(">> 삭제 상태 : " + examEntity1);
//
        // !!! 준영속 상태에서는 삭제 불가 !!! //
    }

}
