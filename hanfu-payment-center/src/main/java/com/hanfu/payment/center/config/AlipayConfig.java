package com.hanfu.payment.center.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.hanfu.payment.center.dao.PayBossMapper;
import com.hanfu.payment.center.model.PayBossExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付的参数配置
 *
 * @author mengday zhang
 */
@Component
@ConfigurationProperties()
public class AlipayConfig {
    @Autowired
    private PayBossMapper payBossMapper;
    private Integer bossId=getBossId();
    /**
     * 支付宝gatewayUrl
     */
    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    /**
     * 商户应用id
     */
    private String appid;
//    private String appid = "2021000116677309";
    /**
     * RSA私钥，用于对商户请求报文加签
     */
    private String appPrivateKey;
//    private String appPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDAZjCbEeW9Oodg47+SvzibakyGe2cMvHVhdRn7g4Kw4XPMAmhCmAj+VRT0Mmdgf5ziQckFWaMngM3ZfmlJ6CV/JYTDCeTI9Iomi2VgLScGvKViYVv4GYrovwHQmYIucmHNJoPKGRUgrHzJzJewNJUQ9KyzydUNFF5lLNoHxvvNcLqzqluL3+VFKlnYpoZHcizc37sk2+usE+lKgvTWwbKTsNCEs1qDNrXTeR+Ik1AidO/BL6q9nU/49ZajA7ZUYKtJpmc/N+9fU8tmfJmhbdmmNMUrmrrukhYZ9CSu9y+P6U7AfMLJkdcPneMjh9ki488G4wPCSe1r1LR2/r5OljxtAgMBAAECggEAU0Bkq9T0ugrHZY9m59LCZc17S32+O26YxBDtqz1eobcMaixofNhYm+So6nmc7c6KTgT1PNFqnb9IU2I1oPtIrlnHtrVjP+lAlikfNpyTFIEwdv910kcLKmbsHB0wjXSFCUYvJJXx5G9+l7tlr6MxFWMqD6m3/K7hoZnkxhS+BEiQWdWRFCHiR5IXjCRn8E3sKhIEitcYQPkTgLsW87ARzTvSWQF/FrIlUJT9Gal3Fj3TBjcvHLUoiIOuGwjNsASFPF+Pd/5aE2e5kQnvReh6hKVy67jb2Y499IoYFsu57PjVNDRdGKmbm8+xB0/YWbdQbnFnRFupGOgpqWe2z1nXwQKBgQDx5+nVdL9q6yN/RiLiWV2h2xM5ZW0F7Xtr3ylqdyXGD2DNkga3PbyOwh0kGR5XY660e2HgVOeqf5rLDndRbPTo89lWtEaOocGRictWXVc7fDwicySF53/V6AzYSyV5GwMrh2qTkL1eIjIXaDyVtnB1JH0g+RXL4maRAgGupaUd6QKBgQDLm98L3JD0wCRKi+gUMFMnLoTlS2p20JWYytGN2d4QQPQGlmpW5cZzz/94zXIKPjcnP9yn6DqJffBNUOy6egZMKHCkpp9j3b8sgzwskon8DqLu3YxmgyS+TRf8mPAzbRepFyZbqe/3rYf9bXg5GadrhrLkfDozzaFRpS6/CQrD5QKBgHtiMYd5U8TiCCn7iQFwWWybw/Nrgy8Z0PDVU1eFTBP4dgSELb6603jxE1H9Kdei9VIC+IomZD83dKmLSFqUu1oyPjGWI8LOSF/GK5tyYnLpy/Y6Sm+uAysEf8C2m3doyrYiDL8VfTENOgVsWjJ2amelY9k0kuR6JpxhrJ/6xvw5AoGBAI2ihz0EGN34zwYAXte0ahOme5HZrVyranUYGT+4342k8VunxJ9gRXWIqilJmNvhPZKTDRbMJ/Q+5CuUcKije7/8nacbtWLN/U+klvZWlZ/+On85JJKnBadPj7EKXoUXjU71qJw5EZHUZ/X18Zye6OSWldj1RbOFhoN6CZOgYZm1AoGAP8dbH+UbL14AGEbR/mAN4yM0EyqwpQ992OB0C7G7A9mZ5kHPChf7DpdJKxhk5Qkm8QS1Sf5UnqR9xqSWGWkyXUJ9TqSVW0bxZoPnTujCL3ztr/S2GGqieKe41b5Q3VWNIkEQSUC/MfsR5k1HGDf02PXDm5GdjIELHnpLp1K4bsI=";
    /**
     * 支付宝RSA公钥，用于验签支付宝应答
     */
    private String alipayPublicKey;
//    private String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj1HaBsKWjiMEvKSfktgTlNmvfM7Q24S/1IUo0mUtwc2u6SR5Kb2ITCKLOn7alTId3k26FncqVOgG+/Yx3a6EHSD7UDrtvteyp4dCSUYmNwHZEoc9BWz8s9qNvFEzyYj2PmAtFC2LnZW5gbMoY3CgY2M8Qsg3rxHRA12UdYwB4XAaleFf1hpr9jt/fWoUPk8ats4X7nbMQSt3B7qFqa+5AfTWwXNvEsQJ5MkM7ofAwAa3XpBhX2+VBFlrsF4zIMQkwhDAefGg3yHPJNGPao6bDIoIEBw778FhHLrVk5ChNUInWMNK3jjLGXXmICB/O0cSxn907EW6P+9k4yh+pea1OQIDAQAB";
    /**
     * 签名类型
     */
    private String signType = "RSA2";
    /**
     * 格式
     */
    private String formate = "json";
    /**
     * 编码
     */
    private String charset = "UTF-8";
    /**
     * 同步地址
     */
    private String returnUrl;
    /**
     * 异步地址
     */
    private String notifyUrl = "https://www.tjsichuang.cn:1443/api/cart/hf-payment/notify";
    /**
     * 最大查询次数
     */
    private static int maxQueryRetry = 5;
    /**
     * 查询间隔（毫秒）
     */
    private static long queryDuration = 5000;
    /**
     * 最大撤销次数
     */
    private static int maxCancelRetry = 3;
    /**
     * 撤销间隔（毫秒）
     */
    private static long cancelDuration = 3000;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getAppid() {
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(bossId).andLastModifierEqualTo("appalipay");
        return payBossMapper.selectByExample(payBossExample).get(0).getAppid();
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppPrivateKey() {
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(bossId).andLastModifierEqualTo("appalipay");
        return payBossMapper.selectByExample(payBossExample).get(0).getPayKey();
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayPublicKey() {
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(bossId).andLastModifierEqualTo("appalipay");
        return payBossMapper.selectByExample(payBossExample).get(0).getSecret();
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getFormate() {
        return formate;
    }

    public void setFormate(String formate) {
        this.formate = formate;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public static int getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public static void setMaxQueryRetry(int maxQueryRetry) {
        AlipayConfig.maxQueryRetry = maxQueryRetry;
    }

    public static long getQueryDuration() {
        return queryDuration;
    }

    public static void setQueryDuration(long queryDuration) {
        AlipayConfig.queryDuration = queryDuration;
    }

    public static int getMaxCancelRetry() {
        return maxCancelRetry;
    }

    public static void setMaxCancelRetry(int maxCancelRetry) {
        AlipayConfig.maxCancelRetry = maxCancelRetry;
    }

    public static long getCancelDuration() {
        return cancelDuration;
    }

    public static void setCancelDuration(long cancelDuration) {
        AlipayConfig.cancelDuration = cancelDuration;
    }
    public Integer getBossId() {
        if (bossId==null){
            bossId=2;
        }
        return bossId;
    }
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }
//    @Bean
//    public AlipayClient alipayClient(){
//        return new DefaultAlipayClient(this.getGatewayUrl(),
//                this.getAppid(),
//                this.getAppPrivateKey(),
//                this.getFormate(),
//                this.getCharset(),
//                this.getAlipayPublicKey(),
//                this.getSignType());
//    }
}
