package ru.probe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.probe.dbmodels.Status;
import ru.probe.dbmodels.Unit;
import ru.probe.repos.UnitsRepository;
import ru.probe.utils.ErrorUtils;
import ru.probe.utils.MessageModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private UnitsRepository unitsRepository;

	@PostMapping(path = "/registry")
    public MessageModel<Unit> regMan(
            @RequestParam @NotBlank Status status,
            @RequestParam @NotBlank int number,
            @RequestParam @NotBlank @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date_from,
            @RequestParam @NotBlank @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date_to,
            @Valid Unit unit,
            BindingResult bindingResult,
            MessageModel<Unit> mm
    ) {

        if (bindingResult.hasErrors()) {
        	return mm.sendErrors(ErrorUtils.getErrors(bindingResult));
        }

        if (status == null) {
        	return mm.sendError("statusERROR", "not correct status");
        }
        
        if (!date_from.before(date_to)) {
        	return mm.sendError("datesERROR", "not correct dates");
        }
        
        if (!unitsRepository.findAllByNumberAndBookToGreaterThanAndBookFromLessThanOrderByBookFrom(
	        		number,
	        		date_from,
	        		date_to
        		).isEmpty()) {
        	return mm.sendError("periodERROR", "This period has booked");
        }

        unit.setUnitStatus(status);

        unit.setBookFrom(date_from);
        unit.setBookTo(date_to);

        unit.setNumber(number);

        unitsRepository.save(unit);

        return mm.sendData(unit);
    }
    
	@PutMapping(path = "/registry")
    public MessageModel<Unit> putMan(
    		@RequestParam Status status,
            @RequestParam int number,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date_from,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date_to,
            @RequestParam @NotBlank Unit unit,
            BindingResult bindingResult,
            MessageModel<Unit> mm
    ) {
    	boolean isChanged = false;
    	
    	if (status != null && !unit.getUnitStatus().equals(status)) {
    		isChanged = true;
    		unit.setUnitStatus(status);
    	}
    	
    	if (unit.getNumber() != number) {
    		isChanged = true;
    		unit.setNumber(number);
    	}
    	
    	if (date_from != null && !unit.getBookFrom().equals(date_from)) {
    		isChanged = true;
    		unit.setBookFrom(date_from);
    	}
    	
    	if (date_to != null && !unit.getBookTo().equals(date_to)) {
    		isChanged = true;
    		unit.setBookTo(date_to);
    	}
    	
    	if (isChanged) {
    		unitsRepository.save(unit);
    		return mm.sendData(unit);
    	} else {
    		return mm.sendError("changeERROR", "Nothing changed");
    	}
    }
}



