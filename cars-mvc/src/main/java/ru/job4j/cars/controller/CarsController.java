package ru.job4j.cars.controller;

import ru.job4j.cars.forms.ActionListForm;
import ru.job4j.cars.forms.AddAdsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.dao.CarsDbStore;
import ru.job4j.cars.models.*;
import ru.job4j.cars.dao.DbStore;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars")
//@SessionAttributes(value = "loginUser", types = UsersEntity.class)
public class CarsController {
    private static final String ANON = "anonymous";

    @Autowired
    DbStore<UsersEntity> userdb;
    @Autowired
    DbStore<MarkEntity> mdb;
    @Autowired
    DbStore<ModelEntity> mddb;
    @Autowired
    CarsDbStore acdb;
    @Autowired
    DbStore<CityEntity> citydb;
    @Autowired
    DbStore<TransmissionEntity> trdb;
    @Autowired
    DbStore<BodytypeEntity> bddb;
    @Autowired
    DbStore<EnginestypeEntity> edb;
    @Autowired
    DbStore<DriveunitEntity> drdb;
    @Autowired
    DbStore<WheelEntity> wdb;
    @Autowired
    DbStore<FotoEntity> fdb;

    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "login", required = false, defaultValue = "") String login, HttpSession session) {
        String result = "login";
        if (login.equals(ANON)) {
            session.setAttribute("loginUser", new UsersEntity(login, "", null));
            result = "redirect:/cars/list";
        }
        return result;
    }

    @PostMapping(value = "/login")
    public String loginWithUser(@RequestParam(name = "login") String login, @RequestParam(name = "pass") String pass,  HttpSession session) {
        UsersEntity user = new UsersEntity(login, pass, null);
        user = userdb.findById(userdb.findIdByModel(user));
        session.setAttribute("loginUser", user);
        return user == null ? "login" : "list";
    }

    @GetMapping(value = "/list")
    public String getListAds(Model model) {
        model.addAttribute("carlist", acdb.findAll());
        model.addAttribute("MarkEntityList", mdb.findAll());
        return "list";
    }

    @PostMapping(value = "/list", consumes = "application/json")
    public String getListAdsAction(@RequestBody ActionListForm actlist, Model model) {
        List<String> params = new ArrayList<>();
        params.add(actlist.getAction());
        params.add(actlist.getMark());
        List result = acdb.findAllCarsWithFilter(params);
        model.addAttribute("carlist", result);
        return "table";
    }

    @GetMapping(value = "/add")
    public String getNewAdForm(Model model) {
        model.addAttribute("CityEntityList", citydb.findAll());
        model.addAttribute("MarkEntityList", mdb.findAll());
        model.addAttribute("TransmissionEntityList", trdb.findAll());
        model.addAttribute("BodytypeEntityList", bddb.findAll());
        model.addAttribute("EnginestypeEntityList", edb.findAll());
        model.addAttribute("DriveunitEntityList", drdb.findAll());
        model.addAttribute("WheelEntityList", wdb.findAll());
        return "add";
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public String postNewAdForm(@ModelAttribute AddAdsForm form, Model model,  HttpSession session) throws IOException {
        CarEntity car = new CarEntity(form.getNote());
        car.setUser((UsersEntity) session.getAttribute("loginUser"));
        car.setCity(citydb.findById(citydb.findIdByModel(new CityEntity(form.getCity()))));
        car.setMark(mdb.findById(mdb.findIdByModel(new MarkEntity(form.getMark()))));
        car.setModel(mddb.findById(mddb.findIdByModel(new ModelEntity(form.getModel(), car.getMark()))));
        car.setPrice(form.getPrice());
        car.setIssue(form.getIssue());
        car.setDist(form.getDist());
        car.setTrans(trdb.findById(trdb.findIdByModel(new TransmissionEntity(form.getTrans()))));
        car.setBtype(bddb.findById(bddb.findIdByModel(new BodytypeEntity(form.getBody()))));
        car.setEtype(edb.findById(edb.findIdByModel(new EnginestypeEntity(form.getEngine()))));
        car.setEnginecapacity(form.getEnginecapacity());
        car.setPower(form.getPower());
        car.setDunit(drdb.findById(drdb.findIdByModel(new DriveunitEntity(form.getDrive()))));
        car.setWheel(wdb.findById(wdb.findIdByModel(new WheelEntity(form.getWheel()))));
        Set<FotoEntity> fotos = new HashSet<>();
        FotoEntity ft = null;
        for (var i = 0; i < form.getUpfile().length; i++) {
            ft = new FotoEntity();
            ft.setName(form.getUpfile()[i].getOriginalFilename());
            ft.setFoto(form.getUpfile()[i].getBytes());
            ft.setCar(car);
            fotos.add(ft);
        }
        car.setFotos(fotos);
        acdb.add(car);
        return "list";
    }

    @PostMapping(value = "/models")
    @ResponseBody
    public List<String> getAllModels(@RequestParam(value = "mark") String mark) {
        List<ModelEntity> mlist = mddb.findAll();
        return mlist.stream().filter((x) -> mark.equals(x.getMark().getName())).map(AbsProjectEntity::getName).collect(Collectors.toList());
    }

    @GetMapping(value = "/ad")
    public String showAd(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("curcar", acdb.findById(id));
        return "car";
    }

    @PostMapping(value = "/ad")
    public String setAttrAd(@RequestParam(value = "id") int id) {
        CarEntity car = acdb.findById(id);
        car.setOld(!car.isOld());
        acdb.update(car);
        return "redirect:/cars/list";
    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> getImageAsResponseEntity(@RequestParam(value = "cid") int cid) {
        HttpHeaders headers = new HttpHeaders();
        byte[] media = fdb.findById(cid).getFoto();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}
