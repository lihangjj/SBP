package lz.cm.controller.pages.back.client;

import lz.cm.controller.AbstractController;
import lz.cm.service.IClientService;
import lz.cm.vo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/pages/back/client")
public class ClientController extends AbstractController {
    @Autowired
    private IClientService clientService;

    @RequestMapping("/list")
//获取方案
    String list(HttpServletRequest request) {
        setColumnMap(request, "需求:claim|电话:phone|姓名:name|公司:company:");
        return "pages/back/client/client-list";
    }

    //客户方案列表
    @RequestMapping("/listAjax")
    @ResponseBody
    Map listAjax(HttpServletRequest request) {

        return handSplit(request, clientService);
    }

    @RequestMapping("/edit")
    @ResponseBody
    boolean edit(Client client) {
        try {
            return clientService.edit(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/dealOrNoDeal")
    @ResponseBody
    boolean dealOrNoDeal(Client client) {
        try {
            return clientService.plEditDealFlag(new String[]{String.valueOf(client.getClientid())}, client.getDealFlag());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override

    protected String setUrl() {
        return null;
    }
}
