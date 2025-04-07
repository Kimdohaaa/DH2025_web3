package example.day04.day04.service;

import example.day04.day04.model.dto.TodoDto;
import example.day04.day04.model.entity.TodoEntity;
import example.day04.day04.model.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    // [1] 등록
    public TodoDto todoSave(TodoDto todoDto){
        System.out.println("TodoService.todoSave");
        System.out.println("todoDto = " + todoDto);

        // 1) TodoDto -변환-> TodoEntity (영속 전 Entity)
        TodoEntity todoEntity = todoDto.toEntity();

        // 2) 해당 Entity 에 영속성 부여 (DB 와 매핑) 후 영속화된 Entity 반환받기
        TodoEntity saveEntity = todoRepository.save(todoEntity);

        // 3) 존재 여부 확인 후 반환
        if(saveEntity.getId() > 1){ // 영속된 객체가 존재 시
            return saveEntity.toDto(); // TodoEntity -변환-> TodoDto 한 뒤 반환
        }else { // 영속된 객체가 존재하지 않을 시
            return  null; // null 반환
        }
    }

    // [2] 전체 조회
    public List<TodoDto> todoFindAll(){
        System.out.println("TodoService.todoFindAll");

        // 1) 영속된 모든 Entity 조회
        List<TodoEntity> todoEntityList = todoRepository.findAll();

        // 2) TodoEntity -변환-> TodoDto (스트림 사용 X 버전)
        List<TodoDto> todoDtoList = new ArrayList<>();

        for(int i = 0; i < todoEntityList.size(); i++){
            TodoDto todoDto = todoEntityList.get(i).toDto();

            todoDtoList.add(todoDto);
        }
        return todoDtoList;

        // *** 스트림 사용 시 *** //
        // return todoRepository.findAll().stream()
        //      .map(TodoEntity::toDto)
        //      .collect(Collectors.toList());
    }

    // [3] 개별 조회
    public TodoDto todoFindById(int id){
        System.out.println("TodoService.todoFindById");
        System.out.println("id = " + id);

        // 1) 영속된 엔티티 중 PK 키가 매개변수로 받은 id 와 같은 엔티티 조회
        // Optional 클래스 : null 을 제어하는 메소드를 제공하는 클래스 (nullPointException 오류 방지)
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(id);

        // 2) 존재 여부 확인
        if(optionalTodoEntity.isPresent()){ // isPresent() : 조회한 결과가 존재하면
            // 3) 해당 엔티티 꺼내기
            TodoEntity todoEntity = optionalTodoEntity.get();

            // 4) Entity -변환-> DTO
            TodoDto todoDto = todoEntity.toDto();

            // 5) 반환
            return todoDto;
        }

        return  null; // 없으면 null 반환

        // *** 스트림 사용 시 *** //
        // return todoRepository.findById(id) // 매개변수로 받은 id와 일치하는 PK 키 조회
        //            .map(TodoEntity::toDto) // 만약 존재하면 Entity -변환-> DTO
        //            .orElse(null); // 존재하지 않으면 null 반환
    }

    // [4] 수정 (트랜잭션 필수 (Service 클래스 위에 주입함)
    public TodoDto todoUpdate(TodoDto todoDto) {
        System.out.println("TodoService.todoUpdate");
        System.out.println("todoDto = " + todoDto);

        // 1) 수정할 엔티티 조회
        Optional<TodoEntity> optional = todoRepository.findById(todoDto.getId());

        // 2) 존재 여부 확인
        if (optional.isPresent()) { // 만약 존재하면
            // 3) 해당 엔티티 꺼내기
            TodoEntity todoEntity = optional.get();

            // 4) 해당 엔티티 수정하기
            todoEntity.setTitle(todoDto.getTitle());
            todoEntity.setContent(todoDto.getContent());
            todoEntity.setDone(todoDto.isDone());

            // 5) Entity -변환-> DTO 후 반환
            return todoEntity.toDto();

        } else { // 만약 존재하지 않으면
            return null; // null 반환
        }

        // *** 스트림 사용 시 *** //
        // return todoReopositoy.findById(todoDto.getId()) // 매개변수로 받은 id와 일치하는 PK 키 조회
        //            .map(
        //              (entity) -> { // 람다식 사용
        //                  todoEntity.setTitle(todoDto.getTitle());
        //                  todoEntity.setContent(todoDto.getContent());
        //                  todoEntity.setDone(todoDto.isDone());
        //                  return entity.toDto();
        //              }
        //             ) // 만약 존재하면 Entity -변환-> DTO 후 반환
        //            .orElse(null); // 존재하지 않으면 null 반환

    }

    // [5] 개별 삭제
    public boolean todoDelete(int id){
        System.out.println("TodoService.todoDelete");
        System.out.println("id = " + id);

        // 1) 삭제할 엔티티 존재 여부 조회
        boolean result = todoRepository.existsById(id);

        if(result == true){ // 만약 해당 엔티티가 존재하면
            // 3) 해당 엔티티 영속성 삭제
            todoRepository.deleteById(id);

            // 4) true 반환
            return true;
        }

        return false; // 해당 엔티티가 존재하지 않을 시 false 반환

        // *** 스트림 사용 시 *** //
        // return todoRepository.findById(id) // 매개변수로 받은 id와 일치하는 PK 키 조회
        //              .map(
        //                  (entity) -> // 람다식 사용
        //                      todoRepositody.deleteById(id); // 해당 엔티티 삭제
        //                      return true; // truen 반환
        //                  )
        //              }
        //              .orElse(false); // 존재하지 않으면 false 반환

    }

    /*
    [영속된 엔티티 조회 메소드]
    1. .findAll() : 영속된 전체 엔티티 반환
    2. .findById() : 해당 PK 에 해당하는 조회된 엔티티를 반환
    3. .existsById() : 해당 PK 에 해당하는 존재여부를 확인 후 boolean 타입으로 반환
    */

    // [6] 전체 조회 (+ 페이징 처리)
    public List<TodoDto> todoFindByPage(int page, int size){
        System.out.println("TodoService.tododFindByPage");
        System.out.println("page = " + page + ", size = " + size);

        /*
        [JPA 가 제공하는 페이징 처리 클래스]
        1. PageRequest 클래스
            - .of(조회할 페이지 번호, 페이지 당 조회할 자료의 개수, Sort.by(Sort.Direction.DESC/ASC, "정렬할 필드명"))
                -> static 메소드로 new 키워드 없이 사용 가능
                -> 조회할 페이지 번호는 0부터 시작하므로 -1 후 사용
                -> DESC : 정렬할 필드명을 기준으로 내림차순 정렬
                -> ASC : 정렬할 필드명을 기준으로 오름차순 정렬
        */

        // 1) PageRequest 클래스를 이용한 페이징 처리 설정
        PageRequest pageRequest
                = PageRequest.of(page -1 , size, Sort.by(Sort.Direction.DESC, "id"));
                            // 초기값 기준 1페이지에 3개 조회 / "id" 필드 기준으로 내림차순 정렬

        // 2) PageRequest 의 객체를 findXXX 에 매개변수로 대입하기 (스트림 사용)
        Page<TodoEntity> todoEntityPage = todoRepository.findAll(pageRequest); // Page 타입으로 반환받을 수 있음
        System.out.println("todoEntityPage = " + todoEntityPage);

        System.out.println(todoEntityPage.getTotalPages()); // .getTotalPages() : 전체 페이지 수 반환
        System.out.println(todoEntityPage.getTotalElements()); // .getTotalElements() : 전체 게시물 수 반환
        System.out.println(todoEntityPage.getContent()); // .getContent() : Page 타입 -변환-> List 타입

        // 3) Page 타입의 Entity -변환-> DTO
        List<TodoDto> todoDtoList = new ArrayList<>();
        for(int i = 0; i < todoEntityPage.getSize(); i ++){
            // 4) 페이징 처리된 Entity 꺼내오기
            TodoDto todoDto = todoEntityPage.getContent().get(i).toDto();
            todoDtoList.add(todoDto);
        }

        // 4) 반환
        return todoDtoList;

        // *** 스트림 사용 시 *** //
        // return  todoRepository.findAll(pageRequest).stream()
        //          .map(TodoEntity::toDto)
        //          .collect(Collectors.toList());
    }

    // [*] 제목 검색 조회 (입력한 값과 일치한 제목 조회)
    public List<TodoDto> search1 (String title){
        System.out.println("TodoService.search1");
        System.out.println("title = " + title);

        // *) 쿼리메소드 : JPA Repository 에 직접 만든 추상메소드 사용
        return todoRepository.findByTitle(title)
                .stream().map(TodoEntity::toDto)
                .collect(Collectors.toList());

        // *) 네이티브쿼리 : JPA Repository 에 직접 만든 추상메소드 사용
        // return todoRepository.findByTitleNative(title)
        //         .stream().map(TodoEntity::toDto)
        //         .collect(Collectors.toList());
    }

    // [*] 제목 검색 조회 (입력한 값이 포함된 제목 조회)
    public List<TodoDto> search2 (String keyword){
        System.out.println("TodoService.search2");
        System.out.println("keyword = " + keyword);

        // *) 쿼리메소드
        // return todoRepository.findByTitleContaining(keyword)
        //         .stream().map(TodoEntity::toDto)
        //         .collect(Collectors.toList());

        // *) 네이티브쿼리
        return todoRepository.findByTitleNativeSearch(keyword)
                .stream().map(TodoEntity::toDto)
                .collect(Collectors.toList());
    }
}
