package edu.shaykemelov.dynamiclogic.services.storage.modules;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryModuleSourcesStorageService implements ModuleSourcesStorageService {

    private final Map<String, String> modules = new ConcurrentHashMap<>();

    @Override
    public void add(final String code, final String source) {

        modules.put(code, source);
    }

    @Override
    public void remove(final String code) {

        modules.remove(code);
    }

    @Override
    public void clear() {

        modules.clear();
    }

    @Override
    public Set<String> listCodes() {

        return Set.copyOf(modules.keySet());
    }

    @Override
    public Map<String, String> listSources() {

        return Map.copyOf(modules);
    }

    @Override
    public String getSource(final String code) {

        return modules.get(code);
    }
}
