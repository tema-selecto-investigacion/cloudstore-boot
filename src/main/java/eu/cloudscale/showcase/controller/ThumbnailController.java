package eu.cloudscale.showcase.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class ThumbnailController {

    @GetMapping(value = "/img/*/thumb*.gif", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] productThumbnail() throws IOException{
        InputStream in = getClass().getResourceAsStream("/thumb.gif");
        return IOUtils.toByteArray(in);
    }
}
