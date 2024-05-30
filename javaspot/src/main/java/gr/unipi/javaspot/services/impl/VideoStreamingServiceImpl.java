package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.services.VideoStreamingService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class VideoStreamingServiceImpl implements VideoStreamingService {

    private static final String FORMAT = "classpath:videos/%s.mp4";
    private final ResourceLoader resourceLoader;

    @Override
    public Mono<Resource> getVideo(String title) {
        return Mono.fromSupplier(() -> resourceLoader.getResource(String.format(FORMAT, title)));
    }
}
