package main.java.utils;

    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.devtools.DevTools;
    import org.openqa.selenium.devtools.v108.network.Network;
    import java.util.Optional;


    public class CaptureRequests {

            public void captureHttpRequests(WebDriver driver){

            /* declaring and initializing devtools class variable */
            DevTools devTools = (DevTools) driver;
            devTools.createSession();
            //After creating the session , we need to send a CDP(Customer data Platform) command
            // so that we would be able to capture the network log or the network traffic

            devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
            //x= devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty())).serialise();
             //When the parameter values would be passed in place of .empty() , the response returned in json or yml is serialized to an object.
                //here, x would be suppose y(e.g,person) class object
                // and the desired data is retrieved by using the properties of the object.
                //e.g x.name(property) retrieves name of the person (may be in name tag in json format data)
            //Now we have to add a listener to it to capture network traffic
            devTools.addListener(Network.requestWillBeSent(),
                    requestWillBeSent ->   {                                 // it is a consumer
                        System.out.println("Request url is"+requestWillBeSent.getRequest().getUrl() );
                        System.out.println("Request type is"+ requestWillBeSent.getRequest().getMethod());
                        System.out.println("Request header is"+requestWillBeSent.getRequest().getHeaders().toString());

                    });

            }


}
