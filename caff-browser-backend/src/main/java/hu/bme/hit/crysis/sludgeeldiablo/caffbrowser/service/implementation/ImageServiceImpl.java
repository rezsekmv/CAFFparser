package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.implementation;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CaffJson;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNativeParserException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNotFoundException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper.ImageMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository.ImageRepository;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.GifSequenceWriter;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.NativeParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.PpmReader.ppm;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final UserService userService;

    @Value("${repopath}")
    private String path;

    private Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    @Override
    public void delete(Long id) {
        log.trace("ImageService : delete, id=[{}]", id);
        validateCanDelete(id);
        imageRepository.deleteById(id);
    }

    private void validateCanDelete(Long id) {
        if (!canCurrentUserModifyImage(id)) {
            throw new CbException("error.image.delete");
        }
    }

    @Override
    public Boolean canCurrentUserModifyImage(Long id) {
        return isOwnedByCurrentUser(id) || userService.isCurrentUserAdmin();
    }

    private Boolean isOwnedByCurrentUser(Long id) {
        return findById(id).getUserId().equals(getCurrentUserId());
    }

    @Override
    public Image findById(Long id) {
        log.trace("ImageService : findById, id=[{}]", id);
        return imageRepository.findById(id)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public ImageDto save(MultipartFile file) {
        log.trace("ImageService : save, file=[{}]", file);
        CaffJson caff = parseJson(file.getName());
        createGif(caff, file.getName());
        String uuid = parseFile(file);
        Image createdImage = imageRepository.save(createImage(uuid));
        return imageMapper.toDto(createdImage);
    }

    private String parseFile(MultipartFile file) {
        try {
            return NativeParserUtil.parse(file);
        } catch (Throwable t) {
            throw new CbNativeParserException(t.getMessage());
        }
    }

    private CaffJson parseJson(String filename) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path + "caff-browser-native-parser/output-json/" + filename + "-json.json")));

            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<CaffJson> jsonAdapter = moshi.adapter(CaffJson.class);

            CaffJson caff = jsonAdapter.fromJson(content);
            System.out.println(caff);
            return caff;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createGif(CaffJson caff, String filename) {
        try {
            File dir = new File(path + "\\caff-browser-native-parser\\output-images");
            List<File> images = Arrays.stream(dir.listFiles()).filter(file -> file.getName().startsWith(filename)).collect(Collectors.toList());

            ImageOutputStream output = new FileImageOutputStream(new File(path + "\\caff-browser-backend\\src\\main\\resources\\static\\" + filename + ".gif"));
            GifSequenceWriter writer = new GifSequenceWriter(output, 1, 1, true);

            for (File image : images) {
                byte[] fileContent = Files.readAllBytes(image.toPath());
                //TODO
                BufferedImage bufferedImage = ppm(1000, 667, 255, Arrays.copyOfRange(fileContent, 4, fileContent.length - 1));
                writer.writeToSequence(bufferedImage);
            }

            writer.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Image createImage(String uuid) {
        Image image = new Image();
        image.setUuid(uuid);
        image.setUserId(userService.getCurrentUser().getId());
        return image;
    }

    @Override
    public ImageDto get(Long id) {
        log.trace("ImageService : get, id=[{}]", id);
        return imageMapper.toDtoWithDetails(findById(id));
    }

    @Override
    public Page<ImageDto> getAll(Pageable pageable) {
        log.trace("ImageService : getAll, pageable=[{}]", pageable);
        return imageRepository.findAll(pageable).map(imageMapper::toDto);
    }

    @Override
    public Boolean canCurrentUserCommentImage(Long imageId) {
        return userService.isCurrentUserAdmin() || !isCurrentUserCommentedAlready(imageId);
    }

    private Boolean isCurrentUserCommentedAlready(Long imageId) {
        return findById(imageId).getComments().stream()
                .anyMatch(c -> c.getUserId().equals(getCurrentUserId()));
    }
}
