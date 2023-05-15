package com.kakaobean.dataTech.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "TimeStamp")
public class TimeStamp {

    @Id
    @GeneratedValue
    private Long id;

    private String time_stamp;

    @OneToMany(mappedBy = "timeStamp", cascade = CascadeType.ALL)
    private List<Precipitation> precipitation;

}
