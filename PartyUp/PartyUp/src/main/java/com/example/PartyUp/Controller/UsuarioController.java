package com.example.PartyUp.Controller;
import org.springframework.web.bind.annotation.RestController;
import com.example.PartyUp.Model.Dto.UsuarioDTO ;
import com.example.PartyUp.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Actualizar perfil de usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable String id,
            @RequestBody Usuario usuario) {

        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(actualizado);
        }
    }