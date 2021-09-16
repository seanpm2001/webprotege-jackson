package edu.stanford.protege.webprotege.jackson;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.semanticweb.owlapi.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import uk.ac.manchester.cs.owl.owlapi.*;

@SpringBootApplication
public class WebProtegeJacksonApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProtegeJacksonApplication.class, args);
	}

	@Bean
	@ConditionalOnMissingBean
	OWLDataFactory dataFactory() {
		return new OWLDataFactoryImpl();
	}

	@Bean
	GuavaModule guavaModule() {
		return new GuavaModule();
	}

	@Bean
	public ObjectMapper objectMapper(OWLDataFactory dataFactory) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
		mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new GuavaModule());
		SimpleModule module = new SimpleModule();


		module.addSerializer(new EntityTypeSerializer());
		module.addDeserializer(EntityType.class, new EntityTypeDeserializer());

		module.setMixInAnnotation(OWLAnnotation.class, OWLAnnotationMixin.class);
		module.setMixInAnnotation(OWLAnnotationImpl.class, OWLAnnotationImplMixin.class);

		module.addSerializer(OWLLiteral.class, new OWLLiteralSerializer());
		module.addDeserializer(OWLLiteral.class, new OWLLiteralDeserializer(dataFactory));

		module.setMixInAnnotation(OWLOntologyID.class, OWLOntologyIDMixin.class);

		module.setMixInAnnotation(NodeID.class, NodeIDMixin.class);


		module.addSerializer(AxiomType.class, new AxiomTypeSerializer());
		module.addDeserializer(AxiomType.class, new AxiomTypeDeserializer());

		module.setMixInAnnotation(OWLAxiom.class, OWLAxiomMixin.class);

		module.setMixInAnnotation(OWLSubClassOfAxiom.class, OWLSubClassOfAxiomMixin.class);
		module.setMixInAnnotation(OWLSubClassOfAxiomImpl.class, OWLSubClassOfAxiomImplMixin.class);

		module.setMixInAnnotation(OWLEquivalentClassesAxiom.class, OWLEquivalentClassesAxiomMixin.class);
		module.setMixInAnnotation(OWLEquivalentClassesAxiomImpl.class, OWLEquivalentClassesAxiomImplMixin.class);

		module.setMixInAnnotation(OWLDisjointClassesAxiom.class, OWLDisjointClassesAxiomMixin.class);
		module.setMixInAnnotation(OWLDisjointClassesAxiomImpl.class, OWLDisjointClassesAxiomImplMixin.class);

		module.setMixInAnnotation(OWLAnnotationAssertionAxiom.class, OWLAnnotationAssertionAxiomMixin.class);
		module.setMixInAnnotation(OWLAnnotationAssertionAxiomImpl.class, OWLAnnotationAssertionAxiomImplMixin.class);

		module.setMixInAnnotation(OWLClassAssertionAxiom.class, OWLClassAssertionAxiomMixin.class);
		module.setMixInAnnotation(OWLClassAssertionAxiomImpl.class, OWLClassAssertionAxiomImplMixin.class);

		module.setMixInAnnotation(OWLDeclarationAxiom.class, OWLDeclarationAxiomMixin.class);
		module.setMixInAnnotation(OWLDeclarationAxiomImpl.class, OWLDeclarationAxiomImplMixin.class);

		module.setMixInAnnotation(OWLObjectPropertyAssertionAxiom.class, OWLObjectPropertyAssertionAxiomMixin.class);
		module.setMixInAnnotation(OWLObjectPropertyAssertionAxiomImpl.class, OWLObjectPropertyAssertionAxiomImplMixin.class);

		module.setMixInAnnotation(OWLDataPropertyAssertionAxiom.class, OWLDataPropertyAssertionAxiomMixin.class);
		module.setMixInAnnotation(OWLDataPropertyAssertionAxiomImpl.class, OWLDataPropertyAssertionAxiomImplMixin.class);


		module.setMixInAnnotation(OWLSubObjectPropertyOfAxiom.class, OWLSubObjectPropertyOfAxiomMixin.class);
		module.setMixInAnnotation(OWLSubObjectPropertyOfAxiomImpl.class, OWLSubObjectPropertyOfAxiomImplMixin.class);
		
		module.setMixInAnnotation(OWLDataPropertyExpression.class, OWLDataPropertyExpressionMixin.class);

		module.setMixInAnnotation(OWLObjectPropertyExpression.class, OWLObjectPropertyExpressionMixin.class);
		module.setMixInAnnotation(OWLObjectInverseOf.class, OWLObjectInverseOfMixin.class);
		module.setMixInAnnotation(OWLObjectInverseOfImpl.class, OWLObjectInverseOfMixinImpl.class);

		module.setMixInAnnotation(OWLEntity.class, OWLEntityMixin.class);
		// We need this to support legacy representation of entities that use "type" instead of "@type"
		// for the field name that identifies the type id
		module.addDeserializer(OWLEntity.class, new OWLEntityDeserializer<>(dataFactory));

		module.setMixInAnnotation(OWLClass.class, OWLClassMixin.class);
		module.addDeserializer(OWLClassImpl.class, new OWLEntityDeserializer<>(dataFactory, EntityType.CLASS));

		module.setMixInAnnotation(OWLDatatype.class, OWLDatatypeMixin.class);
		module.addDeserializer(OWLDatatypeImpl.class, new OWLEntityDeserializer<>(dataFactory, EntityType.DATATYPE));

		module.setMixInAnnotation(OWLProperty.class, OWLPropertyMixIn.class);

		module.setMixInAnnotation(OWLObjectProperty.class, OWLObjectPropertyMixin.class);
		module.addDeserializer(OWLObjectPropertyImpl.class, new OWLEntityDeserializer<>(dataFactory, EntityType.OBJECT_PROPERTY));

		module.setMixInAnnotation(OWLDataProperty.class, OWLDataPropertyMixin.class);
		module.addDeserializer(OWLDataPropertyImpl.class, new OWLEntityDeserializer<>(dataFactory, EntityType.DATA_PROPERTY));
//
		module.setMixInAnnotation(OWLAnnotationProperty.class, OWLAnnotationPropertyMixin.class);
		module.addDeserializer(OWLAnnotationPropertyImpl.class, new OWLEntityDeserializer<>(dataFactory, EntityType.ANNOTATION_PROPERTY));

		module.setMixInAnnotation(OWLIndividual.class, OWLIndividualMixin.class);

		module.setMixInAnnotation(OWLNamedIndividual.class, OWLNamedIndividualMixin.class);
		module.addDeserializer(OWLNamedIndividualImpl.class, new OWLEntityDeserializer<>(dataFactory, EntityType.NAMED_INDIVIDUAL));

		module.setMixInAnnotation(OWLAnonymousIndividual.class, OWLAnonymousIndividualMixin.class);
		module.setMixInAnnotation(OWLAnonymousIndividualImpl.class, OWLAnonymousIndividualImplMixin.class);

		module.setMixInAnnotation(OWLObjectInverseOf.class, OWLObjectInverseOfMixin.class);
		module.setMixInAnnotation(OWLObjectInverseOfImpl.class, OWLObjectInverseOfMixin.class);

		module.setMixInAnnotation(OWLClassExpression.class, OWLClassExpressionMixin.class);

		module.setMixInAnnotation(OWLLiteral.class, OWLLiteralMixin.class);

		module.setMixInAnnotation(IRI.class, IRIMixin.class);

		mapper.addHandler(new MissingTypeIdDeserializationProblemHandler());


		mapper.registerModule(module);

		return mapper;
	}
}
