package com.kakaobean.dataTech.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "SubwayDay")
public class SubwayDay {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Week week_day;

    @OneToMany(mappedBy = "week_day", cascade = CascadeType.ALL)
    private List<Date> subwayDate;



}
