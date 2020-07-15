package com.log.blog.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

public class AuthenticationUtils {

    public static void checkOwnerAuthentication(@NotNull String currentId, @Nullable String ownerId) {
        if (!currentId.equals(ownerId))
            throw HttpClientErrorException.create(HttpStatus.FORBIDDEN, "",
                    new HttpHeaders(), null, StandardCharsets.UTF_8);
    }
}
