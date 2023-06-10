package com.kakaobean.dataTech.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "Date")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date; // 날짜 2020-05-12

    @ManyToOne
    @JoinColumn
    private DayOfWeek week_day; // 요일 FRI

    public Date(String date) {
        this.date = date;
    }

    public void addWeekDay(DayOfWeek dayOfWeek){
        this.week_day = dayOfWeek;
    }

    //    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Precipitation> perceptation;

}
