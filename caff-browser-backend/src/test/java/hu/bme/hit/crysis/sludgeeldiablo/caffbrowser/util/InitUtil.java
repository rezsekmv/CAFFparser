package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.PasswordDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

public class InitUtil {

    public static UserDto createValidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("virtualriot");
        userDto.setPassword("1234");
        userDto.setName("Virtual Riot");
        userDto.setEmail("virtual@riot.com");
        return userDto;
    }

    public static UserDto createInvalidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("éé--");
        userDto.setPassword("1234");
        return userDto;
    }

    public static CommentDto createValidCommentDto() {
        CommentDto commentDto = new CommentDto();
        commentDto.setImageId(1L);
        commentDto.setContent("Hozzászólok ehhez a képhez");
        return commentDto;
    }

    public static CommentDto createInvalidCommentDto() {
        CommentDto commentDto = new CommentDto();
        commentDto.setImageId(999L);
        commentDto.setContent("Ez a hozzászólás sosem lesz látható");
        return commentDto;
    }

    public static UserDto createValidUpdateUserDto() {
        UserDto userDto = new UserDto();
        userDto.setName("Updated Sludge");
        userDto.setEmail("updated@carnage.sludge");
        return userDto;
    }

    public static UserDto createValidUpdateUserDtoWithUsernameUpdate() {
        UserDto userDto = new UserDto();
        userDto.setUsername("updatedsludge");
        userDto.setName("Sludge El Diablo");
        userDto.setEmail("carnage@feat.sludge");
        return userDto;
    }

    public static UserDto createClearUpdateUserDto() {
        return new UserDto();
    }

    public static PasswordDto createValidPasswordDto() {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("1234");
        passwordDto.setNewPassword("123");
        return passwordDto;
    }

    public static Image createValidImageEntity() {
        Image image = new Image();
        image.setId(1L);
        image.setUuid("045c07c0-1b72-4de7-a49f-e92f6cdb2979");
        image.setUserId(1L);
        image.setDate(LocalDateTime.parse("2018-08-10T13:25:00"));
        image.setCredit("Heavyweight Records");
        image.setCaption("El Diablo concert");
        image.setTags(new HashSet<>(Arrays.asList("concert", "drop", "eldiab", "crowd")));
        image.setHeight(520L);
        image.setWidth(293L);
        return image;
    }

    public static String getGiffPath() {
        return "/045c07c0-1b72-4de7-a49f-e92f6cdb2979.gif";
    }

    public static String getCaffPath() {
        return "/045c07c0-1b72-4de7-a49f-e92f6cdb2979.caff";
    }

    public static MockMultipartFile createValidMultipartfile() {
        return new MockMultipartFile("image", "1.caff", MediaType.MULTIPART_FORM_DATA_VALUE, "1".getBytes());
    }
}
