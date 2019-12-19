package com.hanfu.cancel.service;

import com.hanfu.cancel.model.record;

import java.time.LocalDateTime;
import java.util.List;

public interface CancelService {
    List<record> select();
    List<record> selectDate(LocalDateTime createData, LocalDateTime create1);
    List<record> selectRegion(String site);
    List<record> selectCancelId(int cancelId);
}
