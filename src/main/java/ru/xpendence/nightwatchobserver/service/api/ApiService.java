package ru.xpendence.nightwatchobserver.service.api;

import com.vk.api.sdk.client.actors.UserActor;
import org.springframework.transaction.annotation.Transactional;
import ru.xpendence.nightwatchobserver.dto.AccessTokenDto;
import ru.xpendence.nightwatchobserver.dto.ToRecognitionDto;
import ru.xpendence.nightwatchobserver.entity.AccessToken;
import ru.xpendence.nightwatchobserver.entity.User;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 26.02.19
 * Time: 22:55
 * e-mail: 2262288@gmail.com
 */
public interface ApiService {

    /**
     * Получение access-токена для последующей авторизации.
     *
     * Предварительно пользователь переходит по ссылке, в ответ на которую на сервер прилетает код авторизации. По этому коду
     * сервис авторизуется в VK API и получает токен с определённым сроком действия (в зависимости от запроса, срок может быть
     * вечным). Данный токен далее используется для всех действий со стороны сервиса от имени клиента.
     *
     * Если авторизация неудачна, код сохраняется в базу для последующих попыток авторизации.
     *
     * @param code код авторизации. Срок "жизни" - 1 час.
     * @return полученный и сохранённый в базу токен.
     */
    AccessTokenDto authByCode(String code);

    /**
     * Сам бизнес-процесс получения токена через VK API.
     *
     * Если процесс неудачный, выбрасывается исключение, в котором код авторизации записывается в базу.
     *
     * @param code код авторизации.
     * @return пользователя, сгенерированного на основе данных, полученных из VK.
     */
    User authorize(String code);

    /**
     * Сканирование стены пользователя для выявления новых постов на стене.
     *
     * @param userId ID пользователя в БД приложения.
     * @return удачное сканирование или нет.
     */
    @Transactional
    Boolean scan(Long userId);

    /**
     * Метод определяет, не истёк ли срок годности у токена. Если истёк, сервис запускает процесс получения нового токена.
     */
    boolean validateAccessToken(AccessToken accessToken);

    UserActor getActorByUserId(Long userId);

    void send(ToRecognitionDto dto);

    void checkIfUserExists(Integer authResponse, User user);
}
