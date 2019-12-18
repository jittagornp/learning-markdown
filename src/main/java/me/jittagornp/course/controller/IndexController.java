/*
 * Copyright 2017-Current jittagornp.me
 */
package me.jittagornp.course.controller;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 *
 * @author jitta
 */
@Controller
public class IndexController {

    @ResponseBody
    @GetMapping({"", "/"})
    public Mono<String> getIndex() {

        final MutableDataSet options = new MutableDataSet();

        final Parser parser = Parser.builder(options).build();
        final HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        final ClassPathResource resource = new ClassPathResource("static/index.md");
        try (final Reader reader = new InputStreamReader(resource.getInputStream())) {
            final Node document = parser.parseReader(reader);
            final String html = renderer.render(document);
            return Mono.just(html);
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

}
