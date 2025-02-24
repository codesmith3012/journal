package net.engineeringdigest.journalApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="ID")
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;//studentName

    @Getter
    @Setter
    private int age;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="school_id")
    @Getter
    @Setter
    private SchoolData schoolData;



}
