package me.ks.springdeveloper.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("/example")
    public String example(Model model) {
        Person person = new Person();
        person.setId(1L);
        person.setName("병신");
        person.setAge(56);
        person.setHobbies(List.of("1","2","3","4","운덩","운동"));

        model.addAttribute("person", person);
        model.addAttribute("today", LocalDate.now());

        return "/example";
    }


    @Data
    class Person{
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
