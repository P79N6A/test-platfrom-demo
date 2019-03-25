package com.demo;

import com.demo.learn.common.client.http.HttpClientRequest;
import com.demo.learn.common.header.http.HttpHeader;
import com.demo.learn.template.HttpTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by temper on 2019/3/24,下午4:38.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class IntgTest {
    public static void main(String[] args) throws URISyntaxException {
        HttpTemplate<Person, Person> template = new HttpTemplate<>();




        //**************  post  *******************
        HttpClientRequest<Person> person = HttpClientRequest.post(new URI("http://localhost:8080/demo")).header(new HttpHeader().contentType("application/json")).body(new Person("sinePost", 20));
        Person result = template.exchange(person, Person.class).getBody();
        System.out.println(result);


        //************** get  *******************

        HttpClientRequest request =  HttpClientRequest.get(new URI("http://localhost:8080/demo")).build();
        System.out.println(template.exchange(request, Person.class).getBody());


    }



}
