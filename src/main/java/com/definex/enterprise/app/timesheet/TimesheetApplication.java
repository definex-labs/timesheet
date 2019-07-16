package com.definex.enterprise.app.timesheet;

import com.definex.enterprise.app.timesheet.view.TimeSheetView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class TimesheetApplication {

	@Bean
	@Scope( value = WebApplicationContext.SCOPE_SESSION,
			proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TimeSheetView timeSheet() {
		return new TimeSheetView();
	}

	public static void main(String[] args) {
		SpringApplication.run(TimesheetApplication.class, args);
	}

}
