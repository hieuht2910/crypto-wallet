package com.hieu.crypto.service.price;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieu.crypto.model.PairDefinition;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PairDefinitionService {

    private static final String PAIRS_FILE = "pairs-definition.json";

    public List<PairDefinition> readPairs() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(PAIRS_FILE);
        InputStream input = resource.getInputStream();

        List<PairDefinition> pairs = mapper.readValue(input, new TypeReference<>() {});
        return pairs;
    }
}
