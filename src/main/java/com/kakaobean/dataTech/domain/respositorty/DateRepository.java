package com.kakaobean.dataTech.domain.respositorty;

import com.kakaobean.dataTech.domain.Date;
import com.kakaobean.dataTech.domain.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DateRepository extends JpaRepository<Date, Long> {

    @Query("select d from Date d where d.date = :date")
    Optional<Date> findByDate(@Param("date") String date);
}
