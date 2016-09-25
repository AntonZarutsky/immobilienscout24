package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.devtest.domain.Tag;
import lombok.NonNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import static de.immobilienscout.devtest.domain.Tag.LOGIN_FORM;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;


@Component
public class LoginFormAnalyzer implements PageAnalyzer{

    @Override
    public void analyze(@NonNull Document doc, @NonNull ImmutableMap.Builder<Tag,String> builder) {

        Long formsCount =
            doc.select("form")
               .stream()
               .filter(form -> containElement(form, "input[type=text]"))
               .filter(form -> containElement(form, "input[type=password]"))
               .filter(form -> containElement(form, "button[type=submit]"))
               .count();

        builder.put(LOGIN_FORM, formsCount.toString());
    }

    private boolean containElement(Element element, String child) {

        return isNotEmpty(element.select(child));
    }
}

