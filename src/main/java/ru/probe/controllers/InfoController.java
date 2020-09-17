package ru.probe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.probe.convertors.DateConvertUtils;
import ru.probe.dbmodels.Unit;
import ru.probe.repos.UnitsRepository;

import java.util.List;

/**
 * receive here info get requests
 * 
 * @author maksim
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private UnitsRepository unitsRepository;

    /**
     * return current unit info
     * 
     * @param unit
     * @return Unit
     */
    @GetMapping("/{unit}")
    public Unit getUnit(@PathVariable Unit unit) {
        return unit;
    }

    /**
     * check bookinable period
     * 
     * @param date_from
     * @param date_to
     * @return List<Unit>
     */
    @GetMapping("/check/dates")
    public List<Unit> getByDates(
            @RequestParam(name = "from")
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") java.util.Date date_from,
            @RequestParam(name = "to")
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") java.util.Date date_to
    ) {
        return unitsRepository.findAllByBookToGreaterThanAndBookFromLessThanOrderByBookFrom(
                DateConvertUtils.convertDateToSqlDate(date_from),
                DateConvertUtils.convertDateToSqlDate(date_to)
        );
    }
    
    /**
     * return all units
     * 
     * @return List<Unit>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getall")
    public Iterable<Unit> regManPost() {
        return unitsRepository.findAll();
    }
}

