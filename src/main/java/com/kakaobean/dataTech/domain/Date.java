package com.kakaobean.dataTech.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;


@Entity
@Getter
@Table(name = "Date")
public class Date {

    @Id
    @GeneratedValue
    private Long id;

    private java.util.Date date; // 날짜 2023/5/12

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SubwayDay week_day; // 요일 FRI

    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL)
    private List<Precipitation> perceptation;

}
