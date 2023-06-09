package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
public class MpaService {
    @Qualifier("mpaDbStorage")
    private final MpaStorage storage;

    public MpaService(MpaStorage storage) {
        this.storage = storage;
    }

    public List<RatingMPA> getRating() {
        return storage.getRating();
    }

    public RatingMPA getRatingById(long id) {
        return storage.getRatingById(id);
    }
}
