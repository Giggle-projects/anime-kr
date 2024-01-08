package anime.shutdown;

import jakarta.annotation.PostConstruct;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class ShutDownHandler {

    private final ApplicationContext context;
    private final RequestMappingHandlerMapping requestHandlerMapping;
    private final ShutDownController shutDownController;

    public ShutDownHandler(
        ApplicationContext context,
        @Qualifier(value = "requestMappingHandlerMapping")
        RequestMappingHandlerMapping requestHandlerMapping,
        ShutDownController shutDownController
    ) {
        this.context = context;
        this.requestHandlerMapping = requestHandlerMapping;
        this.shutDownController = shutDownController;
    }

    @PostConstruct
    public void handle() {
        var shutDownControllers = AutoConfigurationPackages.get(context).stream()
            .flatMap(it -> new Reflections(it).getTypesAnnotatedWith(ShutDown.class).stream())
            .collect(Collectors.toList());

        shutDownControllers.forEach(it -> Arrays.stream(it.getMethods())
            .filter(definedHandlerMethod -> definedHandlerMethod.isAnnotationPresent(GetMapping.class))
            .forEach(definedHandlerMethod -> {
                var apiPaths = definedHandlerMethod.getAnnotation(GetMapping.class).value();
                addHandler(apiPaths);
            }));
    }

    private void addHandler(String[] paths) {
        var options = new RequestMappingInfo.BuilderConfiguration();
        options.setPatternParser(new PathPatternParser());
        try {
            requestHandlerMapping.registerMapping(
                RequestMappingInfo
                    .paths(paths)
                    .methods(RequestMethod.GET)
                    .options(options)
                    .build(),
                shutDownController,
                shutDownController.getDefaultHandlerMethod()
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

@RestController
class ShutDownController {

    public ResponseEntity<String> noticeShutDown() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("현재 사용할 수 없는 Api 입니다.");
    }

    public Method getDefaultHandlerMethod() throws NoSuchMethodException {
        return getClass().getMethod("noticeShutDown");
    }
}
