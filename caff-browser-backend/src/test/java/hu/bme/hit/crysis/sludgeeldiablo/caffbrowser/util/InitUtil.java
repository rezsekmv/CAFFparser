package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;

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
}
