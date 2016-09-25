package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.immobilienscout.devtest.domain.Tag;
import de.immobilienscout.devtest.utils.UrlUtils;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import static de.immobilienscout.devtest.domain.Tag.EXTERNAL_LINK;
import static de.immobilienscout.devtest.domain.Tag.INTERNAL_LINK;


@Component
@Slf4j
public class LinkAnalyzer implements PageAnalyzer{

    @Override
    public void analyze(@NonNull Document doc, @NonNull ImmutableMap.Builder<Tag,String> builder) {

        Pattern internalLinkPattern = UrlUtils.internalLinksPattern(doc.baseUri());

        Map<Tag, Long> links =
            doc.select("a[href]")
               .stream()
               .collect(
                   Collectors.groupingBy(el -> this.getAccessibility(el, internalLinkPattern),
                       Collectors.counting())
               );

        ImmutableList.of(INTERNAL_LINK, EXTERNAL_LINK).forEach(
            tag -> {
                builder.put(tag, String.valueOf(links.getOrDefault(tag, 0l)));
            }
        );
    }


    private Tag getAccessibility(Element element, Pattern internalLinkPattern) {

        String href = element.absUrl("href");

        return internalLinkPattern.matcher(href).matches() ? INTERNAL_LINK : EXTERNAL_LINK;

    }

}

