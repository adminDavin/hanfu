package com.hanfu.common.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.FileInputStream;
import java.io.IOException;


public class FdfsClient {

//	public static void main(String[] args) throws IOException, MyException {
//		ClientGlobal.init("fdfs_client.conf");
//		TrackerGroup trackerGroup = ClientGlobal.g_tracker_group;
//        TrackerClient trackerClient = new TrackerClient(trackerGroup);
//   	 	File file = new  File("C:\\Users\\123\\Desktop\\timg.jpg");
//   		FileInputStream fis = new  FileInputStream(file);
//   		TrackerServer trackerServer = trackerClient.getConnection();
//   		StorageServer storage = new StorageServer("172.26.16.97", 23000, 0);
//   		System.out.println(storage.getInetSocketAddress());
//   		StorageClient client = new StorageClient(trackerServer, storage);
//	   	byte[] file_buff = null;
//        if (fis != null) {
//            int len = fis.available();
//            file_buff = new byte[len];
//            fis.read(file_buff);
//        }
//        fis.close();
//		String[] fileid = client.upload_file(file_buff, "5", null);
//		byte[] f = client.download_file("group1", "M00/00/00/rB8WNl2JvYGAEwT_AAJY7HS4zkE62123.5");
//		client.delete_file("group1", "M00/00/00/rB8WNl2Jvg2AUiP4AAJY7HS4zkE97756.5");
//		System.out.println(client.get_file_info(fileid[0], fileid[1]));
//		System.out.println(JSON.toJSON(fileid));
//	}

    private static StorageClient client = null;

    static {
        try {
            ClientGlobal.init("fdfs_client.conf");
            TrackerGroup trackerGroup = ClientGlobal.g_tracker_group;
            TrackerClient trackerClient = new TrackerClient(trackerGroup);
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storage = new StorageServer("39.100.237.144", 23000, 0);
            client = new StorageClient(trackerServer, storage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public static String[] uploadFile(FileInputStream fis, String userId) throws IOException, MyException {
        return client.upload_file(FdfsClient.streamToByte(fis), userId, null);
    }

    public static String[] uploadFile(byte[] fis, String userId) throws IOException, MyException {
        return client.upload_file(fis, userId, null);
    }

    public static byte[] downloadFile(String group_name, String remoteFilename) throws IOException, MyException {
        return client.download_file(group_name, remoteFilename);
    }

    public static void deleteFile(String group_name, String remoteFilename) throws IOException, MyException {
        client.delete_file(group_name, remoteFilename);
    }

    public static byte[] streamToByte(FileInputStream fis) throws IOException {
        byte[] file_buff = null;
        if (fis != null) {
            int len = fis.available();
            file_buff = new byte[len];
            fis.read(file_buff);
        }
        return file_buff;
    }

}
