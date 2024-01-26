package com.emma.firstapp;

import com.emma.firstapp.intro.Option;
import com.emma.firstapp.intro.Option.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/intro")
public class IntroFieldController {

    @ModelAttribute
    public void addOptionToModel(Model model) {
        List<Option> options = Arrays.asList(
                new Option("SPR", "Spring", Type.FRAMEWORK),
                new Option("PLA", "Play", Type.FRAMEWORK),
                new Option("QARK", "Qarkus", Type.FRAMEWORK),
                new Option("THYM", "Thymeleaf", Type.STARTER),
                new Option("SWEB", "SpringWeb", Type.STARTER),
                new Option("DEVT", "Devtools", Type.STARTER));
        Type[] types = Option.Type.values();
        for (Type type : types) {
            String attributeName = type.toString().toLowerCase();
            List<Option> attributeValue = filterByType(options, type);
            log.info("Type: {}", type.toString());
            log.info("AttributeName: {}", attributeValue);
            log.info("AttributeValue: {}", attributeValue);
            model.addAttribute(attributeName, attributeValue);
        }
    }

    @GetMapping
    public String showIntroFieldForm() {
        return "intro";
    }

    private List<Option> filterByType(
            List<Option> options, Type type) {
        return options
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
