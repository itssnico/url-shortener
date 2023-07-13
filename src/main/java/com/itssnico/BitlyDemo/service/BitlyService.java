package com.itssnico.BitlyDemo.service;

import com.opsmatters.bitly.Bitly;
import com.opsmatters.bitly.api.model.v4.CreateBitlinkRequest;
import com.opsmatters.bitly.api.model.v4.CreateBitlinkResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service // to indicate this is the service of the application
public class BitlyService {

    @Value("${BIITLY_TOKEN}")
    String BIITLY_TOKEN; // here were mapping the token from the properties file

    private Bitly client;

    @PostConstruct
    public void setUp(){
        client = new Bitly(BIITLY_TOKEN);
    }
    public String getShortURL(String longUrl){
        String link = "error"; //setting this as default

        try{
            CreateBitlinkResponse response = client.bitlinks().shorten(longUrl).get();

            link = response.getLink();
            //in this try block were converting the long into short by
            // first getting the longURL and shorten him and giving back the
            // shortURL in the response and getting the link
        }
        catch (Exception e) {
        }

        return link;
    }

}
