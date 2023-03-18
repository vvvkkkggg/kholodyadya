package org.vgk.kholodyadya.contoller;

import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vgk.kholodyadya.database.SingletonSessionFactory;

@RestController
public class User {
    @GetMapping("/")
    public String test() {
        try (SessionFactory sessionFactory = SingletonSessionFactory.getSessionFactory()) {
            return "OK";
        } catch (Throwable e) {
            return e.getMessage();
        }
    }
}