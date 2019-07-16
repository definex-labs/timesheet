package com.definex.enterprise.app.timesheet.controllers;

import com.definex.enterprise.app.timesheet.entities.Period;
import com.definex.enterprise.app.timesheet.repositories.PeriodRepository;
import com.definex.enterprise.app.timesheet.services.TimeManagementService;
import com.definex.enterprise.app.timesheet.utility.Consts;
import com.definex.enterprise.app.timesheet.view.PeriodView;
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
import java.text.ParseException;

@Controller
@RequestMapping("/periods/")
@PreAuthorize("hasRole('ROLE_timesheet.admin')")
public class PeriodController {

    private final TimeManagementService timeManagementService;

    @Autowired
    public PeriodController (TimeManagementService timeManagementService) {
        this.timeManagementService = timeManagementService;
    }

    @GetMapping("add")
    public String showSignUpForm(Model model) {
        model.addAttribute("period", new PeriodView());
        model.addAttribute("prdList", Consts.getPeriod());
        model.addAttribute("monthList", Consts.getMonths());
        model.addAttribute("yearList", Consts.getYears());
        return Consts.PATH_PERIOD + "/add-period";
    }

    @GetMapping("list")
    public String showList(Model model) {
        model.addAttribute("periods", timeManagementService.getAllPeriods(true));
        return Consts.PATH_PERIOD + "/index";
    }

    @PostMapping("add")
    public String addStudent(PeriodView period, Model model) {
        try {
            timeManagementService.savePeriod(period);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:list";
    }

    @GetMapping("delete/{id}")
    public String deletePeriod(@PathVariable("id") Long id, Model model) {
        timeManagementService.deletePeriod(id);
        model.addAttribute("periods", timeManagementService.getAllPeriods(true));
        return Consts.PATH_PERIOD + "/index";
    }
}
