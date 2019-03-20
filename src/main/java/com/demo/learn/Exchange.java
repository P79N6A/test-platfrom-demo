package com.demo.learn;

import com.demo.learn.common.client.ClientRequest;
import com.demo.learn.common.client.ClientResponse;


public interface Exchange {
    <REQ, RESP> ClientResponse<RESP> exchange(ClientRequest<REQ> requestMessage);
}
