package co.edu.poligran;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Configuration
@AutoConfigureAfter(ThymeleafAutoConfiguration.class)
public class ThymeleafConfiguration {

	@Autowired
	SpringTemplateEngine engine;

	@PostConstruct
	public void setTemplateREsolver() {
		FileTemplateResolver templateResolver = new FileTemplateResolver();
//		templateResolver.setPrefix("./src/main/resources/templates/");
		templateResolver.setPrefix("/src/main/resources/templates/");

		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(1);
		engine.setTemplateResolver(templateResolver);
	}

}
