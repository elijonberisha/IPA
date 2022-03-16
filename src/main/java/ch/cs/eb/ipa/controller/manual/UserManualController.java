package ch.cs.eb.ipa.controller.manual;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Paths;

@Controller
public class UserManualController {

    @GetMapping("/download_manual")
    @ResponseBody
    public FileSystemResource downloadManual() {
        return new FileSystemResource(Paths.get(".").toAbsolutePath().normalize().toString().replace("\\", "/").concat("/src/main/resources/USER_MANUAL_ELR.pdf"));
    }
}
