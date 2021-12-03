package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class SetConverter implements AttributeConverter<Set<String>, String> {

    private static final String SEPARATOR = "; ";

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        return attribute == null ? null : String.join(SEPARATOR, attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Arrays.stream(dbData.split(SEPARATOR)).collect(Collectors.toSet());
    }
}
