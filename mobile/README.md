# **Chow-Me-In Mobile**

* [**Setup**](#setup)

## **SETUP**

Setup is as normal like any other Android application.
<br/><br/>
A hangup may be getting the Google Sign In working, this requires a **signed** APK.<br/>
This is done using the `chow-sign.jks` file in this directory.<br/>
To do so, in Android Studio go to File > Project Structure... and click on the "Signing" tab.<br/>
Create a configuration, the name of ours is `config`, choose file path to the .jks file, the key alias is `key`, and both passwords are `frankiebois`.<br/>
Next go to the "Build Types" tab, for each of your build types you should update the `Signing Config` to the `config` file we just created.<br/>
<br/>
We should now be able to run our application in Android Studio like normal.<br/>
You can also generate the release APK by going from Build > Generate Signed APK and using the same config as above.

To see a diagram of our mobile client architecture, see [here](../doc/chowmein%20mobile%20client%20architecture%20diagram.pdf)
