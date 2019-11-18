package ru.job4j.shortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.shortener.repositories.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository users;

}
