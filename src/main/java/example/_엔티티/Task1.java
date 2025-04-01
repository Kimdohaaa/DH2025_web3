package example._엔티티;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasktodo")
public class Task1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    private int id ;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(nullable = false)
    private boolean state ;

    @Column(nullable = false)
    private LocalDate createat;

    @Column(nullable = false)
    private LocalDateTime updateat;
}
