package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.manager.ImageManager;
import it.unicam.cs.ids.manager.POIManager;
import it.unicam.cs.ids.model.content.Image;
import it.unicam.cs.ids.util.BadRequestException;
import it.unicam.cs.ids.util.NotFoundException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/v1/image")
public class ImageController {
    private static final String UPLOAD_DIRECTORY = "uploads";
    private final ImageManager manager;
    private final POIManager poiManager;

    public ImageController(ImageManager manager, POIManager poiManager) {
        this.manager = manager;
        this.poiManager = poiManager;
    }


    @GetMapping("/{id}")
    public Image getById(@PathVariable String id) {
        Image image = manager.get(id);
        if (image == null) {
            throw new NotFoundException("Image not found");
        }
        return image;
    }

    @PostMapping("/submit")
    public String submit(@RequestPart @NotNull MultipartFile image, @RequestPart @NotEmpty String description, @RequestPart @NotEmpty String poiId) throws IOException {
        if (image.getSize() <= 0 || image.getSize() > 1024 * 1024 * 10) { // 10 MB
            throw new BadRequestException("Invalid image size");
        }
        String originalName = image.getOriginalFilename();
        if (originalName == null || originalName.isEmpty()) {
            throw new BadRequestException("Invalid image name");
        }
        if (poiManager.get(poiId) == null) {
            throw new BadRequestException("POI not found");
        }

        String extension = originalName.substring(image.getOriginalFilename().lastIndexOf('.'));
        String name = poiId + "/" + UUID.randomUUID() + extension;
        Path path = Paths.get(UPLOAD_DIRECTORY, name);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        manager.submit(new Image(name, description, null, false, poiId));
        return UPLOAD_DIRECTORY + "/" + name;
    }

    @GetMapping("/approve/{id}")
    public void approve(@PathVariable String id) {
        manager.approve(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        manager.remove(id);
    }
}
