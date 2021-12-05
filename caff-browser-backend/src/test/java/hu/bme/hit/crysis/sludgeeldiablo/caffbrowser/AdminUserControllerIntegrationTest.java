package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.InitUtil;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.TestConfig;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.UserSessionTest;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminUserControllerIntegrationTest extends UserSessionTest {

    private static final String BASE_URL = "/api/admin/user";
    private static final String BASE_PARAMETER_URL = BASE_URL + "/{userId}";

    private final MockMvc mockMvc;
    private final JacksonTester<UserDto> userJson;

    @Test
    @DisplayName("Összes felhasználó megtekintése")
    void testGetAllUser() throws Exception {
        // when, then
        this.mockMvc.perform(get(BASE_URL)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numberOfElements", is(3)))
                .andExpect(jsonPath("$.content[0].username", is("sludge")))
                .andExpect(jsonPath("$.content[1].username", is("carnage")))
                .andExpect(jsonPath("$.content[2].username", is("borgore")));
    }

    @Test
    @DisplayName("Felhasználói adatok sikeres lekérése azonosító alapján")
    void testGetUser() throws Exception {
        // when, then
        this.mockMvc.perform(get(BASE_PARAMETER_URL, 3L)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.username", is("borgore")))
                .andExpect(jsonPath("$.name", is("Borgore")))
                .andExpect(jsonPath("$.email", is("borgore@magictrick.com")));
    }

    @Test
    @DisplayName("Nem létező felhasználó sikertelen lekérése")
    void testGetUserNotFoundUser() throws Exception {
        // when, then
        this.mockMvc.perform(get(BASE_PARAMETER_URL, 999L)
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Felhasználói adatok sikeres módosítása azonosító alapján")
    void testUpdateUser() throws Exception {
        // given
        UserDto dto = InitUtil.createValidUpdateUserDto();

        // when, then
        this.mockMvc.perform(put(BASE_PARAMETER_URL, 3L)
                .contentType((MediaType.APPLICATION_JSON))
                .content(userJson.write(dto).getJson())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.username", is("borgore")))
                .andExpect(jsonPath("$.name", is("Updated Sludge")))
                .andExpect(jsonPath("$.email", is("updated@carnage.sludge")));
    }

    @Test
    @DisplayName("Felhasználói fiók törlése azonosító alapján")
    void testDeleteUser() throws Exception {
        // when, then
        this.mockMvc.perform(delete(BASE_PARAMETER_URL, 2L)
                .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
