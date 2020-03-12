package com.hanfu.payment.center.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.github.wxpay.sdk.WXPayConfig;

public class MiniProgramConfig implements WXPayConfig {

    private byte[] certData;
    
    public MiniProgramConfig() throws IOException {
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("payment/cert/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }
    
    @Override
    public String getAppID() {
        return "wx2641aaa105c07dd4";
    }

    @Override
    public String getMchID() {
        // TODO Auto-generated method stub
        return "1574620741";
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return "tjsichuang0827abcdef199509abcdef";
    }

    @Override
    public InputStream getCertStream() {
        // TODO Auto-generated method stub
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        // TODO Auto-generated method stub
        return 0;
    }

}
