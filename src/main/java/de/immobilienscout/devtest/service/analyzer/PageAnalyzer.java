package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.devtest.domain.Tag;
import java.util.Map;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;


public interface PageAnalyzer {

    void analyze(@NonNull Document doc, @NonNull ImmutableMap.Builder<Tag,String> builder);

}
