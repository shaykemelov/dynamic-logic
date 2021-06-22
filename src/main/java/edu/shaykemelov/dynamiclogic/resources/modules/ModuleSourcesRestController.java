package edu.shaykemelov.dynamiclogic.resources.modules;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import edu.shaykemelov.dynamiclogic.resources.dto.Source;
import edu.shaykemelov.dynamiclogic.services.storage.modules.ModuleSourcesStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ModuleSourcesRestController
{
    private final ModuleSourcesStorageService moduleSourcesStorageService;

    @Autowired
    public ModuleSourcesRestController(final ModuleSourcesStorageService moduleSourcesStorageService)
    {
        this.moduleSourcesStorageService = moduleSourcesStorageService;
    }

    @PostMapping(value = "/module_sources")
    public ResponseEntity<String> addModuleSource(@RequestBody final Source source)
    {
        moduleSourcesStorageService.add(source.code(), source.source());

        return ResponseEntity.created(URI.create("/module_sources")).build();
    }

    @DeleteMapping(value = "/module_sources/{moduleSourceCode}")
    public ResponseEntity<String> removeModulesSource(
            @PathVariable(name = "moduleSourceCode") final String moduleSourceCode)
    {
        moduleSourcesStorageService.remove(moduleSourceCode);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/module_sources")
    public ResponseEntity<String> clearModuleSources()
    {
        moduleSourcesStorageService.clear();

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/module_sources/codes")
    public ResponseEntity<Set<String>> listSourceModuleCodes()
    {
        final var codes = moduleSourcesStorageService.listCodes();

        return ResponseEntity.ok(codes);
    }

    @GetMapping(value = "/module_sources/codes/{code}")
    public ResponseEntity<String> getSource(@PathVariable(name = "code") final String code)
    {
        final var source = moduleSourcesStorageService.getSource(code);

        return ResponseEntity.ok(source);
    }

    @GetMapping(value = "/module_sources")
    public ResponseEntity<Set<Source>> listSourceModules()
    {
        final var sourceModules = moduleSourcesStorageService.listSources()
                                                             .entrySet()
                                                             .stream()
                                                             .map(e -> new Source(e.getKey(), e.getValue()))
                                                             .collect(Collectors.toSet());

        return ResponseEntity.ok(sourceModules);
    }
}
