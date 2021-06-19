package edu.shaykemelov.dynamiclogic.resources.modules;

import edu.shaykemelov.dynamiclogic.services.modules.ModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ModulesRestController {

    private final ModulesService modulesService;

    @Autowired
    public ModulesRestController(final ModulesService modulesService) {

        this.modulesService = modulesService;
    }

    @GetMapping(value = "/modules/reload")
    public ResponseEntity<String> reload() {

        modulesService.reload();

        return ResponseEntity.ok().build();
    }
}
