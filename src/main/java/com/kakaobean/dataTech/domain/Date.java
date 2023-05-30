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
    @GeneratedValue
    private Long id;

    private LocalDate date; // 날짜 2023/5/12

    @ManyToOne
    @JoinColumn
    private DayOfWeek week_day; // 요일 FRI

    public Date(LocalDate date) {
        this.date = date;
    }

    public void addWeekDay(DayOfWeek dayOfWeek){
        this.week_day = dayOfWeek;
    }

    //    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Precipitation> perceptation;

}
