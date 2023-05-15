package com.kakaobean.dataTech.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "SubwayStation")
public class SubwayStation {


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SubwayLine subwayLine;

    @OneToMany(mappedBy = "subwayStation", cascade = CascadeType.ALL)
    private List<SubwayUsage> subwayUsage;


}
