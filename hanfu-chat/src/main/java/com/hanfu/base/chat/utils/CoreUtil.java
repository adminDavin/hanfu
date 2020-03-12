package com.hanfu.base.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.hanfu.base.chat.model.Message;

public class CoreUtil {
    public static void sort(List<Message> list) {
        list.sort(Comparator.comparing(Message::getTime));
    }

    /**
     * format date
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
