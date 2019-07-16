package com.definex.enterprise.app.timesheet.controllers;

import com.definex.enterprise.app.timesheet.services.TimeManagementService;
import com.definex.enterprise.app.timesheet.utility.Consts;
import com.definex.enterprise.app.timesheet.view.LovPojo;
import com.definex.enterprise.app.timesheet.view.TimeRowPojo;
import com.definex.enterprise.app.timesheet.view.TimeSheetView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/timesheet/")
public class TimeSheetController {

    private TimeManagementService timeManagementService;

    @Autowired
    private TimeSheetView timeSheet;

    @Autowired
    public TimeSheetController(TimeManagementService timeManagementService) {
        this.timeManagementService = timeManagementService;
    }


    @GetMapping("list")
    public String showTimeSheet(Model model) {
        if (timeSheet.getPeriods() == null || timeSheet.getPeriods().isEmpty()) {
            initTimeSheet();
        }
        timeSheet = timeManagementService.getTimeSheetView(timeSheet);
        model.addAttribute("timeSheetView", timeSheet);
        return Consts.PATH_TIMESHEET + "/index";
    }

    private void initTimeSheet() {
        timeSheet.setPeriods(timeManagementService.getAllPeriods(false));
        timeSheet.setWbs(timeManagementService.getAllWbs());
        timeSheet.setPeriod(timeManagementService.getActivePeriod());
    }

    @PostMapping("update")
    public String updateView(@Valid TimeSheetView view, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return Consts.PATH_WBS + "/add-wbs";
        }
        this.timeSheet.setPeriod(view.getPeriod());
        return "redirect:list";
    }

    @PostMapping(value = "update", params = "action=submit")
    public String submitTime(TimeSheetView timeSheet, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return Consts.PATH_WBS + "/add-wbs";
        }
        timeManagementService.saveTimeSheet(timeSheet);
        return "redirect:list";
    }

    @GetMapping("addRow")
    public String addRow (Model model) {
        addRow();
        model.addAttribute("timeSheetView", timeSheet);
        return Consts.PATH_TIMESHEET + "/index";
    }

    private void addRow() {
        TimeRowPojo row = new TimeRowPojo();
        List<TimeRowPojo> rows = timeSheet.getRows();
        rows.add(row);

        timeSheet.setRows(rows);
    }
}
