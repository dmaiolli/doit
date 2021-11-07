package br.com.denys.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class Credential {

    @Email
    private String username;

    @NotBlank
    private String password;

}
