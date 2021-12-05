package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.InitUtil;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.TestConfig;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
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
class AuthControllerIT {

    private static final String SIGN_UP_URL = "/api/public/sign-up";
    private static final String API_DOCS_URL = "/api/public/api-docs";
    private static final String LOGIN_URL = "/api/login";

    private final MockMvc mockMvc;
    private final JacksonTester<UserDto> userJson;

    @Test
    @DisplayName("Új felhasználó sikeres regisztrációja")
    void testSignUp() throws Exception {
        // given
        UserDto dto = InitUtil.createValidUserDto();

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
    @DisplayName("Helytelen felhasználónév megadásakor sikertelen regisztráció")
    void testSignUpInvalidUsername() throws Exception {
        // given
        UserDto dto = InitUtil.createInvalidUserDto();

        // when, then
        this.mockMvc.perform(post(SIGN_UP_URL)
                .contentType((MediaType.APPLICATION_JSON))
                .content(userJson.write(dto).getJson())
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("OpenAPI Docs sikeres lekérése")
    void testApiDocs() throws Exception {
        // when, then
        this.mockMvc.perform(get(API_DOCS_URL)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Sikeres bejelentkezés")
    void testLogin() throws Exception {
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
    @DisplayName("Nem létező felhasználóval sikertelen a bejelentkezés")
    void testLoginUsernameNotExist() throws Exception {
        // when, then
        this.mockMvc.perform(post(LOGIN_URL)
                .contentType((MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .content("username=notarealuser&password=1234")
                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
