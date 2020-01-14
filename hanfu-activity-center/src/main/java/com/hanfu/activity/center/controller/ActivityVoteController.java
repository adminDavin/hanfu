package com.hanfu.activity.center.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/activity/vote")
@Api
public class ActivityVoteController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

}
