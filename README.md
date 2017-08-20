# Updater
A simple update + credits template for whatever your're doing (plus feedback xD)

# Customisation
in res/values/arrays.xml, there will be a string-array that you can use to disable/enable features
The array should look like *this*
```sh
    <string-array name="features">
        <item>true</item> // Core OTA
        <item>false</item> // Profiles
        <item>true</item> // tunables
        <item>true</item> // credits
        <item>true</item> //Feedback
        <item>false</item>  // with root methods
    </string-array>
```
It stores ONLY boolean tf values, and it will ONLY disable features
# Download
A full guide will be here on how to define the download location and push out OTAs

# Methods
After retrieving the updated package using the async task, we shall now install.
- Automated apk install *without prompt* (*.apk, root) 
- Automated apk install *without prompt* (*.apk, root) 
- Flash directly from the app, prompt to reboot (root, *.zip)
- Reboot to recovery prompt (*.zip)

## Fully automated shit
If you push out updates every 5 mins, you probably won't want your users to go in the app and verify every install. This is why there is 
At a very small cost, there will be a small background process checking for updates at intervals defined by the user
```sh
if (there is a update), update();
```
simple enough

### Annoying prompts
In order to not recieve prompts to click on the "install" (apk) or "reboot to recovery" (zip) everytime you push out a update, there has to be root to automate this "installation process"
