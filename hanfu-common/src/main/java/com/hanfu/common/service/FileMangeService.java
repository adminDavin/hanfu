package com.hanfu.common.service;

import java.io.FileInputStream;
import java.io.IOException;

import org.csource.common.MyException;
import org.springframework.stereotype.Service;

import com.hanfu.common.utils.FdfsClient;

public class FileMangeService {

    public String[] uploadFile(FileInputStream fis, String userId) {
        String[] fileid = null;
        try {
            fileid = FdfsClient.uploadFile(fis, userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return fileid;
    }

    public String[] uploadFile(byte[] fis, String userId) {
        String[] fileid = null;
        try {
            fileid = FdfsClient.uploadFile(fis, userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return fileid;
    }

    public byte[] downloadFile(String group_name, String remoteFilename) {
        byte[] fileid = null;
        try {
            fileid = FdfsClient.downloadFile(group_name, remoteFilename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return fileid;
    }

    public void deleteFile(String group_name, String remoteFilename) {
        try {
            FdfsClient.deleteFile(group_name, remoteFilename);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
