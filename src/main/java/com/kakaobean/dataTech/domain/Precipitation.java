package com.kakaobean.dataTech.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "Precipitation")
public class Precipitation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Date date;

    @ManyToOne
    @JoinColumn
    private TimeStamp timeStamp;

    private float precipitation;

//    @OneToMany(mappedBy = "precipitation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<SubwayUsage> subwayUsage;


}
