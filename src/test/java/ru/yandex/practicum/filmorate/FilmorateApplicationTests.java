package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private User locaLUser;
	private User storageUser;
	private Film localFilm;
	private Film storageFilm;


	@BeforeEach
	void buildUsersAndFilmsTest() {
		locaLUser = User.builder()
				.id(1)
				.email("mail@yandex.ru")
				.login("2e4sd1d")
				.name("est ad4ipisicing")
				.birthday(LocalDate.parse("1976-09-20"))
				.build();
		storageUser = userStorage.createUser(locaLUser);

		localFilm = Film.builder()
				.id(1)
				.name("fi23t")
				.description("Description")
				.releaseDate(LocalDate.parse("1900-03-25"))
				.duration(200)
				.mpa(new RatingMPA(5, "NC-17"))
				.likes(new HashSet<>())
				.genres(new ArrayList<>())
				.build();
		storageFilm = filmStorage.createFilm(localFilm);
	}

	@Test
	void createUserTest() {
		assertEquals(locaLUser, storageUser);
	}

	@Test
	void createFilmTest() {
		assertEquals(localFilm, storageFilm);
	}

	@Test
	void updateUserTest() {
		User updatedUser = User.builder()
				.id(1)
				.email("mail2@yandex.ru")
				.login("3e4sd1d")
				.name("est1 ad4ipisicing")
				.birthday(LocalDate.parse("1977-09-20"))
				.build();
		storageUser = userStorage.updateUser(updatedUser);
		assertEquals(updatedUser, storageUser);
	}

	@Test
	void updateFilmTest() {
		Film updatedFilm = Film.builder()
				.id(1)
				.name("fi25t")
				.description("Desasdcription")
				.releaseDate(LocalDate.parse("1902-03-25"))
				.duration(200)
				.mpa(new RatingMPA(2, "testRate2"))
				.build();
		storageFilm = filmStorage.updateFilm(updatedFilm);
		assertEquals(updatedFilm, storageFilm);
	}

	@Test
	void findUserByIdTest() {
		assertEquals(locaLUser, userStorage.findUserById(locaLUser.getId()));
	}

	@Test
	void findFilmById() {
		assertEquals(localFilm, filmStorage.findFilmById(localFilm.getId()));
	}

	@Test
	void findAllUsers() {
		User secondLocaLUser = User.builder()
				.id(2)
				.email("maa2sdil@yandex.ru")
				.login("2efas4sd1d")
				.name("est ad4ipasfisicing")
				.birthday(LocalDate.parse("1966-09-20"))
				.build();
		User secondStorageUser = userStorage.createUser(secondLocaLUser);
		User thirdLocaLUser = User.builder()
				.id(3)
				.email("ma3asdil@yandex.ru")
				.login("2edfas4sd1d")
				.name("est ad4ipasfisicing")
				.birthday(LocalDate.parse("1996-09-20"))
				.build();
		User thirdStorageUser = userStorage.createUser(thirdLocaLUser);
		List<User> allUsers = List.of(locaLUser, secondLocaLUser, thirdLocaLUser);
		assertEquals(allUsers, userStorage.getUsers());
	}

	@Test
	void findAllFilms() {
		Film secondLocalFilm = Film.builder()
				.id(2)
				.name("fi23t2")
				.description("Description2")
				.releaseDate(LocalDate.parse("1900-01-22"))
				.duration(200)
				.mpa(new RatingMPA(3, "PG-13"))
				.likes(new HashSet<>())
				.genres(new ArrayList<>())
				.build();
		Film secondStorageFilm = filmStorage.createFilm(secondLocalFilm);
		Film thirdLocalFilm = Film.builder()
				.id(3)
				.name("fi23t3")
				.description("Description3")
				.releaseDate(LocalDate.parse("2000-01-22"))
				.duration(200)
				.mpa(new RatingMPA(4, "R"))
				.likes(new HashSet<>())
				.genres(new ArrayList<>())
				.build();
		Film thirdStorageFilm = filmStorage.createFilm(thirdLocalFilm);
		List<Film> allFilms = List.of(localFilm, secondLocalFilm, thirdLocalFilm);;
		System.out.println(filmStorage.getFilms());
		assertEquals(allFilms, filmStorage.getFilms());
	}
}
