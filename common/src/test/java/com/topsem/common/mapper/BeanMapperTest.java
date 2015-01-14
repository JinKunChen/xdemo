package com.topsem.common.mapper;

/**
 * @author Chen on 14-12-8.
 */


public class BeanMapperTest {

    public static void main(String[] args) {
        Module source = new Module("module1", "desc1", null);
        Module newModule = new Module("module1", "desc2", "icon2");
        BeanMapper.copy(source, newModule);
        System.out.println(newModule.getDescription());
        System.out.println(newModule.getIcon());
    }
}
