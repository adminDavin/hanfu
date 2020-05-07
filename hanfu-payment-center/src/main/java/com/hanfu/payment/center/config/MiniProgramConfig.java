package com.hanfu.payment.center.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hanfu.payment.center.dao.HfOrderMapper;
import com.hanfu.payment.center.dao.PayBossMapper;
import org.apache.commons.io.IOUtils;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MiniProgramConfig implements WXPayConfig {

    private byte[] certData;
    private Integer bossId=1;
@Autowired
private PayBossMapper payBossMapper;
    public MiniProgramConfig() throws IOException {
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("payment/cert/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }
    @Bean
    @Override
    public String getAppID() {

        return payBossMapper.selectByPrimaryKey(bossId).getAppid();
//        return "wx2641aaa105c07dd4";
    }

    @Override
    public String getMchID() {
        // TODO Auto-generated method stub
        return payBossMapper.selectByPrimaryKey(bossId).getMchid();
//        return "1574620741";
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return payBossMapper.selectByPrimaryKey(bossId).getPayKey();
//        return "tjsichuang0827abcdef199509abcdef";
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
