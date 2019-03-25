package com.demo.pylontest;

import me.ele.contract.client.ClientUtil;
import me.ele.tradecenter.eos.api.ElemeOrderService;

public class PylonContact {

    public static void main(String[] args) {
        ClientUtil.getContext().initClients("/src/main/resource/demo.json");
        try {
            System.out.println(ClientUtil
                    .getContext()
                    .getClient(ElemeOrderService.class)
                    .get(1202027750598566940L));
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    }
