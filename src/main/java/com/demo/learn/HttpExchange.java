package com.demo.learn;

import com.demo.learn.common.client.ClientRequest;
import com.demo.learn.common.client.ClientResponse;
import com.demo.learn.common.client.http.HttpClientRequest;


public interface HttpExchange<REQ, RESP> {
    ClientResponse<RESP> exchange(HttpClientRequest<REQ> requestMessage);
}
