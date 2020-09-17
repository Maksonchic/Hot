package ru.probe.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ru.probe.dbmodels.Unit;

import java.util.Date;
import java.util.List;

public interface UnitsRepository extends JpaRepository<Unit, String> {

    List<Unit> findAllByBookFromLessThanOrBookToGreaterThan(
    		@Param("book_from") Date dateFrom,
    		@Param("book_to") Date dateTo);
    
    List<Unit> findAllByBookToGreaterThanAndBookFromLessThanOrderByBookFrom(
    		@Param("book_from") Date dateFrom,
    		@Param("book_to") Date dateTo);
    
    List<Unit> findAllByNumberAndBookToGreaterThanAndBookFromLessThanOrderByBookFrom(
    		@Param("room_num") int number,
    		@Param("book_from") Date dateFrom,
    		@Param("book_to") Date dateTo);

}
