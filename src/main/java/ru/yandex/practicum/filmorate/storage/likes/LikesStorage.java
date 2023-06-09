package ru.yandex.practicum.filmorate.storage.likes;

public interface LikesStorage {
    void addLike(long id, long userId);

    void deleteLike(long id, long userId);
}
