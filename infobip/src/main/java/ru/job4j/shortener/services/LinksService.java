package ru.job4j.shortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.shortener.repositories.LinksRepository;

@Service
public class LinksService {

    @Autowired
    private LinksRepository links;

}
