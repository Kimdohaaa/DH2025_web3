package example.day02._BaseTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "day02book")
@Data
public class BookEntity extends BaseTime{ // BaseEntity 를 상속받음

    @Id // PRIMARY KEY
    @GeneratedValue( strategy = GenerationType.IDENTITY) // auto_increment
    private int 도서번호;
    @Column( nullable = false , length = 100 ) // not null , varchar(30)
    private String 도서명;
    @Column( nullable = false , length = 30 ) // not null  , varchar(100)
    private String 저자;
    @Column( nullable = false , length = 50 ) // not null  , varchar(50)
    private String 출판사;
    @Column
    private int 출판연도;
}
