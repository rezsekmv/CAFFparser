package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.config.TestConfig;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIT {

    private static final String SIGN_UP_URL = "/api/public/sign-up";
    private static final String API_DOCS_URL = "/api/public/api-docs";
    private static final String LOGIN_URL = "/api/login";

    private final MockMvc mockMvc;
    private final JacksonTester<UserDto> userJson;

    private UserDto createValidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("virtualriot");
        userDto.setPassword("1234");
        userDto.setName("Virtual Riot");
        userDto.setEmail("virtual@riot.com");
        return userDto;
    }

    private UserDto createInvalidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("éé--");
        userDto.setPassword("1234");
        return userDto;
    }

    @Test
    public void testSignUp() throws Exception {
        // given
        UserDto dto = createValidUserDto();

        // when, then
        this.mockMvc.perform(post(SIGN_UP_URL)
                .contentType((MediaType.APPLICATION_JSON))
                .content(userJson.write(dto).getJson())
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Virtual Riot")))
                .andExpect(jsonPath("$.username", is("virtualriot")))
                .andExpect(jsonPath("$.email", is("virtual@riot.com")))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles", hasSize(0)));
    }

    @Test
    public void testSignUpInvalidUsername() throws Exception {
        // given
        UserDto dto = createInvalidUserDto();

        // when, then
        this.mockMvc.perform(post(SIGN_UP_URL)
                .contentType((MediaType.APPLICATION_JSON))
                .content(userJson.write(dto).getJson())
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testApiDocs() throws Exception {
        // when, then
        this.mockMvc.perform(get(API_DOCS_URL)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testLogin() throws Exception {
        // when, then
        this.mockMvc.perform(post(LOGIN_URL)
                .contentType((MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .content("username=sludge&password=1234")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()));
    }

    @Test
    public void testLoginUsernameNotExist() throws Exception {
        // when, then
        this.mockMvc.perform(post(LOGIN_URL)
                .contentType((MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .content("username=notarealuser&password=1234")
                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
