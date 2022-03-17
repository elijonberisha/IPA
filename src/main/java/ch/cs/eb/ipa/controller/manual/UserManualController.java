package ch.cs.eb.ipa.controller.manual;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Paths;

/**
 * author: Elijon Berisha
 * date: 16.03.2022
 * class: UserManualController.java
 */

// CONTAINS THE MAPPING FOR THE MANUAL DOWNLOAD
@Controller
public class UserManualController {

    // FileSystemResource IS RETURNED; FileSystemResource WILL BE CONVERTED TO A FILE USING THE 'download' MODIFIER IN HTML
    @GetMapping("/download_manual")
    @ResponseBody
    public FileSystemResource downloadManual() {
        // CURRENT PROJECT PATH + /src/main/resources/USER_MANUAL_ELR.pdf
        return new FileSystemResource(Paths.get(".").toAbsolutePath().normalize().toString().replace("\\", "/").concat("/src/main/resources/USER_MANUAL_ELR.pdf"));
    }
}
