package com.definex.enterprise.app.timesheet.controllers;

import com.definex.enterprise.app.timesheet.entities.Wbs;
import com.definex.enterprise.app.timesheet.repositories.WbsRepository;
import com.definex.enterprise.app.timesheet.utility.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/wbs/")
public class WbsController {

    private final WbsRepository wbsRepository;

    @Autowired
    public WbsController (WbsRepository repository) {
        this.wbsRepository = repository;
    }

    @GetMapping("add")
    public String showNewEntityForm(Model model) {
        model.addAttribute("entity", new Wbs());
        model.addAttribute("wbsTypeList", Consts.getWbsType());
        model.addAttribute("statusList", Consts.getStatus());
        return Consts.PATH_WBS + "/add-wbs";
    }

    @GetMapping("list")
    public String showList(Model model) {
        model.addAttribute("entityList", wbsRepository.findAll());
        return Consts.PATH_WBS + "/index";
    }

    @PostMapping("add")
    public String addEntity(@Valid Wbs wbs, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return Consts.PATH_WBS + "/add-wbs";
        }

        wbsRepository.save(wbs);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Wbs wbs = wbsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid period Id:" + id));
        model.addAttribute("entity", wbs);
        model.addAttribute("wbsTypeList", Consts.getWbsType());
        model.addAttribute("statusList", Consts.getStatus());
        return Consts.PATH_WBS + "/update-wbs";
    }

    @PostMapping("update/{id}")
    public String updateEntity(@PathVariable("id") long id, @Valid Wbs wbs, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            wbs.setId(id);
            return Consts.PATH_WBS + "/update-wbs";
        }

        Wbs entity = wbsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid period Id:" + id));
        entity.setCode(wbs.getCode());
        entity.setAuthRequired(wbs.getAuthRequired());
        entity.setDescription(wbs.getDescription());
        entity.setOwner(wbs.getOwner());
        entity.setStatus(wbs.getStatus());
        entity.setType(wbs.getType());
        wbsRepository.save(entity);
        model.addAttribute("entityList", wbsRepository.findAll());
        return Consts.PATH_WBS + "/index";
    }

    @GetMapping("delete/{id}")
    public String deleteEntity(@PathVariable("id") long id, Model model) {
        Wbs wbs = wbsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid period Id:" + id));
        wbsRepository.delete(wbs);
        model.addAttribute("entityList", wbsRepository.findAll());
        return Consts.PATH_WBS + "/index";
    }
}
