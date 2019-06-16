package cn.wolfcode.shop.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;


@Component
public class CreateOrderProductMessage {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination createOrderQueue;

    /**
     * 发送消息
     * @param content
     */
    public void sendMessage(String content){
        jmsTemplate.convertAndSend(createOrderQueue,content);
    }
}
