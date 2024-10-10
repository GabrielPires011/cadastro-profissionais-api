package com.br.cadastroprofissionaisapi.controller;

import com.br.cadastroprofissionaisapi.dto.*;
import com.br.cadastroprofissionaisapi.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoService service;

    public ContatoController(ContatoService service) {
        this.service = service;
    }

    @Operation(summary = "Busca o contato por ID",
            description = "Retorna o contato detalhado com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DadosDetalhadosContatoDto.class))),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhadosContatoDto> buscar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @Operation(summary = "Cria um novo contato",
            description = "Cria um novo contato com base no DTO fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<String> criar(@Valid @RequestBody CriarContatoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucesso contato com id " + service.criar(dto) + " cadastrado");
    }

    @Operation(summary = "Altera o contato",
            description = "Altera o contato com base no DTO fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> alterar(@PathVariable Long id, @Valid @RequestBody AlterarContatoDto dto) {
        service.alterar(id, dto);
        return ResponseEntity.ok().body("Sucesso cadastrado alterado");
    }

    @Operation(summary = "Exclui o contato",
            description = "Exclui o contato com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.ok("Contato excluído com sucesso");
    }

    @Operation(summary = "Busca contatos",
            description = "Busca contatos com base em critérios de consulta e campos específicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contatos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de consulta inválidos")
    })
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> buscarProfissionais(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<Map<String, Object>> contatos = service.buscarContatos(q, fields);
        return ResponseEntity.ok(contatos);
    }
}