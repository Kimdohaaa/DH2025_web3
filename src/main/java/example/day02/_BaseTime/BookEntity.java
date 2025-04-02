package example.day02._BaseTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "day02book")
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    @Column(nullable = false, length = 100)
    private String bname;

    @Column(nullable = false, length = 30)
    private String writer;

    @Column(nullable = false, length = 50)
    private  String bcompany;

    @Column
    private int year;
}
