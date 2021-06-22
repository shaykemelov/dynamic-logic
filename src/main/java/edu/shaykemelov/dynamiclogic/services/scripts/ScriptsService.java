package edu.shaykemelov.dynamiclogic.services.scripts;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.shaykemelov.dynamiclogic.services.modules.ModulesService;
import edu.shaykemelov.dynamiclogic.services.storage.scripts.ScriptSourcesStorageService;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ScriptsService
{
    private static final Logger LOG = LoggerFactory.getLogger(ScriptsService.class);

    private final ModulesService modulesService;

    private final ScriptSourcesStorageService scriptSourcesStorageService;

    @Autowired
    public ScriptsService(final ModulesService modulesService,
                          final ScriptSourcesStorageService scriptSourcesStorageService)
    {
        this.modulesService = modulesService;
        this.scriptSourcesStorageService = scriptSourcesStorageService;
    }

    public Object runScript(final String code)
    {
        final var source = scriptSourcesStorageService.getSource(code);

        if (source == null)
        {
            throw new NullPointerException();
        }

        try (final var classLoader = new GroovyClassLoader(modulesService.getModulesClassLoader()))
        {
            final var scriptClass = (Class<?>) classLoader.parseClass(source);
            final var scriptClassConstructor = (Constructor<?>) scriptClass.getConstructor(new Class<?>[]{});
            final var instance = (Script) scriptClassConstructor.newInstance();
            return instance.run();
        }
        catch (final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | IOException e)
        {
            LOG.error(e.getMessage(), e);

            return null;
        }
    }
}
