package com.definex.enterprise.app.timesheet.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    public  static int getEndOfMonth(String period) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(df.parse(period));
        return c.getActualMaximum(Calendar.DATE);
    }

    public static String getCurrentUserName() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else if (principal instanceof DefaultOidcUser) {
            username = ((DefaultOidcUser) principal).getEmail();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
