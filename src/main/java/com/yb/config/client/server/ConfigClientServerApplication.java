package com.yb.config.client.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@ RefreshScope 注解会在配置中心配置改变的时候 手动访问本项目的/refresh路径，就会实现自动刷新配置文件，重新加载配置文件中的数据
@RefreshScope//这个注解和@RestController/@Controller在一起用,这里是为了简便才在启动类这里写的
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientServerApplication{

    //如果引入ribbon会比较友好(比较好封装成自己想要的形式),而不引入ribbon直接用这个比较简单
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientServerApplication.class, args);
    }

    @Value("${aa}")
    private String aa;

    @GetMapping("/a")
    public String a(){
        //微服务名可以用大写或者小写CONFIG-SERVER/config-server都可以
        Object forObject = restTemplate().getForObject("http://config-server/config-server/dev", Object.class);
        System.err.println(forObject);
        return aa;
    }

}
