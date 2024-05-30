package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.services.VideoStreamingService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class StreamingController {

    private final VideoStreamingService service;

    @GetMapping(value = "video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String title) {
        return service.getVideo(title);
    }

}
