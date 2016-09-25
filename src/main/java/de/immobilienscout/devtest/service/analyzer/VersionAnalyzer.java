package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.devtest.domain.Tag;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.val;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import static de.immobilienscout.devtest.domain.Tag.VERSION;


@Component
public class VersionAnalyzer implements PageAnalyzer{

    @Override
    public void analyze(@NonNull Document doc, @NonNull ImmutableMap.Builder<Tag,String> builder) {

        String docType = doc.childNodes()
                         .stream()
                         .filter(node -> node instanceof DocumentType)
                         .map(Node::toString)
                         .collect(Collectors.joining("; "));

        builder.put(VERSION, docType);
    }
}
