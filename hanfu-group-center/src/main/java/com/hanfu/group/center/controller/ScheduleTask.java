package com.hanfu.group.center.controller;


import com.hanfu.group.center.manual.model.GroupOpen;
import com.hanfu.group.center.service.GroupOpenConnectService;
import com.hanfu.group.center.service.GroupOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/17
 * @time: 15:21
 */

@Controller
public class ScheduleTask {
    @Autowired
    GroupOpenService groupOpenService;
    @Autowired
    GroupOpenConnectService groupOpenConnectService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void executeCorpTask1(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format1 = formatter.format(date);
        Long in2 = Long.valueOf(format1);
        List<Date> stopTime = groupOpenService.getStopTime();
        for (Date  time:stopTime) {
            String format = formatter.format(time);
            String timestamp = formatter1.format(time);
            Date startTime1 = null;
            try {
                startTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long stop = Long.valueOf(format);
            if(in2>stop){
               GroupOpen groupOpen  = groupOpenService.selectStopTime(startTime1);
                Integer groupOpenId = groupOpen.getId();
                List<Integer> user = groupOpenService.selectUserId(groupOpen.getGroupId());
                for (Integer  id:user) {
                    groupOpenConnectService.updateState(id,groupOpenId);
                }
                groupOpenService.updateByIsDeleted(groupOpenId);

//                调退款接口
            }

        }

    }

}
