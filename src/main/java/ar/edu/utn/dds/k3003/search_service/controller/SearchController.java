package ar.edu.utn.dds.k3003.search_service.controller;

import ar.edu.utn.dds.k3003.search_service.dto.EstadoUpdateEvent;
import ar.edu.utn.dds.k3003.search_service.dto.HechoDTO;
import ar.edu.utn.dds.k3003.search_service.dto.HechoIndexadoEvent;
import ar.edu.utn.dds.k3003.search_service.dto.PdiIndexadoEvent;
import ar.edu.utn.dds.k3003.search_service.model.HechoIndexado;
import ar.edu.utn.dds.k3003.search_service.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<Page<HechoDTO>> buscar(
            @RequestParam("palabra") String palabra,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HechoDTO> resultados = searchService.buscarHechos(palabra, tags, pageable);

        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getTags() {
        List<String> tags = searchService.getTagsUnicos();
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/index")
    public ResponseEntity<Void> indexarDocumento(@RequestBody HechoIndexadoEvent event) {
        try {
            searchService.indexar(event);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{hechoId}/pdi")
    public ResponseEntity<Void> indexarPdi(
            @PathVariable String hechoId,
            @RequestBody PdiIndexadoEvent event
    ) {
        try {
            searchService.actualizarConPdi(hechoId, event);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{hechoId}/estado")
    public ResponseEntity<HechoIndexado> actualizarEstado(
            @PathVariable String hechoId,
            @RequestBody EstadoUpdateEvent event
            ) {
        HechoIndexado doc = searchService.actualizarEstado(hechoId, event.getEstado());
        return ResponseEntity.ok(doc);
    }

}
