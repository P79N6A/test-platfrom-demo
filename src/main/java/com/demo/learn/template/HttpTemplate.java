package com.demo.learn.template;

import com.demo.learn.Exchange;
import com.demo.learn.common.client.ClientRequest;
import com.demo.learn.common.client.ClientResponse;

public class HttpTemplate implements Exchange {

    @Override
    public <REQ, RESP> ClientResponse<RESP> exchange(ClientRequest<REQ> requestMessage) {
        return null;
    }
}
