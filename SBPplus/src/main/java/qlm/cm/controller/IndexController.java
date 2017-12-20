package qlm.cm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends AbstractController {
    @RequestMapping("/")
    String index() {
            return "index";
    }
}
