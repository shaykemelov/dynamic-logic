package edu.shaykemelov.dynamiclogic.services.storage;

import java.util.Map;
import java.util.Set;

public interface SourcesStorageService {

    void add(final String code, final String source);

    void remove(final String code);

    void clear();

    Set<String> listCodes();

    Map<String, String> listSources();

    String getSource(final String code);
}
