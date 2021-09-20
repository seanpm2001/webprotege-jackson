package edu.stanford.protege.webprotege.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import javax.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2021-09-17
 */
@JsonTypeName("ObjectMinCardinality")
public class OWLObjectMinCardinalityImplMixin {

    @JsonCreator
    public OWLObjectMinCardinalityImplMixin(@JsonProperty("property") @Nonnull OWLObjectPropertyExpression property,
                                            @JsonProperty("cardinality") int cardinality,
                                            @JsonProperty("filler") @Nonnull OWLClassExpression filler) {
    }
}
