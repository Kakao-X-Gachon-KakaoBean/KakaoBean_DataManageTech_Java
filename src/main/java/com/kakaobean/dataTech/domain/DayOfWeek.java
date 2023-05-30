package com.kakaobean.dataTech.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "DayOfWeek")
public class DayOfWeek {

    @Id
    @GeneratedValue
    private Long id;

//    @Enumerated(EnumType.STRING)
    private String week_day;

    public DayOfWeek(String week) {
        this.week_day = week;
    }

//    @OneToMany(mappedBy = "week_day", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Date> subwayDate;



}
