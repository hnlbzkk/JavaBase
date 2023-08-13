package com.xyz.security.model.param;

import jakarta.validation.constraints.NotBlank;

/**
 * @author ZKKzs
 * @since 2023/8/13
 **/
public record LoginParam(@NotBlank(message = "email 不能为空") String email,
                         @NotBlank(message = "password 不能为空") String password) {
}
