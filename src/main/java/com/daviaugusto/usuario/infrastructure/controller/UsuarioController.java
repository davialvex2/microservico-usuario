package com.daviaugusto.usuario.infrastructure.controller;


import com.daviaugusto.usuario.infrastructure.dtos.EnderecoDTO;
import com.daviaugusto.usuario.infrastructure.dtos.TelefoneDTO;
import com.daviaugusto.usuario.infrastructure.dtos.UsuarioDTO;
import com.daviaugusto.usuario.infrastructure.dtos.ViaCepDTO;
import com.daviaugusto.usuario.infrastructure.security.JwtUtil;
import com.daviaugusto.usuario.infrastructure.security.SecurityConfig;
import com.daviaugusto.usuario.infrastructure.services.UsuarioService;
import com.daviaugusto.usuario.infrastructure.services.ViaCepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SHEME)
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ViaCepService viaCepService;


    @PostMapping
    @Operation(summary = "Salvar usuário", description = "Cria um nome usuário")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Logar Usuário", description = "Faz o login do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais invalidas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public String login (@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    @Operation(summary = "Busca dados do usuário por email", description = "Faz a busca dos dados do usuário pelo email")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "403", description = "usuario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping
    @Operation(summary = "Deleta usuário", description = "Deleta usuário pelo email")
    @ApiResponse(responseCode = "200", description = "Usuario excluido com sucesso")
    @ApiResponse(responseCode = "403", description = "usuario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam("email") String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualiza dados do usuário", description = "Atualizar dados de usuário")
    @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "usuario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/telefone/{id}")
    @Operation(summary = "Atualiza telefone", description = "Faz atualização de telefone por id")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "telefone não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@PathVariable Long id,
                                                         @RequestBody TelefoneDTO telefoneDTO){
        return ResponseEntity.ok(usuarioService.atualizarDadosTelefone(id, telefoneDTO));
    }

    @PutMapping("/endereco/{id}")
    @Operation(summary = "Atualiza endereço", description = "Faz atualização do endereço por id")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Endereço não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id,
                                                         @RequestBody EnderecoDTO enderecoDTO){
        return ResponseEntity.ok(usuarioService.atualizarEndereco(id, enderecoDTO));

    }

    @PostMapping("/endereco")
    @Operation(summary = "Cria um novo endereço", description = "Cadastrada um novo endereço")
    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Endereço já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<EnderecoDTO> inserirEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                       @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.inserirEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Cria um novo telefone", description = "Cadastra um novo telefone")
    @ApiResponse(responseCode = "200", description = "Telefone cadastrado com sucesso")
    @ApiResponse(responseCode = "409", description = "telefone já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<TelefoneDTO> inserirTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                       @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.inserirTelefone(token, telefoneDTO));
    }

    @GetMapping("/endereco/{cep}")
    @Operation(summary = "Busca endereço pelo CEP", description = "Busca de CEP")
    @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Os caracteres do CEP invalidos")
    public ResponseEntity<ViaCepDTO> buscarCep(@PathVariable("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscarCep(cep));
    }


}
