package ru.probe.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import ru.probe.config.AuthConfig;
import ru.probe.dbmodels.User;
import ru.probe.repos.UserRepo;
import ru.probe.utils.Logger;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private final AuthConfig	authConfig;
	private final Logger		logger;
	private final UserRepo		userRepo;
	
    public JWTAuthorizationFilter(
    		AuthenticationManager authManager,
    		AuthConfig authConfig,
    		UserRepo userRepo,
    		Logger logger) {
        super(authManager);
        this.authConfig = authConfig;
        this.userRepo = userRepo;
        this.logger = logger;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    	
    	try {
    		String token = request.getHeader(authConfig.getKEY_NAME_TOKEN());
            if (token != null) {

                String userName = JWT.require(Algorithm.HMAC512(authConfig.getSECRET_KEY().getBytes()))
                        .build()
                        .verify(token)
                        .getSubject();
                
                User user;

                if (userName != null && (user = userRepo.findByUsername(userName)) != null) {
                	logger.log(
            				userName + " authorized successfully",
            				"Authorization");
                	
                    return new UsernamePasswordAuthenticationToken(
                    		userName,
                    		null,
                    		user.getRoles());
                }
                return null;
            }
    	} catch(NullPointerException e) {
    		logger.log(
    				"Could not get auth while runtime ERROR",
    				"Authorization",
    				Logger.logTypes.ERROR);
    	} catch(JWTVerificationException e) {
    		logger.log(
    				"Could not verificate token",
    				"Authorization");
    	}
    	
        return null;
    }
}
