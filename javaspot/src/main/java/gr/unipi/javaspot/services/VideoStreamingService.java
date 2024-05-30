package gr.unipi.javaspot.services;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

public interface VideoStreamingService {
    Mono<Resource> getVideo(String title);
}
