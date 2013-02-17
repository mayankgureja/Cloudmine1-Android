Cloudmine1-Android
==================

First CloudMine Android application, a proof-of-concept

How To Run
----------

The Cloudmine1.apk is located inside the /bin folder. Simply sideload it onto your Android phone or emulator and you're good to go.

Coding Environment
------------------

Written, built and tested on [Eclipse-ADT (Android Development Tools)](http://developer.android.com/tools/sdk/eclipse-adt.html)

Description
-----------

Cloudmine1 is an Android 4.2 app. It is a proof-of-concept of some of the basic functionality provided by [CloudMine](https://cloudmine.me), an online service that lets developers offload a lot of development to the cloud, such as databases and user accounts.

This application uses the CloudMine APIs to create, read, update and delete objects saved in the online database attached to the application. The main purpose of the application was to show the power of the authentication features of CloudMine. Using a read-only API Key, this application can access all the information stored in the database. However, a test user account with write permissions is required for any creation or updating.

This application does not show any information on the Android app itself. It simply outputs everything to System.out.

The proof-of-concept showcased here is the reading of data using the read-only API Key and updating of data using a test user account.

Screenshots
-----------

Screenshots of the application in action are available in the /screenshots folder.

Dependencies/Requirements
-------------------------

[CloudMine Android Library](https://cloudmine.me/docs/java)
* cloudmine-android-v0.5.jar

Everything else is part of the standard libraries.