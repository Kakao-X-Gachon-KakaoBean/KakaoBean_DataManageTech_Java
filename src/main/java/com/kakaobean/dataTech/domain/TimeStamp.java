package com.kakaobean.dataTech.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TimeStamp")
public class TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time_stamp;

    public TimeStamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    //    @OneToMany(mappedBy = "timeStamp", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Precipitation> precipitation;

}
