package ru.probe.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.probe.utils.Logger;
import ru.probe.utils.MessageModel;

@RestController
public class ErrorHotelController implements ErrorController  {
	
	/**
	 * name of log file for this controller
	 */
	private final String logName = "RequestControl";
	
	@Autowired
	private Logger logger;
	
	/**
	 * info for the client
	 * @return String error message
	 */
	private MessageModel<String> get403(MessageModel<String> mm) {
		return mm.sendError("Not auth");
	}
	
	/**
	 * info for the client
	 * @return String error message
	 */
	private MessageModel<String> get404(MessageModel<String> mm) {
		return mm.sendError("Not found");
	}
	
	/**
	 * info for the client
	 * @return String error message
	 */
	private MessageModel<String> get500(MessageModel<String> mm) {
		return mm.sendError("Server ill");
	}
    
	/**
	 * main controller method
	 * @param request
	 * @return String message
	 */
    @RequestMapping("/error")
    public MessageModel<String> handleError(
    		HttpServletRequest request,
    		MessageModel<String> mm
    		) {
    	
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
         
        if (status != null) {
        	int statusCode = Integer.valueOf(status.toString());
        	
        	logger.log(String.format(
        			"Request error (status: %s) from: %s", status.toString(), request.getRemoteAddr()),
        			logName,
        			Logger.logTypes.ERROR);
        	
        	if (statusCode == HttpStatus.NOT_FOUND.value()) {
        		return this.get404(mm);
        	} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        		return this.get500(mm);
        	} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
        		return this.get403(mm);
        	}
        } else {
        	logger.log("Request error (null status)", logName, Logger.logTypes.ERROR);
        }
        
        return mm.sendError(request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString());
    }

    /**
     * return the error URL path
     */
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
