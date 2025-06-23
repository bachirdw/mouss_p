package com.gestion.location.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
  @NotBlank(message = "L'email est obligatoire")
  @Email(message = "Format de l'email invalide")
  private String email;

  @NotBlank(message = "Le mot de passe est obligatoire")
  @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
  private String password;

}