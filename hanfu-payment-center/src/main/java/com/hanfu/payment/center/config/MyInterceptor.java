package com.hanfu.payment.center.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hanfu.payment.center.dao.AccountMapper;
import com.hanfu.payment.center.dao.PayBossMapper;
import com.hanfu.payment.center.model.Account;
import com.hanfu.payment.center.model.AccountExample;
import com.hanfu.payment.center.model.PayBoss;
import com.hanfu.payment.center.model.PayBossExample;
import com.hanfu.user.center.dao.HfStoneMapper;
import com.hanfu.user.center.model.HfStone;
import com.hanfu.user.center.service.impl.Permission;
import com.hanfu.user.center.utils.Decrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class MyInterceptor implements HandlerInterceptor {

    Permission permissionService = new Permission();
    @Autowired
    private PayBossMapper payBossMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private HfStoneMapper hfStoneMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
//    @Autowired
//    private HfAdminMapper hfAdminMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        if (cookies==null){
//            return false;
//        }
        logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(),request.getRequestURI());
        Object token= request.getHeader("token");
        System.out.println("------------------");
        System.out.println(token+"我是wdtk");
        Integer userId=1;
        Integer AccId = 1;
        if (token!=null){
            System.out.println(token+"我是wdtk");
            Decrypt decrypt = new Decrypt();
            DecodedJWT jwt = decrypt.deToken((String) token);
            if (jwt.getClaim("Type").asString().equals("user")){
                Object bossId= request.getHeader("bossId");
                System.out.println(bossId+"我是boss");
                request.getServletContext().setAttribute("getServletContext", bossId);
                return true;
            }
            //
            String type = jwt.getClaim("Type").asString();
            AccountExample accountExample = new AccountExample();
            accountExample.createCriteria().andUserIdEqualTo(Integer.valueOf(jwt.getClaim("userId").asInt())).andIsDeletedEqualTo(0).andAccountTypeEqualTo(type).andMerchantIdEqualTo(jwt.getClaim("merId").asInt());
            List<Account> accounts= accountMapper.selectByExample(accountExample);
            userId=accounts.get(0).getId();
            AccId = accounts.get(0).getId();
            if (redisTemplate.opsForValue().get(String.valueOf(AccId)+"token")==null){
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "无权限");
                return false;
            }
            if (!Objects.equals(redisTemplate.opsForValue().get(String.valueOf(AccId)+"token"), token)){
                System.out.println(redisTemplate.opsForValue().get(String.valueOf(AccId)+"token"));
                System.out.println("此账号在别处登陆了");
                System.out.println(redisTemplate.opsForValue().get(String.valueOf(AccId)+"token"));
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "在别处登陆了");
                return false;
            }
        }
        logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(),request.getRequestURI());

        if (permissionService.hasPermission(request,response,handler,userId)==true) {
            if (token!=null){
                Decrypt decrypt = new Decrypt();
                DecodedJWT jwt = decrypt.deToken((String) token);
                String type = jwt.getClaim("Type").asString();
                AccountExample accountExample = new AccountExample();
                accountExample.createCriteria().andUserIdEqualTo(Integer.valueOf(jwt.getClaim("userId").asInt())).andIsDeletedEqualTo(0).andAccountTypeEqualTo(type).andMerchantIdEqualTo(jwt.getClaim("merId").asInt());
                List<Account> accounts= accountMapper.selectByExample(accountExample);
                if (accounts.size()==0){
                    response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
                }
                request.getServletContext().setAttribute("getServletContext", accounts.get(0).getMerchantId());
                request.getServletContext().setAttribute("getServletContextType", type);
                if (type.equals("boss")){
                    request.getServletContext().setAttribute("bossId", accounts.get(0).getMerchantId());
                } else if (type.equals("stone")) {
                    HfStone hfStone = hfStoneMapper.selectByPrimaryKey(accounts.get(0).getMerchantId());
                    request.getServletContext().setAttribute("bossId", hfStone.getBossId());
                }
            }
            return true;
        }
//        response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        logger.info("====拦截到了方法：{}，在该方法执行之前执行====", methodName);
        System.out.println(handler);
        System.out.println(request);
        // 返回 true 才会继续执行，返回 false 则取消当前请求
        response.setStatus(403);
        return false;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        request.getServletContext().removeAttribute("getServletContext");
        request.getServletContext().removeAttribute("getServletContextType");
        request.getServletContext().removeAttribute("bossId");
        logger.info("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
    }
}