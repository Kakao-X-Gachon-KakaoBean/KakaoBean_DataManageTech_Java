package com.kakaobean.dataTech.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "Precipitation")
public class Precipitation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TimeStamp timeStamp;

    private float precipitation;

    @OneToMany(mappedBy = "precipitation", cascade = CascadeType.ALL)
    private List<SubwayUsage> subwayUsage;


}
