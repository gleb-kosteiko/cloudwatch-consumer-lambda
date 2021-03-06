package gk.logconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gk.logconsumer.model.CloudWatchLogEvents;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class DecoderService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public CloudWatchLogEvents decode(String base64Encoded) {
        var decoded = Base64.getMimeDecoder().decode(base64Encoded);
        try {
            var in = new GZIPInputStream(new ByteArrayInputStream(decoded));
            var reader = new InputStreamReader(in);
            return MAPPER.readValue(reader, CloudWatchLogEvents.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
