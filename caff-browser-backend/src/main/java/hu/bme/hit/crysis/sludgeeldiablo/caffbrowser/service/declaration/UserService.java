package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.PasswordDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    /**
     * Új felhasználó mentése
     *
     * @param userDto az új felhasználó adatai
     * @return a mentett felhasználó
     */
    UserDto save(UserDto userDto);

    /**
     * Felhasználó lekérése id alapján
     *
     * @param id felhasználó azonosítója
     * @return a talált felhasználó adatai
     */
    UserDto get(Long id);

    /**
     * Felhasználó keresése id alapján
     *
     * @param id felhasználó azonosítója
     * @return a talált felhasználó entitás
     */
    User findById(Long id);

    /**
     * Egy oldalnyi felhasználó lekérése
     *
     * @return egy oldalnyi felhasználó listája
     */
    Page<UserDto> getAll(Pageable pageable);

    /**
     * Felhasználó entitás keresése név alapján
     *
     * @param username felhasználónév
     * @return a talált felhasználó entitás
     */
    User findByUsername(String username);

    /**
     * Aktuálisan bejelentkezett felhasználó adatainak lekérése
     *
     * @return talált felhasználó adatai
     */
    UserDto getMe();

    /**
     * Aktuálisan bejelentkezett felhasználó entitás
     *
     * @return felhasználó entitás
     */
    User getCurrentUser();

    /**
     * Aktuálisan bejelentkezett felhasználó adatainak módosítása
     *
     * @param userDto új adatok
     * @return módosított felhasználó adatai
     */
    UserDto updateMe(UserDto userDto);

    /**
     * Felhasználó adatainak módosítása id alapján
     *
     * @param id felhasználó azonosítója
     * @param userDto módosított adatok
     * @return a módosított felhasználó
     */
    UserDto update(Long id, UserDto userDto);

    /**
     * Felhasználó törlése id alapján
     *
     * @param id felhasználó azonosítója
     */
    void delete(Long id);

    /**
     * Aktuálisan bejelentkezett felhasználó törlése
     *
     */
    void deleteMe();

    /**
     * Aktuálisan bejelentkezett felhasználó jelszavának módosítása
     *
     * @param passwordDto régi és új jelszó
     */
    void password(PasswordDto passwordDto);

    /**
     * Visszaadja, hogy a bejelentkezett felhasználó bír-e adminisztrátori jogosultsággal
     *
     * @return adminisztrátori jogosultságra utaló logikai érték
     */
    Boolean isCurrentUserAdmin();
}
