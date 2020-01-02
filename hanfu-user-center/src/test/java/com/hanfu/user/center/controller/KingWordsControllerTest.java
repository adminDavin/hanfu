package com.hanfu.user.center.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(KingWordsController.class)
@EnableAutoConfiguration
public class KingWordsControllerTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MockMvc mvc;

//    @Test
//    public void home() throws Exception {
//		this.mvc.perform(get("/kw/users").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
//	}

}
