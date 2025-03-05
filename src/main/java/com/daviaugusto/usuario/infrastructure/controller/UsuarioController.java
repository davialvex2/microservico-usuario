package com.daviaugusto.usuario.infrastructure.controller;


import com.daviaugusto.usuario.infrastructure.dtos.EnderecoDTO;
import com.daviaugusto.usuario.infrastructure.dtos.TelefoneDTO;
import com.daviaugusto.usuario.infrastructure.dtos.UsuarioDTO;
import com.daviaugusto.usuario.infrastructure.security.JwtUtil;
import com.daviaugusto.usuario.infrastructure.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login (@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam("email") String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/telefone/{id}")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@PathVariable Long id,
                                                         @RequestBody TelefoneDTO telefoneDTO){
        return ResponseEntity.ok(usuarioService.atualizarDadosTelefone(id, telefoneDTO));
    }

    @PutMapping("/endereco/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id,
                                                         @RequestBody EnderecoDTO enderecoDTO){
        return ResponseEntity.ok(usuarioService.atualizarEndereco(id, enderecoDTO));

    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> inserirEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                       @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.inserirEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> inserirEndereco(@RequestBody TelefoneDTO telefoneDTO,
                                                       @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.inserirTelefone(token, telefoneDTO));
    }


}
