package com.br.cadastroprofissionaisapi.controller;

import com.br.cadastroprofissionaisapi.dto.AlterarProfissionalDto;
import com.br.cadastroprofissionaisapi.dto.CriarProfissionalDto;
import com.br.cadastroprofissionaisapi.dto.DadosDetalhadosProfissionalDto;
import com.br.cadastroprofissionaisapi.service.ProfissionalService;
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
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalService service;

    public ProfissionalController(ProfissionalService service) {
        this.service = service;
    }

    @Operation(summary = "Busca o profissional por ID",
            description = "Retorna o profissional detalhado com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DadosDetalhadosProfissionalDto.class))),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhadosProfissionalDto> buscar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @Operation(summary = "Cria um novo profissional",
            description = "Cria um novo profissional com base no DTO fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Long> criar(@Valid @RequestBody CriarProfissionalDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @Operation(summary = "Altera o profissional",
            description = "Altera o profissional com base no DTO fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> alterar(@PathVariable Long id, @Valid @RequestBody AlterarProfissionalDto dto) {
        service.alterar(id, dto);
        return ResponseEntity.ok().body("Sucesso cadastrado alterado");
    }

    @Operation(summary = "Exclui o profissional",
            description = "Exclui o profissional com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.ok("Profissional excluído com sucesso");
    }

    @Operation(summary = "Busca profissionais",
            description = "Busca profissionais com base em critérios de consulta e campos específicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de profissionais retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de consulta inválidos")
    })
    @GetMapping("/profissionais")
    public ResponseEntity<List<Map<String, Object>>> buscarProfissionais(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<Map<String, Object>> profissionais = service.buscarProfissionais(q, fields);
        return ResponseEntity.ok(profissionais);
    }
}