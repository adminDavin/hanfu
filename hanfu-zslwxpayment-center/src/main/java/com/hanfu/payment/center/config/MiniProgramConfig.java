package com.hanfu.payment.center.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class MiniProgramConfig implements WXPayConfig {

    private byte[] certData;
    private String PATH_APP = "payment/cert/apiclient_cert.p12";
//    @Bean
    @Override
    public String getAppID() {

        return "wx25eebf24f781cd35";
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
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH_APP);
        try {
            this.certData = IOUtils.toByteArray(certStream);
            certStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
