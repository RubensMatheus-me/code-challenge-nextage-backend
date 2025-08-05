package com.nextage.codeChallenge.controllers;

import com.nextage.codeChallenge.dto.AuthResponseDTO;
import com.nextage.codeChallenge.dto.LoginRequestDTO;
import com.nextage.codeChallenge.dto.RegisterRequestDTO;
import com.nextage.codeChallenge.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Registrar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao registrar usuário")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        AuthResponseDTO response = authService.register(request);

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Autenticar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao autenticar usuário")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody @Valid LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
