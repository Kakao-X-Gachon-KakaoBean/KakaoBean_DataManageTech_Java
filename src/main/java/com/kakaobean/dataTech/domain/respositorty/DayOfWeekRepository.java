package com.kakaobean.dataTech.domain.respositorty;

import com.kakaobean.dataTech.domain.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Long> {

    @Query("select d from DayOfWeek d where d.week_day = :dayOfWeek")
    Optional<DayOfWeek> findByDayOfWeek(@Param("dayOfWeek") String dayOfWeek);

}
