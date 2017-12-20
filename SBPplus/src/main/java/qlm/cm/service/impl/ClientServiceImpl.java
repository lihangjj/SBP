package qlm.cm.service.impl;

import qlm.cm.dao.IClientDAO;
import qlm.cm.service.IClientService;
import qlm.cm.vo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private IClientDAO clientDAO;


    @Override
    public boolean insert(Client client) throws Exception {
        return clientDAO.doCreate(client);
    }
}
