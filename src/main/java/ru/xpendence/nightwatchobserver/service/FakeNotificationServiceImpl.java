package ru.xpendence.nightwatchobserver.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.CreateCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;
import ru.xpendence.nightwatchobserver.entity.User;
import ru.xpendence.nightwatchobserver.entity.WallPost;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 03.03.19
 * Time: 10:24
 * e-mail: 2262288@gmail.com
 */
@Service
public class FakeNotificationServiceImpl implements FakeNotificationService {

    private final WallPostService wallPostService;
    private final VkApiClient vk;

    @Autowired
    public FakeNotificationServiceImpl(WallPostService wallPostService,
                                       VkApiClient vk) {
        this.wallPostService = wallPostService;
        this.vk = vk;
    }

    @Override
    @Transactional
    public void postMessage(ToRecognitionDto dto) {
        WallPost wallPost = wallPostService.getById(dto.getPostId());
        User user = wallPost.getUser();
        UserActor userActor = new UserActor(user.getUserId(), user.getAccessToken().getAccessToken());
        CreateCommentResponse response;
        try {
            response = vk.wall()
                    .createComment(userActor, wallPost.getOriginalPostId())
                    .ownerId(wallPost.getOriginalOwnerPostId())
                    .message("Это объявление является мошенническим.")
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to send request.");
        }
        Integer commentId = response.getCommentId();
    }
}
