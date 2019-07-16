package com.definex.enterprise.app.timesheet.controllers;

import com.definex.enterprise.app.timesheet.entities.CalendarInfo;
import com.definex.enterprise.app.timesheet.repositories.CalendarInfoRepository;
import com.definex.enterprise.app.timesheet.utility.Consts;
import com.definex.enterprise.app.timesheet.view.CalendarInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/calendar/")
@PreAuthorize("hasRole('ROLE_timesheet.admin')")
public class CalendarInfoController {

    private final CalendarInfoRepository calendarInfoRepository;

    @Autowired
    public CalendarInfoController(CalendarInfoRepository repository) {
        this.calendarInfoRepository = repository;
    }


    @GetMapping("list")
    public String showList(Model model) {
        model.addAttribute("entityList", calendarInfoRepository.findAll());
        return Consts.PATH_CALENDAR_INFO + "/index";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        CalendarInfo calendarInfo = calendarInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid period Id:" + id));
        model.addAttribute("entity", calendarInfo);
        model.addAttribute("statusList", Consts.getCalendarStatus());
        return Consts.PATH_CALENDAR_INFO + "/update-calendar";
    }

    @PostMapping("update/{id}")
    public String updateEntity(@PathVariable("id") long id, @Valid CalendarInfo calendarInfo, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            calendarInfo.setId(id);
            return Consts.PATH_CALENDAR_INFO + "/update-calendar";
        }

        CalendarInfo entity = calendarInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid period Id:" + id));

        entity.setStatus(calendarInfo.getStatus());

        calendarInfoRepository.save(entity);
        model.addAttribute("entityList", calendarInfoRepository.findAll());
        return Consts.PATH_CALENDAR_INFO + "/index";
    }
}
