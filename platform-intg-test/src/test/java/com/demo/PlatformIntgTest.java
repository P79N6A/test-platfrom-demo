package com.demo;

import com.demo.learn.common.client.http.HttpClientRequest;
import com.demo.learn.common.header.http.HttpHeader;
import com.demo.learn.template.HttpTemplate;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by temper on 2019/3/24,下午4:38.
 * copy as you like, but with these word.
 * at last, The forza horizon is really fun, buy is made, looking forward to driving together in the hurricane.
 */
public class PlatformIntgTest {
    HttpTemplate<Person, Person> template;

    @Before
    public void init() {
        template = new HttpTemplate<>();
    }

    @Test
    public void testPost() throws URISyntaxException {
        HttpClientRequest<Person> person = HttpClientRequest.post(new URI("http://localhost:8080/demo")).header(new HttpHeader().contentType("application/json")).body(new Person("sinePost", 20));
        Person result = template.exchange(person, Person.class).getBody();
        System.out.println(result);

    }

    @Test
    public void testGet() throws URISyntaxException {
        HttpClientRequest request =  HttpClientRequest.get(new URI("http://localhost:8080/demo")).build();
        System.out.println(template.exchange(request, Person.class).getBody());
    }



}
