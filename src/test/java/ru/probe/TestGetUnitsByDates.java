package ru.probe;

/*
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.probe.dbmodels.Unit;
import ru.probe.repos.UnitsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
*/

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource("/application-test.properties")
public class TestGetUnitsByDates {

//    @Autowired
//    private UnitsRepository unitsRepository;

    /*
    @Test
    public void test1() throws ParseException {
        List<Unit> unitsAll = unitsRepository.findAll();
        
        SimpleDateFormat a = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        
        Date dateFrom = a.parse("15.02.1002 07:08:09");
        Date dateTo = a.parse("16.03.2034 10:11:12");

        List<Unit> unitsSelect = unitsRepository.findAllByBookFromLessThanOrBookToGreaterThan(
        		dateFrom,
        		dateTo
        );

        List<Date> unitsValid = new ArrayList<>();

        unitsAll.forEach((unit) -> {
            if (!(unit.getBookFrom().after(dateTo) || unit.getBookTo().before(dateFrom))) {
                unitsValid.add(unit.getBookFrom());
                unitsValid.add(unit.getBookTo());
                unitsValid.add(null);
            }
        });

        assertNull(null);
    }
    */
}
