package ru.job4j.cars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.CarEntity;
import ru.job4j.cars.DbStore;
import ru.job4j.cars.MarkEntity;
import ru.job4j.cars.UsersEntity;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cars")
@SessionAttributes(value = "loginUser", types = UsersEntity.class)
public class CarsController {

    @Autowired
    DbStore<UsersEntity> userdb;
    @Autowired
    DbStore<CarEntity> cdb;
    @Autowired
    DbStore<MarkEntity> mdb;

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginWithUser(@ModelAttribute(name = "login") String login, @ModelAttribute(name = "pass") String pass,  HttpSession session) {
        UsersEntity user = new UsersEntity(login, pass, null);
        if (!user.getName().equals("anonymus")) {
            user = userdb.findById(userdb.findIdByModel(user));
        }
        session.removeAttribute("loginUser");
        if (user != null) {
            session.setAttribute("loginUser", user);
        }
        return "list";
    }

    @GetMapping(value = "/list")
    public String getListAds(Model model) {
        model.addAttribute("carlist", cdb.findAll());
        model.addAttribute("MarkEntityList", mdb.findAll());
        return "list";
    }
}
