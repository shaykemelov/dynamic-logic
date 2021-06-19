package edu.shaykemelov.dynamiclogic.resources.scripts;

import edu.shaykemelov.dynamiclogic.services.scripts.ScriptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ScriptsRestController {

    private final ScriptsService scriptsService;

    @Autowired
    public ScriptsRestController(final ScriptsService scriptsService) {

        this.scriptsService = scriptsService;
    }

    @GetMapping(value = "/scripts/run/{code}")
    public ResponseEntity<Object> runScript(@PathVariable(name = "code") final String code,
                                            @RequestParam(name = "args") final String rowArgs) {

        final var args = rowArgs == null || rowArgs.isBlank() ? new Object[] {} : rowArgs.split(";");
        final var result = scriptsService.runScript(code, args);

        return ResponseEntity.ok(result);
    }
}
