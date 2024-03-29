/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.backend.lucene.projectionconstructor;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FieldProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ObjectProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

import java.util.Collections;
import java.util.List;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jpa/src/main/java#30.0.0"})
public class AuthorDTO {
    public final String firstName;
    public final String lastName;
    public final List<BookDTO> books;

    public AuthorDTO(String firstName, String lastName) {
        this(firstName, lastName, Collections.emptyList());
    }

    @ProjectionConstructor
    public AuthorDTO(// These @FieldProjection annotations and @ObjectProjection.path wouldn't be necessary
                     // with a record or a class compiled with -parameters
                     @FieldProjection(path = "firstName") String firstName,
                     @FieldProjection(path = "lastName") String lastName,
                     @ObjectProjection(path = "books", includeDepth = 1) List<BookDTO> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }
}
