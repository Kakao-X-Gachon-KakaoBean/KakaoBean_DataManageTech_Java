package com.kakaobean.dataTech.domain;

import javax.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "SubwayUsage")
public class SubwayUsage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    SubwayStation subwayStation;

    @ManyToOne
    @JoinColumn
    private Precipitation precipitation;

    private Integer onBoard;

    private Integer offBoard;



}
