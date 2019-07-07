package uk.co.kenfos.http.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import uk.co.kenfos.domain.Area;
import uk.co.kenfos.domain.Coordinate;

@Configuration
@EnableWebFlux
public class JsonConfig implements WebFluxConfigurer {

    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        var objectMapper = objectMapper();
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
    }

    private ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(getJsonModule());
    }

    private SimpleModule getJsonModule() {
        return new SimpleModule()
            .addSerializer(Coordinate.class, new CoordinateSerializer())
            .addDeserializer(Coordinate.class, new CoordinateDeserializer())
            .addDeserializer(Area.class, new AreaDeserializer());
    }
}

