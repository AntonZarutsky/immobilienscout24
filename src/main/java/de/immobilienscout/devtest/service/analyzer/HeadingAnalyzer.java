package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.devtest.domain.Tag;
import java.util.Map;
import lombok.NonNull;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import static de.immobilienscout.devtest.domain.Tag.H1;
import static de.immobilienscout.devtest.domain.Tag.H2;
import static de.immobilienscout.devtest.domain.Tag.H3;
import static de.immobilienscout.devtest.domain.Tag.H4;
import static de.immobilienscout.devtest.domain.Tag.H5;
import static de.immobilienscout.devtest.domain.Tag.H6;


@Component
public class HeadingAnalyzer implements PageAnalyzer{

    private static final Map<Tag, String> headings =
            ImmutableMap.<Tag, String>builder()
                        .put(H1, "h1")
                        .put(H2, "h2")
                        .put(H3, "h3")
                        .put(H4, "h4")
                        .put(H5, "h5")
                        .put(H6, "h6")
                        .build();

    @Override
    public void analyze(@NonNull Document doc, @NonNull ImmutableMap.Builder<Tag,String> builder) {

        headings.forEach((tag, elName) -> {
            Integer count = doc.select(elName).size();
            builder.put(tag, count.toString());
        });

    }
}
