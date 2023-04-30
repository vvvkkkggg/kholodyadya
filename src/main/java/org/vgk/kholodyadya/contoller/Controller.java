package org.vgk.kholodyadya.contoller;

import org.apache.catalina.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vgk.kholodyadya.database.SingletonSessionFactory;
import org.vgk.kholodyadya.entity.Products;
import org.vgk.kholodyadya.entity.Users;
import org.vgk.kholodyadya.repository.ProductRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
public class Controller {
    @GetMapping("/{str}")
    public String test(@PathVariable("str") String str) {
        ProductRepository.checkIfProductExists(str);
        return "OK";
    }
}