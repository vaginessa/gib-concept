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
## Push out OTAs
Okay, this is probably the part you wanna read. So basically, i was showering and thinking on a good method to allow the devs to push otas. I decided that, maybe just parse a json at intervals, from the same source. You dont even need to own a webserver, the json file can be hosted on your repo or just a gist will do, with the template. Just make sure your URL is **FINAL** and **you can make edits** to it. And don't be a retard, obviouly COPY THE RAW FILE LINK. For people who need some spoonfeeding, you can drop me a email on your query or just create a issue. 

Soz, the workflow works like dees:
1. Just built a new (apk/zip/whatever_update_package) and uploaded onto (http://vulcan.droidthug.me/firmware/7.x-mido-firmware-230717.zip)
2. Goes to my updater json
3. Paste my new file *direct* download link, build number (365) and build name(versionthreehundredsixtyfive)
4. State more stuff in json and save
5. User's device checks for update. If build number previously was 364, and now the device finds that ```$newVersion > 364```, then will automatically download and update. Just make sure your build number is not a string and is a data types like (float, int) and build number and your build number is not negative incremental. Any of such exceptions, you'll have to modify the code. But, try to keep the (displayed to user) build name a ```String``` and (displayed to internal update checker) build number a ```int```, ```double``` or ```float```

For lazy ppl or those with CI builds, there will be a script to help you automatically generate the json. Just adapt the function from the script and execute it on every build, easy shit. 

For those with webserver and a domain, you can do your own hosting yay, everything works the same

# Methods
After retrieving the updated package using the async task, we shall now install.
- Automated apk install *without prompt* (*.apk, root) 
- Automated apk install *with prompt* (*.apk, no-root) 
- Flash directly from the app, prompt to reboot (root, *.zip)
- Reboot to recovery prompt (*.zip, no-root)

## Fully automated shit
If you push out updates every 5 mins, you probably won't want your users to go in the app and verify every install. This is why there is 
At a very small cost, there will be a small background process checking for updates at intervals defined by the user
```sh
if (there is a update), update();
```
simple enough

### Annoying prompts
In order to not recieve prompts to click on the "install" (apk) or "reboot to recovery" (zip) everytime you push out a update, there has to be root to automate this "installation process"

### Storage
Up to the user, but there will be a internal && external storage check and they can then choose whether they want the installation package temp location and whether to delete it after install

### Theming
I might do "in app theming" but substratum does a good job.
Unless no major themes support, i don't think ill implement

### Contributions
Send a pull if you spot an issue,if possible, instead of just waiting for me to fix it. Make sure you indicate your pull before working on it in the "issues" tab, i don't wanna waste your time. 

Soz anyways, you have any features/update_package type requests, feel free to voice them. Also, gib some feedback too xD 
