package com.github.sir4ur0n.java7functional.configuration;

import static io.vavr.API.Set;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class SwaggerConfiguration {

  private TypeResolver typeResolver;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(ignoreErrorEndpoint()::test)
        .build()
        .produces(Set("application/json").toJavaSet())
        .consumes(Set("application/json").toJavaSet())
        .protocols(Set("http").toJavaSet())
        .alternateTypeRules(getAlternateTypeRules())
        .genericModelSubstitutes(Option.class, Optional.class, ResponseEntity.class);
  }

  /**
   * As SpringFox can't deal with Vavr's model, we have to define alternative rules
   * See https://github.com/springfox/springfox/issues/1971
   */
  private AlternateTypeRule[] getAlternateTypeRules() {
    return new AlternateTypeRule[]{
        newRule(
            typeResolver.resolve(ResponseEntity.class, typeResolver.resolve(List.class, WildcardType.class)),
            typeResolver.resolve(java.util.List.class, WildcardType.class),
            Ordered.HIGHEST_PRECEDENCE
        ),
        newRule(
            typeResolver.resolve(ResponseEntity.class, typeResolver.resolve(Set.class, WildcardType.class)),
            typeResolver.resolve(java.util.List.class, WildcardType.class),
            Ordered.HIGHEST_PRECEDENCE
        ),
        newRule(
            typeResolver.resolve(ResponseEntity.class, typeResolver.resolve(Seq.class, WildcardType.class)),
            typeResolver.resolve(java.util.List.class, WildcardType.class),
            Ordered.HIGHEST_PRECEDENCE
        ),
        newRule(
            typeResolver
                .resolve(ResponseEntity.class, typeResolver.resolve(Map.class, WildcardType.class, WildcardType.class)),
            typeResolver.resolve(java.util.Map.class, WildcardType.class, WildcardType.class),
            Ordered.HIGHEST_PRECEDENCE
        ),
        newRule(
            typeResolver.resolve(List.class, WildcardType.class),
            typeResolver.resolve(java.util.List.class, WildcardType.class)
        ),
        newRule(
            typeResolver.resolve(Set.class, WildcardType.class),
            typeResolver.resolve(java.util.List.class, WildcardType.class)
        ),
        newRule(
            typeResolver.resolve(Seq.class, WildcardType.class),
            typeResolver.resolve(java.util.List.class, WildcardType.class)
        ),
        newRule(
            typeResolver.resolve(Map.class, WildcardType.class, WildcardType.class),
            typeResolver.resolve(java.util.Map.class, WildcardType.class, WildcardType.class)
        )
    };
  }

  private Predicate<String> ignoreErrorEndpoint() {
    return PathSelectors.regex("/error").negate();
  }

}
