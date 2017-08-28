# Updater
A simple updater manager personal/small projects with advanced ci integration. There won't be a need to messily push your ci/apk builds via telegram anymore and gib installation convienience

# Features
### User
- Multi-thread download
- Convinient af lmao
- Advanced CI integration
- Battery efficient wakelock
- Tunables for every aspect (update check intervals and more)
- Nice UI
- Automated flashing/installs
- Notifications

### Developers
- Easy integration with buildbots/slaves
- Your own prefered hosting
- Easy pushing of OTA (JSON can be updated this script)
- Customisable page for your own work
- Stable/ no downtimes because there is NO major hosting infrastructure implemented

# Home/Dashboard
Allows sorting of files per device, file type and popularity
Allow searching of devs

# Customisation
You need to gib some metadata on how you want things to be placed in your ```update.json```. For instance, you would have to define your name, desc and url and **build number**. 
```
{ 
"build" : 365,
"name":"Pizza Kernel",
"desc":"A simple kernel without all the junk features",
"url": "http://vulcan.droidthug.me/firmware/7.x-mido-firmware-230717.zip",
"properties": {
"target": ["hlte", "hltexx", "hltemo"],
    }
}
```

Package type, and other crap is autodetected

- **Types**

types = [zip, dtb, img, apk]

If you would like to overide, use ```{ "type": types[1,2,3 or 4] }```

- **Target**

Target refers to the DEVICE CODENAME

if applicable to all devices, use ```{ "target":generic }```

More variables will be added in future, under the properties object in the json
However, not all stuff are controlled by you. Update check intervals are defined by your users in settings so deal with it

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

## Repository
You can upload your sauce here in this repo [here](https://github.com/updater-repo) if you would like your module to be featured in the app. Builds + sauce can also be in the repo, but make sure there is a ```update.json``` somewhere.
You will have to send a pull to the main repo, defining your update json location

# Methods
After retrieving the updated package using the async task, we shall now install.
- Automated apk install *without prompt* (*.apk, root) 
- Automated apk install *with prompt* (*.apk, no-root) 
- Flash directly from the app, prompt to reboot (root, *.zip)
- Reboot to recovery prompt (*.zip,*.img no-root)

A small note to kernel devs, you can directly upload your dtb without having to place in anykernel if you are not distributing via img. The update process will automatically patch the zimage for you

## Fully automated shit
If you push out updates every 5 mins, you probably won't want your users to go in the app and verify every install. This is why there is Advanced CI that automates bleeding edge build installation process.
At a very small cost, there will be a small background process checking for updates at intervals defined by the user
```sh
//parse Json
//var update is a bool that, will be = true whenever the $newVersion > $oldVersion
if (!update){
//do nothing
} else { 
update(); // then set update = false
}
```
simple enough

### Json stuff
In JSON, values must be one of the following data types:

- a string
- a number
- a JSON object
- an array
- a boolean
- null

so yea make sure you dont give some invalid shit like a function or your json will fail parsing and no update will be recieved. Though, if things fail, a email will be sent for logging and crabs.

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
