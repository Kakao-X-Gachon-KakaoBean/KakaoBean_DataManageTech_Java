package com.kakaobean.dataTech.domain.dto;

import lombok.Getter;

@Getter
public class BasicDto {

    private final Integer onBoard;
    private final Integer offBoard;
    private final String subwayStationName;
    private final String subwayLine;
    private final Double precipitation;
    private final String date; //2022.03.21
    private final String weekDay; // ex 월요일, 화요일
    private final String timeStamp; //0:00, 1:00, 2:00

    public BasicDto(Integer onBoard,
                    Integer offBoard,
                    String subwayStationName,
                    String subwayLine,
                    Double precipitation,
                    String date,
                    String weekDay,
                    String timeStamp) {
        this.onBoard = onBoard;
        this.offBoard = offBoard;
        this.subwayStationName = subwayStationName;
        this.subwayLine = subwayLine;
        this.precipitation = precipitation;
        this.date = date;
        this.weekDay = weekDay;
        this.timeStamp = timeStamp;
    }
}
