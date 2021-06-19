package edu.shaykemelov.dynamiclogic.services.storage.scripts;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryScriptSourcesStorageService implements ScriptSourcesStorageService {

    private final Map<String, String> scripts = new ConcurrentHashMap<>();

    @Override
    public void add(final String code, final String source) {

        scripts.put(code, source);
    }

    @Override
    public void remove(final String code) {

        scripts.remove(code);
    }

    @Override
    public void clear() {

        scripts.clear();
    }

    @Override
    public Set<String> listCodes() {

        return Set.copyOf(scripts.keySet());
    }

    @Override
    public Map<String, String> listSources() {

        return Map.copyOf(scripts);
    }

    @Override
    public String getSource(final String code) {

        return scripts.get(code);
    }
}
