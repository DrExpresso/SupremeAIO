# SupremeAIO
All in one checkout Tool request and browser based with multi-threading to automate checking out products on supremenewyork. 

![alt text](https://github.com/DrExpresso/SupremeAIO/blob/master/SupremeAIO_1.PNG)

<img src="https://github.com/DrExpresso/SupremeAIO/blob/master/bot.gif?raw=true" width="auto" height="auto" />



[DOWNLOAD LINK](https://github.com/DrExpresso/SupremeAIO/releases/tag/1.0.0)


# Requirements
 - [JAVA Download](https://java.com/download)
 
# Features
- **Saving and Loading Billing** - Save your billing information to a JSON File and load the billing directly from the file.
- **Request and Browser Module** - Browser module is purely based on selenium automation which can help with less card declines whereas the Request Module is based on JAVA net (HttpURLConnection), much faster but prone to more card declines.
- **Dark and Light UI Theme** - Easy to use user interface with all setup options on the same pane along with a dark theme. 
- **Keyword Finder, auto add to cart and checkout** - Bot will find the keywords for each task, add the product to the cart and try and checkout, Current for EU only. 
- **Multi threaded** - Support for multiple tasks at the same time to cop multiple items. 
- **Task Creator** - Create tasks in quick succession to setup 5 minutes before the drop.

# Todo
- [ ] **Documentation**
- [x] **Google Sign in Support**
- [ ] **Working console log**
- [ ] **Status message updates**
- [ ] **Stop tasks and quite all running browsers and requests**
- [ ] **Save and Load multiple billing profiles**
- [ ] **Proxy support on both modules**
- [ ] **Encrypt Billing profiles in AES**
- [ ] **Recaptcha Harvesting**
- [ ] **Keyword Droplist in Help menu**
- [ ] **US checkout Support**
- [ ] **Less RAM Usage**
- [ ] **JXBrowser and PhantomJS Module support**
- [ ] **Each Task checking out different product with different billing**
- [ ] **Success logger**
- [ ] **Start Timer**
- [ ] **Custom Installer**
- [ ] **Mac Support**

# Install
> - Download and **run the exe file as Administrator**, installer will automatically extract and install files to your local C drive in program files. Application is 32 bit.

# Usage
After installation you want to load the bot up and add your billing information under File > Profile Editor. 

Then you can set up a task on  left panel of the main bot. Keywords should be whole separated with spaces, no commas or 
other symbols. Keywords can be found [here](https://supremecommunity.com)

Select the desired category the item will be in, do not use the *new* category, size and colour. Use the Browser mode for now
as the Requests still needs finishing.

Input localhost in the proxy field, edit the number of tasks if you wish to do so, use the Default Profile and click create Task. 

Supreme drops at 11:00:00 GTM On Thursday so you want to start tasks at this 5-10 seconds before, Browser instances will launch
which are visible and will attempt to automate the checkout process.

### Disclaimer: Please keep in my mind this bot is still in beta and there might be bugs here and there in this case raise a issue and i will try fixing them. Also if anyone wants to help with development as this is a community project feel free to create a pull request.

# Development
![alt text](https://camo.githubusercontent.com/67530390b2eb4e3c74ff959538fb395b766d50fc/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a4156412d312e382532422d627269676874677265656e2e737667)
![alt text](https://camo.githubusercontent.com/2c5a56324be11da9e23553f610c3d22d131d3ec2/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6d6176656e2d332e302532422d627269676874677265656e2e737667)
![alt text](https://img.shields.io/badge/e(fx)clipse-3.0%2B-brightgreen.svg)
![alt text](https://img.shields.io/badge/JDK-8u72%2B-brightgreen.svg)

Requirements:
- [JAVA Development Kit 8 Update 72](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)
- [Scene Builder 2.0](https://gluonhq.com/products/scene-builder/)
- JAVAFX Plugin

```
# Clone this repository
$ git clone https://github.com/DrExpresso/SupremeAIO.git

# Go into the repository
$ cd SupremeAIO 

# Build the app
$ javac -d bin/ -cp src /src/makery/address/MainApp.java

Running the project
$ java -cp bin MainApp.java
```

# License
```
The MIT License (MIT)

Copyright (c) 2018 DrExpresso

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
