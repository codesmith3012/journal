package net.engineeringdigest.journalApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class SchoolData {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Getter
    @Setter
    private String schoolName;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy="schoolData",cascade = CascadeType.ALL)
    private List<WebhookDetails> webbhookDetails;

    @Setter
    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy="schoolData",cascade = CascadeType.ALL)
    private List<Student> students;

}
