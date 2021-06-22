package edu.shaykemelov.dynamiclogic.services.modules;

import static org.codehaus.groovy.control.Phases.CLASS_GENERATION;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.shaykemelov.dynamiclogic.services.storage.modules.MemoryModuleSourcesStorageService;

import groovy.lang.GroovyClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.codehaus.groovy.control.CompilationUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ModulesService
{
    private static final Logger LOG = LoggerFactory.getLogger(ModulesService.class);

    private final Lock lock = new ReentrantLock();

    private final MemoryModuleSourcesStorageService memoryModuleSourcesStorageService;

    private volatile GroovyClassLoader modulesClassLoader;

    @Autowired
    public ModulesService(MemoryModuleSourcesStorageService memoryModuleSourcesStorageService)
    {
        this.memoryModuleSourcesStorageService = memoryModuleSourcesStorageService;
        this.modulesClassLoader = new GroovyClassLoader();
    }

    public void reload()
    {
        lock.lock();

        try
        {
            final var classLoader = new GroovyClassLoader();
            final var compilationUnit = new CompilationUnit(classLoader);

            memoryModuleSourcesStorageService.listSources().forEach(compilationUnit::addSource);
            compilationUnit.compile(CLASS_GENERATION);

            for (final var groovyClass : compilationUnit.getClasses())
            {

                final var className = groovyClass.getName();
                final var classBytes = groovyClass.getBytes();

                classLoader.defineClass(className, classBytes);
            }

            modulesClassLoader.close();

            modulesClassLoader = classLoader;
        }
        catch (IOException e)
        {
            LOG.error(e.getMessage(), e);
        }
        finally
        {
            lock.unlock();
        }
    }

    public GroovyClassLoader getModulesClassLoader()
    {
        return modulesClassLoader;
    }
}
