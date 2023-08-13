package com.xyz.security.model.param;

import jakarta.validation.constraints.NotBlank;

/**
 * @author ZKKzs
 * @since 2023/8/14
 **/
public record RegisterParam(@NotBlank(message = "name 不能为空") String name,
                            @NotBlank(message = "password 不能为空") String password,
                            @NotBlank(message = "email 不能为空") String email,
                            @NotBlank(message = "phone 不能为空") String phone) {
}
