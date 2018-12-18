# SupremeAIO
All in one checkout Tool request and browser based with multi-threading to automate checking out products on supremenewyork. **PLEASE RUN BOT AS ADMINISTRATOR**

![alt text](https://github.com/DrExpresso/SupremeAIO/blob/master/template.PNG)

<img src="https://raw.githubusercontent.com/DrExpresso/SupremeAIO/master/supremebot.gif?raw=true" width="auto" height="auto" />



[DOWNLOAD LINK](https://github.com/DrExpresso/SupremeAIO/releases)

[DOCUMENTATION LINK](https://drive.google.com/file/d/15v1hTzGvnKBL-A-k81Tk3EI5DmECQIs0/view?usp=sharing)


# Requirements
 - [JAVA Download](https://java.com/download)
 
# Features
- **Saving and Loading Billing** - Save your billing information to a JSON File and load the billing directly from the file.
- **Request and Browser Module** - Browser module is purely based on selenium automation which can help with less card declines whereas the Request Module is based on JAVA net (HttpURLConnection), much faster but prone to more card declines.
- **Dark and Light UI Theme** - Easy to use user interface with all setup options on the same pane along with a dark theme. 
- **Keyword Finder, auto add to cart and checkout** - Bot will find the keywords for each task, add the product to the cart and try and checkout, Current for EU only. 
- **Multi threaded** - Support for multiple tasks at the same time to cop multiple items. 
- **Task Creator** - Create tasks in quick succession to setup 5 minutes before the drop.
- **Instore signups** - Create tasks for instore supreme london to be able to cop items.

# Todo
- [x] **Documentation**
- [x] **Checkout delay**
- [x] **Google Sign in Support**
- [x] **Working console log**
- [x] **Status message updates**
- [x] **Task logs**
- [x] **Stop tasks and quite all running browsers and requests**
- [x] **Save and Load multiple billing profiles**
- [ ] **Proxy support on both modules**
- [ ] **Encrypt Billing profiles in AES**
- [ ] **Recaptcha Harvesting**
- [x] **Keyword Droplist in Help menu**
- [ ] **US checkout Support**
- [x] **Instore signups**
- [ ] **Less RAM Usage**
- [ ] **JXBrowser and PhantomJS Module support**
- [x] **Each Task checking out different product with different billing**
- [ ] **Success logger**
- [x] **Start Timer**
- [ ] **Custom Installer**
- [ ] **Mac Support**
- [x] **Splash screen at launch**
- [x] **Image Scraper**
- [ ] **Quick Tasks**

# Install
> - Download and **run the exe file as Administrator**, installer will automatically extract and install files to your local C drive in program files. Application is 32 bit. If you need any help contact my [Twitter](https://twitter.com/DrExpresso).

# Usage
[DOCUMENTATION LINK](https://drive.google.com/file/d/15v1hTzGvnKBL-A-k81Tk3EI5DmECQIs0/view?usp=sharing)

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

# Donations Appreciated
<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2BVMD39ZCYJ4W" rel="nofollow"><img src="https://camo.githubusercontent.com/f896f7d176663a1559376bb56aac4bdbbbe85ed1/68747470733a2f2f7777772e70617970616c6f626a656374732e636f6d2f656e5f55532f692f62746e2f62746e5f646f6e61746543435f4c472e676966" alt="paypal" data-canonical-src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" style="max-width:100%;"></a>

# License
```
The MIT License (MIT)

Copyright (c) 2018 DrExpresso

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
