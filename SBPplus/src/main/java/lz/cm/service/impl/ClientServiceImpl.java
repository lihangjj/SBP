package lz.cm.service.impl;

import lz.cm.dao.IClientDAO;
import lz.cm.service.IClientService;
import lz.cm.vo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private IClientDAO clientDAO;


    @Override
    public boolean insert(Client client) throws Exception {
        return clientDAO.doCreate(client);
    }

    @Override
    public boolean edit(Client client) throws Exception {
        return clientDAO.doUpdate(client);
    }

    @Override
    public boolean plEditDealFlag(String[] ids, int dealFlag) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("ids", ids);
        parameterMap.put("dealFlag", dealFlag);
        return clientDAO.plUpdateDealFlag(parameterMap);
    }

    @Override
    public Client getVoById(Integer integer) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        return clientDAO.splitVoByFlag(Client.class, column, keyWord, currentPage, lineSize, getCondition(parameterName,"=",parameterValue));
    }
}
