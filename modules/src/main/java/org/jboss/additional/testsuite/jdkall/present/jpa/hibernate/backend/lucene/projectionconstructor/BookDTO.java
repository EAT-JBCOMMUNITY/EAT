/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.additional.testsuite.jdkall.present.jpa.hibernate.backend.lucene.projectionconstructor;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FieldProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IdProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ObjectProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

import java.util.List;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jpa/src/main/java#30.0.0"})
public class BookDTO {
    public final long id;
    public final String title;
    public final List<AuthorDTO> authors;

    @ProjectionConstructor
    public BookDTO(@IdProjection long id,
                   // This @FieldProjection annotations and @ObjectProjection.path wouldn't be necessary
                   // with a record or a class compiled with -parameters
                   @FieldProjection(path = "title") String title,
                   @ObjectProjection(path = "authors", includeDepth = 1) List<AuthorDTO> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }
}
