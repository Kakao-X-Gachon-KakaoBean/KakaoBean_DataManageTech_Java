package com.kakaobean.dataTech.domain;

import lombok.Getter;

import javax.persistence.*;
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

//    @OneToMany(mappedBy = "week_day", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Date> subwayDate;



}
