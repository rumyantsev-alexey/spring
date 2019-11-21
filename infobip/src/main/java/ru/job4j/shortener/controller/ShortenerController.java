package ru.job4j.shortener.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortener.forms.AccAnswer;
import ru.job4j.shortener.forms.ShortUrlAnswer;
import ru.job4j.shortener.models.Link;
import ru.job4j.shortener.models.User;
import ru.job4j.shortener.services.LinksService;
import ru.job4j.shortener.services.UsersService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "API для укорачивания ссылок")
public class ShortenerController {

    @Autowired
    private UsersService usserv;
    @Autowired
    private LinksService linkserv;

    @ApiOperation(value = "создание нового аккаунта для работы с api", response = AccAnswer.class)
    @PostMapping(value = "/account")
    public AccAnswer addAccount(@ApiParam(value = "имя нового аккаунта", required = true) @RequestParam(value = "AccountId") String name) {
        AccAnswer answer = new AccAnswer();
        if (usserv.findUserByName(name) != null) {
            answer.setResult(false);
            answer.setDescription("This account already uses");
        } else {
            answer.setResult(true);
            answer.setDescription("Your account is opened");
            answer.setPassword(usserv.createUserByUsername(name));
        }
        return answer;
    }

    @ApiOperation(value = "регистрация короткой ссылки", authorizations = {@Authorization(value="basicAuth")})
    @PostMapping(value = "/register")
    public ShortUrlAnswer shotUrl(@ApiParam(value = "ссылка для укорачивания", required = true) @RequestParam(value = "url") String url, @ApiParam(value = "тип редиректа") @RequestParam(value = "redirectType", required = false) String redir, Principal princ) {
        User user = usserv.findUserByName(princ.getName());
        return new ShortUrlAnswer(linkserv.getShotLink(url, user));
    }

    @ApiOperation(value = "просмотр статистики по существующему аккаунту", authorizations = {@Authorization(value="basicAuth")})
    @GetMapping(value = "/statistic/{AccountId}")
    public Map<String, Integer> getStatistic(@ApiParam(value = "имя аккаунта", required = true) @PathVariable(value = "AccountId") String account, @ApiParam(hidden = true) Principal princ) {
        Map<String, Integer> result = new HashMap<>();
        if (account.equals(princ.getName())) {
            List<Link> list = usserv.findUserByName(account).getLinks();
            list.forEach((x) -> result.put(x.getSource(), x.getCount()));
        }
        return result;
    }

    @ApiOperation(value = "вызов ссылки по короткому адресу", authorizations = {@Authorization(value="basicAuth")})
    @GetMapping(value = "/slink/{ShortUrl}")
    public void goToLink(@ApiParam(value = "короткая ссылка", required = true) @PathVariable(value = "ShortUrl") String shotUrl, @ApiParam(hidden = true) HttpServletResponse response) throws IOException {
            Link link = linkserv.findByResult(shotUrl);
        String redirect = "";
        if (link != null) {
            redirect = link.getSource();
        }
        response.sendRedirect(redirect);
    }
}
