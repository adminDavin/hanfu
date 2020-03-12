package com.hanfu.base.chat.request;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import com.hanfu.base.chat.constant.CommonConstant;
import com.hanfu.base.chat.model.HfUser;
import com.hanfu.base.chat.service.ChatSessionService;

public class ScheduleTaskConfig {
    private static final Long MINUTE_30 = 1000 * 60 * 30L;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChatSessionService chatSessionService;

    @Scheduled(cron = "0 */30 * * * ?")
    private void clearUser() {
        log.info("定时任务 >>>>>>>>>> 清除注册时间超过30分钟的账户，以及其会话信息");

        List<HfUser> userList = chatSessionService.onlineList();
        userList.forEach(user -> {
            if ((new Date().getTime() - user.getId()) >= MINUTE_30) {
                chatSessionService.delete(user.getId().toString());
                if (redisTemplate.boundValueOps(CommonConstant.CHAT_COMMON_PREFIX + user.getId()).get() != null) {
                    redisTemplate.delete(CommonConstant.CHAT_COMMON_PREFIX + user.getId());
                }
                if (redisTemplate.boundValueOps(CommonConstant.CHAT_FROM_PREFIX + user.getId()).get() != null) {
                    redisTemplate.delete(CommonConstant.CHAT_FROM_PREFIX + user.getId());
                }
            }
        });
    }
}
