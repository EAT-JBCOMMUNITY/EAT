package org.jboss.additional.testsuite.jdkall.present.web.json;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.4", "modules/testcases/jdkAll/Wildfly/web/src/main/java#27.0.0"})
@WebServlet(name = "JsonSerializerServlet", urlPatterns = {JsonSerializerServlet.URL_PATTERN})
public class JsonSerializerServlet extends HttpServlet {

    public static final String URL_PATTERN = "/JsonSerializerServlet";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            resp.setContentType("text/plain");

            Jsonb jsonb = JsonbBuilder.create(new JsonbConfig()
                    .withSerializers(new LocalSerializer())
                    .withDeserializers(new LocalDeserializer()));

            MapObjectLocalString mapObject = new MapObjectLocalString();
            mapObject.getValues().put(Locale.US, "us");

            String json = jsonb.toJson(mapObject);
            MapObjectLocalString resObject = jsonb.fromJson(json, MapObjectLocalString.class);
            resp.getWriter().write(resObject.toString());
        } catch (Exception ex) {
            throw new ServletException(ex.getCause());
        }
    }

    public static class LocalSerializer implements JsonbSerializer<Locale> {

        @Override
        public void serialize(Locale obj, JsonGenerator generator, SerializationContext ctx) {
            generator.write(obj.toLanguageTag());
        }
    }

    public static class LocalDeserializer implements JsonbDeserializer<Locale> {

        @Override
        public Locale deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
            return Locale.forLanguageTag(parser.getString());
        }
    }

    public static class MapObject<K, V> {

        private Map<K, V> values;

        MapObject() {
            this.values = new HashMap<>();
        }

        public Map<K, V> getValues() {
            return values;
        }

        public void setValues(Map<K, V> values) {
            this.values = values;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof MapObject) {
                MapObject<?, ?> to = (MapObject<?, ?>) o;
                return values.equals(to.values);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.values);
        }

        @Override
        public String toString() {
            return values.toString();
        }
    }

    public static class MapObjectLocalString extends MapObject<Locale, String> {
    };
}
