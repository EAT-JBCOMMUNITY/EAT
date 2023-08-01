package org.jboss.additional.testsuite.jdkall.present.jaxrs.reactive;

import java.util.Objects;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
public class Thing {

   private String name;

   public Thing() {
   }

   public Thing(final String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String toString() {
      return "Thing[" + name + "]";
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Thing)) {
         return false;
      }
      return name.equals(((Thing) o).name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }
}
