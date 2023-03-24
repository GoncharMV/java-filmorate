package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Service
public class UserService {

    private final UserDbStorage userStorage;
    private final FriendDbStorage friendDbStorage;

    public UserService(UserDbStorage userStorage, FriendDbStorage friendDbStorage) {
        this.userStorage = userStorage;
        this.friendDbStorage = friendDbStorage;
    }

    public Collection<User> getUsers() {
        return userStorage.getAll();
    }

    public User getUser(Long id) {
        return userStorage.get(id);
    }

    public List<User> getFriends(Long userId) {
        return friendDbStorage.getFriends(userId);
    }

    public List<User> mutualFriends(Long userId, Long friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        return friendDbStorage.commonFriends(user.getId(), friend.getId());
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        if (Objects.equals(user.getId(), friend.getId())) {
            if (user.getFriends().contains(friend.getId())) {
                throw new NullPointerException("Друг уже добавлен");
            }
            throw new NullPointerException("Нельзя добавить себя в друзья");
        }
        user.getFriends().add(friend.getId());
        friendDbStorage.addFriend(user.getId(), friend.getId());
    }

    public User addUser(User user) {
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public void deleteUser(Long id) {
        userStorage.delete(id);
    }

    public void deleteFriend(Long id, Long friendId) {
        friendDbStorage.deleteFriend(id, friendId);
    }
}
