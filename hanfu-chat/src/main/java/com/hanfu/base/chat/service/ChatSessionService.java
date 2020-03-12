package com.hanfu.base.chat.service;

import java.util.List;

import com.hanfu.base.chat.model.HfUser;
import com.hanfu.base.chat.model.Message;

public interface ChatSessionService {
    HfUser findById(String userId);

    void pushMessage(String fromId, String toId, String message);

    List<HfUser> onlineList();

    List<Message> commonList();

    List<Message> selfList(String fromId, String toId);

    void delete(String id);
}
