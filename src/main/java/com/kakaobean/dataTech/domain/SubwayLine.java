package com.kakaobean.dataTech.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "SubwayLine")
public class SubwayLine {

    @Id
    @GeneratedValue
    private Long id;

    private String lineName;

    @OneToMany(mappedBy = "subwayLine", cascade = CascadeType.ALL)
    private List<SubwayStation> subwayStation;

}
