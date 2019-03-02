package ru.xpendence.nightwatchobserver;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
//@ComponentScan(basePackages = {
//        "ru.xpendence.nightwatchobserver.controller",
//        "ru.xpendence.nightwatchobserver.dto",
//        "ru.xpendence.nightwatchobserver.mapper",
//        "ru.xpendence.nightwatchobserver.repository",
//        "ru.xpendence.nightwatchobserver.service",
//})
@PropertySource(value = {
        "classpath:vk.properties",
        "classpath:kafka.properties"
})
@EnableScheduling
public class NightWatchObserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NightWatchObserverApplication.class, args);
    }

    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }
}
