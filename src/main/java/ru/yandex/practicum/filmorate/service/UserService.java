package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getUsers() {
        return userStorage.getAll();
    }

    public User getUser(Long id) {
        return userStorage.get(id);
    }

    public List<User> getFriends(Long userId) {
        List<User> friendsList = new ArrayList<>();
        User user = userStorage.get(userId);
        Set<Long> friends = user.getFriends();
        for (Long friend : friends) {
            User f = userStorage.get(friend);
            friendsList.add(f);
        }
        return friendsList;
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        List<User> userFriends = getFriends(id);
        List<User> otherFriends = getFriends(otherId);
        List<User> commonList = new ArrayList<>();

        for (User friend : userFriends) {
            if (otherFriends.contains(friend)) {
                commonList.add(friend);
            }
        }
        return commonList;
    }

    public Set<Long> addFriend(Long id, Long friendId) {
        addToFriendList(friendId, id);
        return addToFriendList(id, friendId);
    }

    public User addUser(User user) {
        validationCheck(user);
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        validationCheck(user);
        return userStorage.update(user);
    }

    public void deleteFriend(Long id, Long friendId) {
        removeFromFriendList(id, friendId);
        removeFromFriendList(friendId, id);
    }

    private void validationCheck(User newUser) {
        String newEmail = newUser.getEmail();
        Collection<User> usersList= userStorage.getAll();
        for (User user : usersList) {
            String email = user.getEmail();
            if (newEmail.equals(email)) {
                throw new ValidationException("email already exists");
            }
        }
    }

    private Set<Long> addToFriendList(Long id, Long friendId) {
            User user = userStorage.get(id);
            user.getFriends().add(friendId);
            Set<Long> friends = user.getFriends();
            friends.add(friendId);
            user.setFriends(friends);
            return friends;
    }

    private void removeFromFriendList(Long id, Long friendId) {
            User user = userStorage.get(id);
            Set<Long> friends = user.getFriends();
            friends.remove(friendId);
            user.setFriends(friends);
    }

}
