package io.github.openabcd.cloud.common.vo;

import io.github.openabcd.cloud.common.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

@Slf4j
public class PassportResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(PassportHeader.class) != null
                && parameter.getParameterType().equals(Passport.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) {
        final var encodedPassport = webRequest.getHeader(Passport.PASSPORT_HEADER);
        if (encodedPassport == null) {
            throw new IllegalArgumentException("Missing Openabcd-Passport header");
        }
        return JsonUtil.fromJson(Base64.getDecoder().decode(encodedPassport), Passport.class);
    }
}
