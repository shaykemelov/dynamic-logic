package edu.shaykemelov.dynamiclogic.resources.scripts;

import edu.shaykemelov.dynamiclogic.resources.dto.Source;
import edu.shaykemelov.dynamiclogic.services.storage.scripts.ScriptSourcesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ScriptSourcesRestController {

    private final ScriptSourcesStorageService scriptSourcesStorageService;

    @Autowired
    public ScriptSourcesRestController(ScriptSourcesStorageService scriptSourcesStorageService) {

        this.scriptSourcesStorageService = scriptSourcesStorageService;
    }

    @PostMapping(value = "/script_sources")
    public ResponseEntity<String> addScriptSource(@RequestBody final Source source) {

        scriptSourcesStorageService.add(source.code(), source.source());

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/script_sources/{scriptSource}")
    public ResponseEntity<String> removeModulesSource(@PathVariable(name = "scriptSource") final String scriptSource) {

        scriptSourcesStorageService.remove(scriptSource);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/script_sources")
    public ResponseEntity<String> clearModuleSources() {

        scriptSourcesStorageService.clear();

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/script_sources/codes")
    public ResponseEntity<Set<String>> listSourceModuleCodes() {

        final var codes = scriptSourcesStorageService.listCodes();

        return ResponseEntity.ok(codes);
    }

    @GetMapping(value = "/script_sources/codes/{code}")
    public ResponseEntity<String> getSource(@PathVariable(name = "code") final String code) {

        final var source = scriptSourcesStorageService.getSource(code);

        return ResponseEntity.ok(source);
    }

    @GetMapping(value = "/script_sources")
    public ResponseEntity<Set<Source>> listSourceModules() {

        final var sourceModules = scriptSourcesStorageService.listSources()
                .entrySet()
                .stream()
                .map(e -> new Source(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(sourceModules);
    }
}
