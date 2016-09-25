package de.immobilienscout.devtest.api.dto;

import de.immobilienscout.devtest.domain.Tag;
import java.util.Map;
import lombok.Value;

@Value
public class AnalysisResult {

    private Map<Tag, String> analysis;

}
