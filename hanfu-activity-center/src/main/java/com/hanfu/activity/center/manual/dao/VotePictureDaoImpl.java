package com.hanfu.activity.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.activity.center.manual.model.FileDesc;
import com.hanfu.activity.center.manual.model.HfUser;

@Repository
public class VotePictureDaoImpl implements VotePictureDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Integer insertFileDesc(FileDesc fileDesc) {
        Integer result = sqlSessionTemplate.insert("insertFileDesc", fileDesc);
        return result;
    }

    @Override
    public FileDesc selectFileDesc(Integer fileDescId) {
        FileDesc result = sqlSessionTemplate.selectOne("selectFileDesc", fileDescId);
        return result;
    }

}
