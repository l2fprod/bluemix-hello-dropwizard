# bluemix-hello-dropwizard

[Dropwizard](http://www.dropwizard.io/) is a Java framework for developing ops-friendly, high-performance, RESTful web services.

This is a simple "Hello world!" app deploying a Dropwizard application to [IBM Bluemix](https://ibm.com/bluemix).

## Running the app on Bluemix

1. Create a Bluemix Account

  [Sign up][bluemix_signup_url] for Bluemix, or use an existing account.
    
2. Download and install the [Cloud-foundry CLI][cloud_foundry_url] tool

3. Clone the app to your local environment from your terminal using the following command

  ```
  git clone https://github.com/l2fprod/bluemix-hello-dropwizard.git
  ```

4. cd into this newly created directory

5. Build the application with Maven

  ```
  mvn package
  ```

6. Edit the `manifest.yml` file and change the `<application-name>` and `<application-host>` from `fredl-hello-dropwizard` to something unique.

	```
    applications:
    - name: fredl-hello-dropwizard
      host: fredl-hello-dropwizard
      memory: 256MB
	```

  The host you use will determinate your application url initially, e.g. `<application-host>.mybluemix.net`.

7. Connect to Bluemix in the command line tool and follow the prompts to log in.

	```
	$ cf api https://api.ng.bluemix.net
	$ cf login
	```
8. Push the application to Bluemix.

  ```
  $ cf push
  ```

And voila! You now have your very own instance running on Bluemix. Navigate to the application url, e.g. `<application-host>.mybluemix.net`.

## Details of the project

*This should work with other Cloud Foundry platforms too.*

### The project uses the Java Buildpack

The [manifest.mf](manifest.mf) specifies the [Java Buildpack](https://github.com/cloudfoundry/java-buildpack). This buildpack allows to run arbitrary Java applications. This line specifies the buildpack:

  ```
  buildpack: https://github.com/cloudfoundry/java-buildpack.git
  ```

### The project uses the Dist ZIP container variant of the Java Buildpack

I could not figure out whether Dropwizard allows to configure the app without a YAML file, or by packaging the YAML in the app JAR, or if the buildpack would allow to deploy one JAR and additional files (if there is, ping me, it will make the sample a bit easier). So I rely on using the Dist ZIP container provided by the buildpack. It provides a way to push the app as an archive (a ZIP) containing a *bin* and a *lib* directory. The *bin* directory can contain an *app.bat* start script. This is how I start the app and passes all needed parameters in [app.bat](src/bin/app.bat]:

  ```
  #!/bin/sh
  echo "Starting DropWizard app"
  $JAVA_HOME/bin/java $JAVA_OPTS -Ddw.server.connector.port=$PORT -jar lib/*.jar server lib/*.yml
  ```

  * JAVA_OPTS is provided by the buildpack as is JAVA_HOME.
  * -Ddw.server.connector.port=$PORT allows to pass the Cloud Foundry port for our app to communicate with the outside world. It overrides the value defined in the [YAML file](hello-world.yml).
  * the *.jar and *.yml make the script reusable as is with other apps - but you could also hardcode the right values.

### The project overrides the default Dropwizard path

In the [YAML file](hello-world.yml), the application context path is set to '/' and the resources to '/api' instead of the '/application' default. This is only the place where the Jetty server port is redefined - however its value will not be used but this required for the previous -Ddw.server.connector.port property to work:

  ```
  server:
    type: simple
    applicationContextPath: / # this makes the assets (index.html) available at http://host
    rootPath: /api/* # resources http://host/api/
    connector:
      type: http
      port: 8080 # Server port is overriden on startup in src/main/bin/app.bat
  ```
    
### The project creates a custom Maven assembly

As I'm using a Dist ZIP, I need to build this ZIP so it can be pushed. This is achieved by an assembly defined in [bluemix.xml](src/assembly/bluemix.xml) and listed in [pom.xml plugins](pom.xml). The resulting ZIP looks like:

  ```
  Archive:  target/dropwizard-0.0.1-SNAPSHOT-bluemix.zip
    Length    Name
    --------  ----
           0  bin/
         137  bin/app.bat
           0  lib/
         310  lib/hello-world.yml
    14150673  lib/dropwizard-0.0.1-SNAPSHOT.jar
    --------  -------
    14151120  5 files
  ```


## Troubleshooting

To troubleshoot your Bluemix app the main useful source of information is the logs. To see them, run:

  ```sh
  $ cf logs <application-name> --recent
  ```
  
---

This project is a sample application created for the purpose of demonstrating the use of Dropwizard with IBM Bluemix.
The program is provided as-is with no warranties of any kind, express or implied.

[bluemix_signup_url]: https://console.ng.bluemix.net/?cm_mmc=GitHubReadMe-_-BluemixSampleApp-_-Node-_-Workflow
[cloud_foundry_url]: https://github.com/cloudfoundry/cli

