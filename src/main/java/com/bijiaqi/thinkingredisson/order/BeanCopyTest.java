package com.bijiaqi.thinkingredisson.order;

import cn.hutool.core.bean.BeanUtil;

/**
 * <p>创建时间: 2024/11/28 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
public class BeanCopyTest {
    public static void main(String[] args) {
        Order order = new Order();
        order.setId(1857028013069258761L);

        OrderDTO dto = new OrderDTO();
        dto.setId("1857028013069258761");

        OrderDTO result1 = BeanUtil.copyProperties(order, OrderDTO.class);
        Order result2 = BeanUtil.copyProperties(dto, Order.class);

        System.out.println(result1.getId());
        System.out.println(result2.getId());
    }
}
