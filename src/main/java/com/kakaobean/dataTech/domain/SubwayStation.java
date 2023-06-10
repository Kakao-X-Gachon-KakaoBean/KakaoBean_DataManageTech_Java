package com.kakaobean.dataTech.domain;

import javax.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "SubwayStation")
public class SubwayStation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    @ManyToOne
    @JoinColumn
    private SubwayLine subwayLine;

//    @OneToMany(mappedBy = "subwayStation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<SubwayUsage> subwayUsage;


}
