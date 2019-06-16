package cn.wolfcode.shop.message;

import cn.wolfcode.shop.service.IOrderInfoService;
import cn.wolfcode.shop.vo.OrderVo;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class CreateOrderConsumerMessage {

    @Autowired
    private IOrderInfoService orderInfoService;
    /**
     * 监听消息中心的消息
     * @param message
     * @throws JMSException
     */
    @JmsListener(destination = "createOrderMessage" ,containerFactory = "jmsListenerContainerQueue")
    public void listenerMessage(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String content = textMessage.getText();
        OrderVo orderVo = JSON.parseObject(content, OrderVo.class);
        orderInfoService.createOrder(orderVo);
    }
}
