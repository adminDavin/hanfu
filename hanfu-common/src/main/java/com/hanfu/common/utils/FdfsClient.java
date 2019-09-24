package com.hanfu.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

import com.alibaba.fastjson.JSON;

public class FdfsClient {
	
	public static void main(String[] args) throws IOException, MyException {
		ClientGlobal.init("fdfs_client.conf");
		TrackerGroup trackerGroup = ClientGlobal.g_tracker_group;
        TrackerClient trackerClient = new TrackerClient(trackerGroup);
   	 	File file = new  File("C:\\Users\\administor\\Pictures\\a.png");
   		FileInputStream fis = new  FileInputStream(file);
   		TrackerServer trackerServer = trackerClient.getConnection();
   		StorageServer storage = new StorageServer("47.105.72.216", 23000, 0);
   		System.out.println(storage.getInetSocketAddress());
   		StorageClient client = new StorageClient(trackerServer, storage);
	   	byte[] file_buff = null;
        if (fis != null) {
            int len = fis.available();
            file_buff = new byte[len];
            fis.read(file_buff);
        }
        fis.close();
		String[] fileid = client.upload_file(file_buff, "5", null);
		byte[] f = client.download_file("group1", "M00/00/00/rB8WNl2JvYGAEwT_AAJY7HS4zkE62123.5");
		client.delete_file("group1", "M00/00/00/rB8WNl2Jvg2AUiP4AAJY7HS4zkE97756.5");
		System.out.println(client.get_file_info(fileid[0], fileid[1]));
		System.out.println(JSON.toJSON(fileid));
	}
	
	private static StorageClient client = null;
	
	static {
		try {
			ClientGlobal.init("fdfs_client.conf");
			TrackerGroup trackerGroup = ClientGlobal.g_tracker_group;
	        TrackerClient trackerClient = new TrackerClient(trackerGroup);
	        TrackerServer trackerServer = trackerClient.getConnection();
	   		StorageServer storage = new StorageServer("47.105.72.216", 23000, 0);
	        client = new StorageClient(trackerServer, storage);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] fileMange(FileInputStream fis, String userId) throws IOException, MyException {
		return client.upload_file(FdfsClient.streamToByte(fis), userId, null);
	}
	
	public static byte[] fileMange(String group_name, String remoteFilename) throws IOException, MyException {
		return client.download_file(group_name, remoteFilename);
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
