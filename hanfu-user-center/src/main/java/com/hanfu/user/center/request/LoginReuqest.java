package com.hanfu.user.center.request;

import java.io.Serializable;

public class LoginReuqest implements Serializable {
	private String username;
	private String password;
	private String sourceType;
	private String authKey;
}
