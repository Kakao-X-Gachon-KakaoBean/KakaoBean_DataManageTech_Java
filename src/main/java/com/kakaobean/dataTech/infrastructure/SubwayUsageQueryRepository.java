package com.kakaobean.dataTech.infrastructure;

import com.kakaobean.dataTech.domain.dto.BasicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubwayUsageQueryRepository {

    private final JdbcTemplate jdbcTemplate;
    private Integer last = 1000000;
    private final Integer limit = 1000000;

    public List<BasicDto> findAll(){
        List<BasicDto> list = jdbcTemplate.query("SELECT on_board, off_board, station_name, line_name, precipitation, date, week_day, time_stamp\n" +
                        "FROM subway_usage as su " +
                        "join subway_station ss on su.subway_station_id = ss.id\n" +
                        "join subway_line sl on sl.id = ss.subway_line_id\n" +
                        "join precipitation p on su.precipitation_id = p.id\n" +
                        "join date d on d.id = p.date_id\n" +
                        "join day_of_week dow on dow.id = d.week_day_id\n" +
                        "join time_stamp ts on p.time_stamp_id = ts.id\n" +
                        "where su.id < ? limit ?",
                (rs, rowNum) -> new BasicDto(
                        rs.getInt("on_board"),
                        rs.getInt("off_board"),
                        rs.getString("station_name"),
                        rs.getString("line_name"),
                        rs.getDouble("precipitation"),
                        rs.getString("date"),
                        rs.getString("week_day"),
                        rs.getString("time_stamp")
                ),
                last, limit
        );
        last += limit;
        return list;
    }
}

// 인덱스를 태우는 페이징 처리 쿼리
//SELECT * FROM 테이블명 WHERE 숫자(A) < key LIMIT 숫자(B)