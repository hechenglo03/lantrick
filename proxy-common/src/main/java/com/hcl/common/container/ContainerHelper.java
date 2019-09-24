package com.hcl.common.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @Author: hechenglo03
 * @Date:2019/9/23
 * @Description:
 */
public class ContainerHelper {

    public static Logger logger = LoggerFactory.getLogger(ContainerHelper.class);

    private static List<Container> cacheContainers;

    public static void start(List<Container> containers){
        cacheContainers = containers;
        startContainer();



    }

    private static void startContainer(){
        for(Container container:cacheContainers){
            logger.info("开始启动容器"+container.getClass().getName());
            container.start();
            logger.info("启动容器完毕"+container.getClass().getName());
        }
    }


}
