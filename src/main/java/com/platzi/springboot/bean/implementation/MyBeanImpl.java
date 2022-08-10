package com.platzi.springboot.bean.implementation;

public class MyBeanImpl implements MyBean {

    @Override
    public String hello() {
        return "Hello from my BeanImpl";
    }
}
