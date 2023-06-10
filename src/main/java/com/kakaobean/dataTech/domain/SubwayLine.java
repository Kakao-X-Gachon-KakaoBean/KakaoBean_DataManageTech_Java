package com.kakaobean.dataTech.domain;

import javax.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "SubwayLine")
public class SubwayLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lineName;

//    @OneToMany(mappedBy = "subwayLine", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<SubwayStation> subwayStation;

}
