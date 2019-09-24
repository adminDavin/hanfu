package com.hanfu.common.utils;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;

public class FdfsClient {
	
	public static void main(String[] args) throws IOException, MyException {
		ClientGlobal.init("fdfs_client.conf");
	}
}
