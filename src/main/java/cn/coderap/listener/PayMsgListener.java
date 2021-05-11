package cn.coderap.listener;

import cn.coderap.pojo.PayInfo;
import cn.coderap.service.IOrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 关于PayInfo，生产中的正确操作：
 * 不应该在mall项目中再写一次PayInfo，而是pay项目提供一个jar包，供mall引入使用。
 * 由于没有使用jar包，下面涉及到的支付状态，只能先使用硬编码了
 * Created by yw
 * 2021/5/11
 */
@RabbitListener(queues = {"payNotify"})
@Component
@Slf4j
public class PayMsgListener {

    @Resource
    private IOrderService orderService;

    @RabbitHandler
    public void handle(String msg) {
        log.info("【接收到消息】=> {}", msg);
        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        //如果支付状态是成功，就修改订单的状态为已支付
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
