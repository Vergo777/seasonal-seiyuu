package moe.vergo.seasonalseiyuuapi.adapter.out.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class WebAdapterTestHelper {
    private WebAdapterTestHelper() {}

    public static String classpathFileToString(String filepath) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(filepath).getInputStream(), Charset.defaultCharset());
    }
}
