package com.hanfu.user.center.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WxLoginConfig {
	public static String APPID ="wx2641aaa105c07dd4";
	public static String SECRET ="fb26dde971b62de61c4573b12bd5f5da";
	public static String GRANTTYPE = "authorization_code";
    public static CloseableHttpClient client = HttpClients.createDefault();

	public static enum AuthType{
	    WECHART("1"),
	    phone("2");
	    private String authType;
	    AuthType(String authType) {
            this.authType = authType;
        }
	    
	    public String getAuthType() {
	        return this.authType;
	    }
	}
	
	public static enum LoginType {
	    ACTIVITY("activity") {
	        @Override
	        public WechartProPerties getWechartConfig() {
	            return new WechartProPerties().addAppId("wx2641aaa105c07dd4").addSecret("fb26dde971b62de61c4573b12bd5f5da").addGrantType("authorization_code");
	        }
	    },
	    SINGLE_MERCHART("singleMerchart") {
            @Override
            public WechartProPerties getWechartConfig() {
                return new WechartProPerties().addAppId("wx2641aaa105c07dd4").addSecret("fb26dde971b62de61c4573b12bd5f5da").addGrantType("authorization_code");
            }
        };
	    
	    private String name;
	    
	    LoginType(String name) {
	        this.name = name;
	    }
	    
	    public abstract WechartProPerties getWechartConfig();
	    
	    public static LoginType getLoginType(String name) {
	        if (name == null) {
	            return LoginType.ACTIVITY;
	        }
	        for (LoginType loginType : LoginType.values()) {
	            if (name.equalsIgnoreCase(loginType.name)) {
	                return loginType;
	            }
	        }
	        return LoginType.ACTIVITY;
	    }
	}
	
	public static class WechartProPerties {
	    private String appId;
	    private String secret;
	    private String grantType;
        public String getAppId() {
            return appId;
        }
        public WechartProPerties addAppId(String appId) {
            this.appId = appId;
            return this;
        }
        public String getSecret() {
            return secret;
        }
        public WechartProPerties addSecret(String secret) {
            this.secret = secret;
            return this;
        }
        public String getGrantType() {
            return grantType;
        }
        public WechartProPerties addGrantType(String grantType) {
            this.grantType = grantType;
            return this;
        }
	}
	
    public static JSONObject parseWechart(String encryptedData, String sessionKey, String iv)
            throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] dataByte = Base64.decode(encryptedData);
        byte[] keyByte = Base64.decode(sessionKey);
        byte[] ivByte = Base64.decode(iv);
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));

        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        String result = new String(resultByte, "UTF-8");
        return JSON.parseObject(result);
    }
    
    public static JSONObject getSessionKeyOrOpenId(String code, String appName) throws ParseException, IOException {
        WechartProPerties wechartConfig = LoginType.getLoginType(appName).getWechartConfig();
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("appid", wechartConfig.getAppId()));
        list.add(new BasicNameValuePair("secret", wechartConfig.getSecret()));
        list.add(new BasicNameValuePair("grant_type", wechartConfig.getGrantType()));
        list.add(new BasicNameValuePair("js_code", code));

        String params = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session" + "?" + params);

        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, Consts.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(result);
        response.close();
        return jsonObject;
    }
}