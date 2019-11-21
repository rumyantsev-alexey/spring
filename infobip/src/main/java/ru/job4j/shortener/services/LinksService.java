package ru.job4j.shortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.shortener.RandomString;
import ru.job4j.shortener.models.Link;
import ru.job4j.shortener.models.User;
import ru.job4j.shortener.repositories.LinksRepository;

@Service
public class LinksService {
    private static final int SURL_LEN = 6;

    @Autowired
    private LinksRepository linkr;
//    @Autowired
//    private UsersRepository usr;

    public String getShotLink(String dest, User user) {
        String surl = RandomString.genString(SURL_LEN);
        Link newlink = new Link();
        newlink.setSource(dest);
        newlink.setResult(surl);
        newlink.setUser(user);
        newlink = linkr.save(newlink);
//        user.getLinks().add(newlink);
//        usr.save(user);
        return surl;
    }

    public Link findByResult(String result) {
        return linkr.findByResult(result);
    }
}
