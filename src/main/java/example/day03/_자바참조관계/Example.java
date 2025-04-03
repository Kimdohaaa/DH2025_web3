package example.day03._자바참조관계;

import java.util.ArrayList;

public class Example {
    // 참조 : 실제 인스턴스(객체)의 위치(주소값)를 참조
    // .(도트 연산자)를 통해 복합참조(참조의 참조의 참조 ...) 가능

    public static void main(String[] args) {
        // [1] 인스턴스 생성 후 참조
        // => new Category() : 인스턴스 생성
        // => Category category : 생성된 인스턴스를 변수가 참조
        Category category = new Category();

        // [2] 복합참조
        // => category 변수가 참조하는 인스턴스는 Category 클래스의 두개의 멤버변수 참조가능
        // => category 변수의 참조 횟수 : 3개 (Category/cno/cname)
        category.setCno(1);
        category.setCname("공지사항");

        System.out.println(category.getCno());
        System.out.println(category.getCname());

        // [3] Category 를 참조하는 Board 인스턴스 생성 (Builder 패턴 사용)
        // => board 변수의 참조 횟수 : 5(Board/bno/bname/bcontent/Category) + category 변수의 Category 멤버변수 참조 2개 = 총 7 개
        Board board  = Board.builder()
                .bno(1)
                .bname("안녕")
                .bcontent("안녕~~~")
                .category(category)
                .build();

        System.out.println(board.getBno());
        System.out.println(board.getBname());
        System.out.println(board.getBcontent());
        System.out.println(board.getCategory().getCno()); // Board -참조-> Category -참조-> cno
        System.out.println(board.getCategory().getCname()); // Board -참조-> Category -참조-> cname

        // [4] "공지사항" 1번 게시물에 댓글 등록 (Builder 패턴 사용)
        // => reply 변수의 참조 횟수 : 10 개(Reply/rno/rcontent/Board/bno/bname/bcontent/Category/cno/cname)
        Reply reply = Reply.builder()
                .rno(1)
                .rcontent("댓글임")
                .board(board) // 1 번 댓글에 1번 게시물 인스턴스를 대잉ㅂ
                .build();

        System.out.println(reply.getRno());
        System.out.println(reply.getRcontent());
        System.out.println(reply.getBoard().getBno());
        System.out.println(reply.getBoard().getBname());
        System.out.println(reply.getBoard().getBcontent());
        System.out.println(reply.getBoard().getCategory().getCno());
        System.out.println(reply.getBoard().getCategory().getCname());

        // Reply 에서 Board 와 Category 조회 [O]
        // Board 에서 Category 조회 [O]
        // Category 에서 Board 와 Reply 조회 [X]
        // Board 에서 Reply 조회 [X]
        // => 단방향이기 때문


        // [5] 양방향 (DB 에서는 단방향만 사용하지만 Java 에서는 양방향도 가능)
        // ★ 순환 참조 주의 : 서로 참조만 계속 하는 무한 루프에 빠지는 것 ★
        // => Category 를 통해 Board 와 Reply 조회 가능
        // => Board 에서 Reply 조회 가능
        category.setBoardList(new ArrayList<>()); // 메모리 할당
        category.getBoardList().add(board); // Category 에 Board 추가
        board.setReplyList(new ArrayList<>()); // 메모리 할당
        board.getReplyList().add(reply); // Board 에 Reply 추가

        System.out.println(category.getBoardList().get(0).getReplyList().get(0)); // 순환참조 발생 : StackOverFlowError 발생
                                                                    // StackOverFlowError : JVM 의 메모리 공간이 OverFlow 됨

        // ★ 해결법 : 양방향 중 한쪽 방향에서만 toString 을 사용하고 반대 방향에서는 @ToString.Exclude 어노테이션을 통해 toString 을 사용하지 않기 ★ //


    }

}
