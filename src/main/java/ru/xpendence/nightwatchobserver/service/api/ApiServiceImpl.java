package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.VkApiClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 26.02.19
 * Time: 22:56
 * e-mail: 2262288@gmail.com
 */
public class ApiServiceImpl implements ApiService {

    @Autowired
    private VkApiClient client;

    @Override
    public void test() {
    }
}
