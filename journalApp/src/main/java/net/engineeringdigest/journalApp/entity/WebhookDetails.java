package net.engineeringdigest.journalApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class WebhookDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="ID")
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String eventName;//add,delete

    @Getter
    @Setter
    private String endPointUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="school_id")
    @Getter
    @Setter
    private SchoolData schoolData;



}
