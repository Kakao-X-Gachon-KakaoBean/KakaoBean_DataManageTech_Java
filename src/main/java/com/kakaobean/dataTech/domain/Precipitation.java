package com.kakaobean.dataTech.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    private double precipitation;

    public Precipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public void addDate(Date date){
        this.date = date;
    }

    public void addTimeStamp(TimeStamp timeStamp){
        this.timeStamp = timeStamp;
    }

    //    @OneToMany(mappedBy = "precipitation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<SubwayUsage> subwayUsage;


}
