package edu.stanford.protege.webprotege.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;

import javax.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2021-09-17
 */
@JsonTypeName("DataMinCardinality")
public class OWLDataMinCardinalityImplMixin {

    @JsonCreator
    public OWLDataMinCardinalityImplMixin(@JsonProperty("property") @Nonnull OWLDataPropertyExpression property,
                                          @JsonProperty("cardinality") int cardinality,
                                          @JsonProperty("filler") @Nonnull OWLDataRange filler) {
    }
}
