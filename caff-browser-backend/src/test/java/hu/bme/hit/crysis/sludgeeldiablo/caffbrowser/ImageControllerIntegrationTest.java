package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.InitUtil;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.NativeParserUtil;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.TestConfig;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.UserSessionTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageControllerIntegrationTest extends UserSessionTest {

    private static final String BASE_URL = "/api/image";
    private static final String BASE_PARAMETER_URL = BASE_URL + "/{imageId}";

    private final MockMvc mockMvc;

    @Test
    @DisplayName("CAFF fájl feltöltése a rendszerbe")
    void testCreateImage() throws Exception {
        // given
        MockMultipartFile multipartFile = InitUtil.createValidMultipartfile();

        MockedStatic<NativeParserUtil> mockNativeParserUtil = Mockito.mockStatic(NativeParserUtil.class);
        mockNativeParserUtil.when(() -> NativeParserUtil.parse(any())).thenReturn(InitUtil.createValidImageEntity());
        mockNativeParserUtil.when(() -> NativeParserUtil.getGifPath(any())).thenReturn(InitUtil.getGiffPath());
        mockNativeParserUtil.when(() -> NativeParserUtil.getCaffPath(any())).thenReturn(InitUtil.getCaffPath());

        // when, then
        this.mockMvc.perform(multipart(BASE_URL)
                .file(multipartFile)
                .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Galéria egy oldalának megtekintése")
    void testGetAllImage() throws Exception {
        // when, then
        this.mockMvc.perform(get(BASE_URL)
                .param("size", "8")
                .param("page", "0")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numberOfElements", is(8)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[2].id", is(3)));
    }

    @Test
    @DisplayName("Galéria szűrése megadott paraméterekkel")
    void testGetAllImageFiltered() throws Exception {
        // given
        final String creditFilter = "udg";
        final String captionFilter = "rna";

        // when, then
        this.mockMvc.perform(get(BASE_URL)
                .param("size", "8")
                .param("page", "0")
                .param("credit", creditFilter)
                .param("caption", captionFilter)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content[0].credit", containsStringIgnoringCase(creditFilter)))
                .andExpect(jsonPath("$.content[0].caption", containsStringIgnoringCase(captionFilter)))
                .andExpect(jsonPath("$.content[1].credit", containsStringIgnoringCase(creditFilter)))
                .andExpect(jsonPath("$.content[1].caption", containsStringIgnoringCase(captionFilter)));
    }

    @Test
    @DisplayName("Kép részleteinek lekérése azonosító alapján")
    void testGetImage() throws Exception {
        // when, then
        this.mockMvc.perform(get(BASE_PARAMETER_URL, 1L)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.credit", is("Heavyweight Records")))
                .andExpect(jsonPath("$.caption", is("El Diablo concert")));
    }

    @Test
    @DisplayName("Nem létező kép sikertelen lekérése")
    void testGetImageNotFoundImage() throws Exception {
        // when, then
        this.mockMvc.perform(get(BASE_PARAMETER_URL, 999L)
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Kép törlése azonosító alapján")
    void testDeleteImage() throws Exception {
        // when, then
        this.mockMvc.perform(delete(BASE_PARAMETER_URL, 1L)
                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Nem létező azonosítójú kép sikertelen törlése")
    void testDeleteImageNotFoundImage() throws Exception {
        // when, then
        this.mockMvc.perform(delete(BASE_PARAMETER_URL, 999L)
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
