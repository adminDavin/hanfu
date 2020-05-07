package com.hanfu.payment.center.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hanfu.payment.center.dao.HfOrderMapper;
import com.hanfu.payment.center.dao.PayBossMapper;
import com.hanfu.payment.center.model.PayBossExample;
import org.apache.commons.io.IOUtils;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MiniProgramConfig implements WXPayConfig {

    private byte[] certData;
    private Integer bossId=1;
    private String PATH_APP;
@Autowired
private PayBossMapper payBossMapper;
    @Bean
    @Override
    public String getAppID() {
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(bossId);
        return payBossMapper.selectByExample(payBossExample).get(0).getAppid();
//        return "wx2641aaa105c07dd4";
    }

    @Override
    public String getMchID() {
        // TODO Auto-generated method stub
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(bossId);
        return payBossMapper.selectByExample(payBossExample).get(0).getMchid();
//        return "1574620741";
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(bossId);
        return payBossMapper.selectByExample(payBossExample).get(0).getPayKey();
//        return "tjsichuang0827abcdef199509abcdef";
    }

    @Override
    public InputStream getCertStream() {
        // TODO Auto-generated method stub
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(bossId);
        PATH_APP=payBossMapper.selectByExample(payBossExample).get(0).getApiclientCert();
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
