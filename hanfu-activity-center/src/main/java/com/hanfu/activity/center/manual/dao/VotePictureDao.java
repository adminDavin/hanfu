package com.hanfu.activity.center.manual.dao;

import com.hanfu.activity.center.manual.model.FileDesc;

public interface VotePictureDao {

    Integer insertFileDesc(FileDesc fileDesc);

    FileDesc selectFileDesc(Integer fileDescId);
}
