package ar.edu.utn.dds.k3003.search_service.service;

import ar.edu.utn.dds.k3003.search_service.dto.HechoDTO;
import ar.edu.utn.dds.k3003.search_service.dto.HechoIndexadoEvent;
import ar.edu.utn.dds.k3003.search_service.dto.PdiIndexadoEvent;
import ar.edu.utn.dds.k3003.search_service.model.HechoIndexado;
import ar.edu.utn.dds.k3003.search_service.repository.HechoIndexadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SearchService {
    private final MongoTemplate mongoTemplate;
    private final HechoIndexadoRepository hechoRepository;

    public SearchService(MongoTemplate mongoTemplate, HechoIndexadoRepository hechoRepository) {
        this.mongoTemplate = mongoTemplate;
        this.hechoRepository = hechoRepository;
    }

    public Page<HechoDTO> buscarHechos(String palabra, List<String> tags, Pageable pageable) {
        TextCriteria textCriteria = TextCriteria.forLanguage("spanish").matching(palabra);

        Query query = TextQuery.queryText(textCriteria)
                .sortByScore()
                .with(pageable);

        query.addCriteria(Criteria.where("estado").ne("borrado"));

        if (tags != null && !tags.isEmpty()) {
            query.addCriteria(Criteria.where("tags").all(tags.stream().map(String::toLowerCase).collect(Collectors.toList())));
        }

        //long total = mongoTemplate.count(query, HechoIndexado.class);
        List<HechoIndexado> resultados = mongoTemplate.find(query, HechoIndexado.class);

        List<HechoDTO> dtos = resultados.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(dtos, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), HechoIndexado.class));
        //return new PageImpl<>(dtos, pageable, total);
    }

    public List<String> getTagsUnicos() {
        return mongoTemplate.query(HechoIndexado.class)
                .distinct("tags")
                .as(String.class)
                .all()
                .stream()
                .filter(tag -> tag != null && !tag.isBlank())
                .sorted()
                .collect(Collectors.toList());
    }

    public void indexar(HechoIndexadoEvent event) {
        HechoIndexado doc = hechoRepository.findByHechoId(event.getHechoId())
                .orElse(new HechoIndexado());

        doc.setHechoId(event.getHechoId());
        doc.setNombre(event.getNombre());
        doc.setDescripcion(event.getDescripcion());
        doc.setContenidoPdis(event.getContenidoPdis());
        doc.setTags(event.getTags());
        doc.setEstado(event.getEstado());
        doc.setColeccion(event.getColeccion());
        doc.setUrlImagenPrincipal(event.getUrlImagenPrincipal());

        hechoRepository.save(doc);
    }

    public HechoIndexado actualizarConPdi(String hechoId, PdiIndexadoEvent event) {
        HechoIndexado doc = hechoRepository.findByHechoId(hechoId)
                .orElseThrow(() -> new RuntimeException("Hecho no encontrado en el índice " + hechoId));

        String contenidoPdiNuevo = (event.getContenido() == null) ? "" : event.getContenido();
        String contenidoPdiActual = (doc.getContenidoPdis() == null) ? "" : doc.getContenidoPdis();

        doc.setContenidoPdis(contenidoPdiActual + " " + contenidoPdiNuevo);

        if (doc.getTags() == null) {
            doc.setTags(new ArrayList<>());
        }
        if (event.getTags() != null) {
            for (String tag : event.getTags()) {
                if (!doc.getTags().contains(tag.toLowerCase())) {
                    doc.getTags().add(tag.toLowerCase());
                }
            }
        }

        return hechoRepository.save(doc);
    }

    public HechoIndexado actualizarEstado(String hechoId, String nuevoEstado) {
        HechoIndexado doc = hechoRepository.findByHechoId(hechoId)
                .orElseThrow(() -> new RuntimeException("Hecho no encontrado."));

        doc.setEstado(nuevoEstado);
        return hechoRepository.save(doc);
    }

    private HechoDTO toDTO(HechoIndexado doc) {
        return new HechoDTO(
                doc.getHechoId(),
                doc.getNombre(),
                doc.getColeccion(),
                doc.getUrlImagenPrincipal()
        );
    }
}
