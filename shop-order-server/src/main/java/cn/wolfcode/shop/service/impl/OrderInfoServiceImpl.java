package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.mapper.InvoiceMapper;
import cn.wolfcode.shop.mapper.OrderInfoMapper;
import cn.wolfcode.shop.mapper.OrderProductMapper;
import cn.wolfcode.shop.mapper.OrderProductPropertyMapper;
import cn.wolfcode.shop.qo.OrderQueryObject;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.service.*;
import cn.wolfcode.shop.utils.IdGenerateUtil;
import cn.wolfcode.shop.utils.RedisContants;
import cn.wolfcode.shop.vo.CarVo;
import cn.wolfcode.shop.vo.OrderStatusChangeVo;
import cn.wolfcode.shop.vo.OrderVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderInfoServiceImpl implements IOrderInfoService{
    @Reference
    private IUserAddressService        userAddressService;
    @Reference
    private IProductSkuService         productSkuService;
    @Reference
    private IProductService            productService;
    @Autowired
    private OrderInfoMapper            OrderInfoMapper;
    @Autowired
    private OrderProductMapper         orderProductMapper;
    @Autowired
    private OrderProductPropertyMapper orderProductPropertyMapper;
    @Autowired
    private InvoiceMapper              invoiceMapper;
    @Autowired
    private RedisTemplate              redisTemplate;
    @Autowired
    private IOrderActionService orderActionService;
    @Override
    public void createOrder(OrderVo orderVo) {
        //获取登录对象
        UserLogin userLogin = orderVo.getUserLogin();
        //创建订单对象，设置用户id
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userLogin.getId());
        //生成订单编号
        IdGenerateUtil idGenerateUtil = IdGenerateUtil.get();
        String orderSn=idGenerateUtil.nextId()+"";
        orderInfo.setOrderSn(orderSn);
        //通过用户收货地址id获取用户收货地址对象，并设置到订单信息中
        UserAddress userAddress = userAddressService.getById(orderVo.getUserAddressId());
        orderInfo.setAddress(userAddress.getAddress());
        orderInfo.setPhone(userAddress.getPhone());
        orderInfo.setConsignee(userAddress.getConsignee());
        orderInfo.setCountry(userAddress.getCountry());
        orderInfo.setProvince(userAddress.getProvince());
        orderInfo.setCity(userAddress.getCity());
        orderInfo.setDistrict(userAddress.getDistrict());
        orderInfo.setZipcode(userAddress.getZipcode());
        //设置订单的其他信息，比如订单状态，收货状态，物流状态，下单时间等
        orderInfo.setOrderStatus(new Byte("0"));
        orderInfo.setFlowStatus(new Byte("0"));
        orderInfo.setPayStatus(new Byte("0"));
        orderInfo.setOrderTime(new Date());
        orderInfo.setPayType(orderVo.getPay().getPayType());
        //设置订单总金额
        BigDecimal amount = BigDecimal.ZERO;
        orderInfo.setOrderAmount(amount);
        //插入该订单
        OrderInfoMapper.insert(orderInfo);
        //获取购物车商品列表遍历，并生成订单商品明细
        for (CarVo car : orderVo.getCarList()) {
        //car的信息转移到订单商品明细中
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(orderInfo.getId());
            orderProduct.setSkuId(car.getSkuId());
            orderProduct.setProductNumber(car.getNumber());
        //通过skuId获取sku对象，并设置价格
            ProductSku productSku = productSkuService.getById(car.getSkuId());
               orderProduct.setProductPrice(productSku.getPrice());
        //计算订单商品明细中的商品小计
            orderProduct.setTotalPrice(orderProduct.getProductPrice().multiply(new BigDecimal(orderProduct.getProductNumber())));
        //把订单商品明细中的商品小计累加到订单总额中
            amount = amount.add(orderProduct.getTotalPrice());
            Product product = productService.getProductById(productSku.getProductId());
            orderProduct.setProductName(product.getName());
            orderProduct.setProductImg(product.getImage());
        //插入该订单商品明细
            orderProductMapper.insert(orderProduct);
        //遍历sku属性集合，并设置到订单商品明细属性对象中，插入该订单商品明细属性数据
            productSku.getProductSkuPropertyList().forEach(productSkuProperty -> {
                OrderProductProperty orderProductProperty = new OrderProductProperty();
                orderProductProperty.setOrderProductId(orderProduct.getOrderId());
                orderProductProperty.setName(productSkuProperty.getSkuProperty().getName());
                orderProductProperty.setValue(productSkuProperty.getValue());
                orderProductPropertyMapper.insert(orderProductProperty);
            });
        //判断Redis中有无该购物车数据，如果有则删除
            String carKey = RedisContants.USER_CAR
                    .replace("USERID", userLogin.getId() + "")
                    .replace("PRODUCTID", productSku.getProductId() + "")
                    .replace("SKUID", productSku.getId() + "");
            redisTemplate.delete(carKey);
        }
        //从新设置订单总额
        orderInfo.setOrderAmount(amount);
        //更新该订单
        OrderInfoMapper.updateByPrimaryKey(orderInfo);
        //判断是否需要开发票，如果要则,设置发票人和发票对应的订单，并插入发票信息
        Invoice invoice = orderVo.getInvoice();
        if(invoice!=null){
            invoice.setUserId(userLogin.getId());
            invoice.setOrderId(orderInfo.getId());
            invoiceMapper.insert(invoice);
        }
    }

    @Override
    public PageResult query(OrderQueryObject qo) {
        int count = OrderInfoMapper.queryCount(qo);
        if(count == 0){
            return PageResult.empty();
        }
        List<OrderInfo> orderInfoList = OrderInfoMapper.queryList(qo);

        return new PageResult(orderInfoList,count,qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public OrderInfo getById(Long id) {
        return OrderInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void changeStatus(OrderStatusChangeVo vo) {
        OrderInfo orderInfo = OrderInfoMapper.selectByPrimaryKey(vo.getOrderId());
        switch(vo.getChangeType()){
            case 1:
                orderInfo.setOrderStatus(new Byte("1"));
                break;
            case 2:
                orderInfo.setFlowStatus(new Byte("1"));
                break;
            case 3:
                orderInfo.setFlowStatus(new Byte("4"));
                break;
            case 5:
                orderInfo.setOrderStatus(new Byte("2"));
                orderInfo.setFlowStatus(new Byte("2"));
                break;

        }
        OrderInfoMapper.updateByPrimaryKey(orderInfo);
        orderActionService.addOrderAction(orderInfo,vo.getUserName(),vo.getPlace(),vo.getNote());
    }
}
