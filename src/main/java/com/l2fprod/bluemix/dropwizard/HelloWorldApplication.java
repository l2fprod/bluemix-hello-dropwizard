package com.l2fprod.bluemix.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

  public static void main(String[] args) throws Exception {
    new HelloWorldApplication().run(args);
  }

  @Override
  public String getName() {
    return "hello-world";
  }

  @Override
  public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
    // server html pages found under src/main/resources/assets
    bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));    
  }

  @Override
  public void run(HelloWorldConfiguration configuration, Environment environment) {
    final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(),
        configuration.getDefaultName());
    final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
    environment.healthChecks().register("template", healthCheck);
    environment.jersey().register(resource);
  }

}