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

### Troubleshooting

To troubleshoot your Bluemix app the main useful source of information is the logs. To see them, run:

  ```sh
  $ cf logs <application-name> --recent
  ```
  
---

This project is a sample application created for the purpose of demonstrating the use of Dropwizard with IBM Bluemix.
The program is provided as-is with no warranties of any kind, express or implied.

[bluemix_signup_url]: https://console.ng.bluemix.net/?cm_mmc=GitHubReadMe-_-BluemixSampleApp-_-Node-_-Workflow
[cloud_foundry_url]: https://github.com/cloudfoundry/cli

