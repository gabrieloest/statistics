package com.n26.statistics.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TransactionControllerIntegrationTest
{

    private final String URL = "/transactions";
    private final String TRANSACTION = this.getJsonFromResources();

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;


    @Before
    public void setUp()
    {

        MockitoAnnotations.initMocks(this);
        this.mvc =
            MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }


    @Test
    public void failToCreatetransaction() throws Exception
    {

        this.mvc
            .perform(
                MockMvcRequestBuilders
                    .post(this.URL).contentType(MediaType.APPLICATION_JSON)
                    .content(this.TRANSACTION.replace("1532231340439", "-1532231340439")))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void successToCreateTransaction() throws Exception
    {
        Instant instant = Instant.now();
        String millis = String.valueOf(instant.toEpochMilli());

        this.mvc
            .perform(
                MockMvcRequestBuilders
                    .post(this.URL).contentType(MediaType.APPLICATION_JSON)
                    .content(this.TRANSACTION.replace("1532231340439", millis)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void successToCreatePastTransaction() throws Exception
    {
        this.mvc
            .perform(
                MockMvcRequestBuilders
                    .post(this.URL).contentType(MediaType.APPLICATION_JSON)
                    .content(this.TRANSACTION))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    public void getAlltransactions() throws Exception
    {
        this.mvc
            .perform(MockMvcRequestBuilders.get(this.URL))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    private String getJsonFromResources()
    {

        String result;

        ClassLoader classLoader = getClass().getClassLoader();

        InputStream vendorJsonAsStream = classLoader.getResourceAsStream("transaction-dto.json");
        result =
            new BufferedReader(new InputStreamReader(vendorJsonAsStream))
                .lines()
                .collect(Collectors.joining("\n"));

        return result;
    }
}
