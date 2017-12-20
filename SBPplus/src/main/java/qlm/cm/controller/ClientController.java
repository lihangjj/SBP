package qlm.cm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import qlm.cm.service.IClientService;
import qlm.cm.vo.Client;

import java.util.Date;


@Controller
@RequestMapping("/pages/front/client")
public class ClientController extends AbstractController {
    @Autowired
    private IClientService clientService;

    @RequestMapping("/getProgram")//获取方案
    public String getProgram(Model model, Client client) {
        client.setPubdate(new Date());
        System.err.println(client);

        try {
            if (clientService.insert(client)) {
                model.addAttribute("result", true);
            } else {
                model.addAttribute("result", false);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/front/client/getProgram_Result";
    }

}
