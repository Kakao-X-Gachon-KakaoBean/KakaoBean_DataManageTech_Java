package com.kakaobean.dataTech.domain;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "SubwayUsage")
public class SubwayUsage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    SubwayStation subwayStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Precipitation precipitation;

    private Integer onBoard;

    private Integer offBoard;



}
