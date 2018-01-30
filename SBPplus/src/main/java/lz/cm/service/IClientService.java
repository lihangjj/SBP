package lz.cm.service;

import lz.cm.vo.Client;

public interface IClientService extends IService<Integer, Client> {
    boolean insert(Client client) throws Exception;

    boolean edit(Client client) throws Exception;
    boolean plEditDealFlag(String[] ids,int dealFlag) throws Exception;

}
