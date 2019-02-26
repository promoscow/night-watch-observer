package ru.xpendence.nightwatchobserver;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
public class NightWatchObserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NightWatchObserverApplication.class, args);
    }

    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }
}
