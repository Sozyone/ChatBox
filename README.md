![Shape2](RackMultipart20220221-4-11dad7z_html_a63612b476a1e314.gif) ![Shape1](RackMultipart20220221-4-11dad7z_html_a63612b476a1e314.gif) ![](RackMultipart20220221-4-11dad7z_html_f5d7b18bcdeb6a79.png)

# Android Application

GÄVLE UNIVERSITY

![Shape4](RackMultipart20220221-4-11dad7z_html_3d7e6bf4c0671732.gif) ![Shape3](RackMultipart20220221-4-11dad7z_html_3d7e6bf4c0671732.gif)

michael loubier

Mike\_lou89@hotmail.com

February 11, 2022

# Table of Contents

[1Introduction 2](#_Toc95485152)

[2Theory 2](#_Toc95485153)

[2.1 Firebase tools used: 2](#_Toc95485154)

[3Method 3](#_Toc95485155)

[4Results 7](#_Toc95485156)

[5Concluding Discussion 11](#_Toc95485157)

[6References 12](#_Toc95485158)

# 1Introduction

In this project, I am creating a chat application using Firebase services. The program&#39;s graphical interface is very intuitive. You first login or register a new account, then you can start chatting. You can also edit basic information in your profile like your username, email, profile picture, and application theme (light or dark). This program is very basic so it could be used as a template for a future project. That is actually the goal of this project: to create a functional and usable template app that can be improved upon in the future.

I make use of four Firebase tools in this project: Authentication, Realtime Database, Analytics, and Storage. I am also using Firebase UI for creating the whole sign in and register process, so I don&#39;t have to create whole classes and xml files for that, only a few lines of code using Firebase UI builders.

# 2Theory

First of all, let&#39;s talk about Firebase since it is an integral part of this project. Firebase is a platform that offers products and solutions for creating mobile and web applications. It is now owned and developed by Google. At first, it was an independent company made in 2011, but in 2014, Google acquired them. It is now their flagship service for app development. [1]

## 2.1 Firebase tools used:

**- Realtime Database** : Real time syncing for JSON data. It is a cloud-hosted NoSQL database that lets us store and sync data between our users in real time. [2]

**- Authentication** : Provides easy sign-in methods with any platform. It builds secure authentication systems easily that keeps end users experience in mind. It provides an end-to-end identity solution, supporting email and password accounts, phone auth, and Google, Twitter, Facebook, and GitHub login, and more. [2]

**- Cloud Storage** : Used to store our users&#39; photos and videos. It is designed to help us store user files in the cloud. It is also easily manageable. [2]

**- Analytics** : It is used to analyze user engagement and app usage. [2]

# 3Method

I started by creating a new project and connecting it to Firebase. This is done very easily using the Tools -\&gt; Firebase in android studio. Afterwards, I had to make sure to have all the dependencies in the &quot;build.gradle&quot; module level file as seen in the picture 1 below.

![Picture 4](RackMultipart20220221-4-11dad7z_html_12e6de344423569e.gif)

Picture 1: Firebase dependencies.

After that, I had to also enable all the tools I wanted in the Firebase console under my project. This console is accessible online after creating an account and linking a project to their website. In the picture 2 below, is the authentication tool enabled for my project. Some accounts that were made inside my app are seen there. All new accounts are saved automatically.

![Picture 1](RackMultipart20220221-4-11dad7z_html_15e6d0e6c95d1291.gif)

Picture 2: Firebase authentication.

I also use this authentication tool to completely create my sign in and register UI. This is achieved using FirebaseUI sign in provider builder. I can easily add almost any kind of login methods using this builder. I could easily add Facebook, Google, Github, etc… See the picture 3 below to see what the builder code looks like.

![Picture 10](RackMultipart20220221-4-11dad7z_html_f7c4a06f5cb15406.gif)

Picture 3: FirebaseUI sign in providers.

This way I did not have to spend time creating a Login and a Register activity. I could also easily add my app logo in there and even some links to terms and privacy papers (although I just used example.com for that). A picture of what my login page looks like will be down in the results section of this document.

In the picture 4 below, my Firebase real time database is shown. It is organized as a JSON file. Very easily created with a few lines of code. It really just creates itself and updates itself as the app is used. In the code, I just use database references and Firebase takes care of everything else.

At first, it was a bit difficult to use and understand (as with any other new tools used) just because I didn&#39;t know anything about how Firebase works, but after some research it became very easy. It is very enjoyable to use!

![Picture 5](RackMultipart20220221-4-11dad7z_html_4ed6e326d28a0de9.gif)

Picture 4: Firebase real time database.

In the picture 5 below is my Firebase storage tool. It saves profile pictures of all users and updates itself if the user changes picture. It also creates folders on its own for each user. You can also see the path for a profile picture in the screenshot below (DB Reference -\&gt; users -\&gt; username).

![Picture 3](RackMultipart20220221-4-11dad7z_html_27a1fcafeed697f.gif)

Picture 5: Firebase storage.

# 4Results

In the end, I was able to make my simple chat app with everything working properly in connection to Firebase. When launching the app, the user arrives to the sign in page. The user can either sign in or register. This page is as seen in the picture 6 below. Everything on it is built by FirebaseUI.

![Picture 1](RackMultipart20220221-4-11dad7z_html_2f6fa3f10ca10274.gif)

Picture 6: App Sign in page.

When selecting &quot;Sign in with email&quot;, the app sends the user to a login page where they must enter their email. If the email isn&#39;t in the database, it then creates an account by asking for a username and password; it puts the user through a registration process in other words. If the email is in the database thought, it simple asks for the password then logs in. So, the login and register process is done through the same page, there is no special registration page really. See picture 7 and 8 below.

![Picture 11](RackMultipart20220221-4-11dad7z_html_76fde1da533ec137.gif) ![Picture 3](RackMultipart20220221-4-11dad7z_html_f2050658677aa39.gif)

Picture 7: Email sign in selected. Picture 8: Email already registered.

The picture 9 below shows what happens when an email not already in the database is entered. It then asks information to create this new user account (sign up) then send the new user to the app chat directly after. So, everything in terms of login and registering is very quick and easy, all thanks to FirebaseUI sign in builder.

![Picture 4](RackMultipart20220221-4-11dad7z_html_3ec040a66ba38f5f.gif)

Picture 9: Registration (sign up).

Next, I am showing what the chat page looks like (see picture 10 below). I am also showing what the option menu does (the three dots icon). This menu is only used to sign out or delete the account. When an account is deleted, it is also deleted from the database, obviously.

![Picture 5](RackMultipart20220221-4-11dad7z_html_cf4245b9ad65ea27.gif) ![Picture 6](RackMultipart20220221-4-11dad7z_html_2def8947e5dfdbe1.gif)

Picture 10: Chat page and options.

In the menu bar up top, two icons can be seen. One sends the user to the chat and the other sends the user to their profile. A screenshot of the profile page is shown in the picture 11 below.

In the profile page, the user can see and update some their information such as:

- Profile picture

- Username

- Email

- Theme

The profile picture is changed by clicking on it, then selecting a picture from the phone&#39;s library. The username and email are changed by clicking on the &quot;Update Profile&quot; button at the bottom of the page. The theme is changed by simply clicking the desired radio button.

![Picture 7](RackMultipart20220221-4-11dad7z_html_790efe36eac860e7.gif) ![Picture 8](RackMultipart20220221-4-11dad7z_html_5c49c79a07e0b5d5.gif)

Picture 11: Profile page.

One problem with the theme is that it doesn&#39;t save itself, so it only is changed while the app is running. If the app is closed, the theme always goes back to light.

# 5Concluding Discussion

In conclusion, I was able to achieve my goals with this app which was to create a simple chat app that can then be improved upon in the future. I am very happy with my sign in/register page, since it is very functional, and it took almost no code at all to create other than some Firebase imports. It is also very easy to add future login methods if needed.

This way, I ended up having a very modern process for login in or registering and it does the job perfectly. Who needs a crazy customized login page that adds probably 300 lines of code (plus resource files) to the project for the same result with probably a ton of bugs? Even using FirebaseUI builder, you get some level of customization, and it uses a user-friendly modern UI, so why not use that?

Other parts of my code are probably not as optimized as I would want it to be though. For example, I should manage my users better since I am using parts of the FirebaseUser and my own user objects to do things in the app. I should put everything into my own user object instead.

At first, I thought I would use only FirebaseUser properties to make my app as simple as possible, but I quickly realized that doing this would be making my users way too simple and not customizable at all, which went against my goal of making a template app that can be easily improved upon.

I also didn&#39;t use my user&#39;s profile information for anything other than for the user to look at. I should have found a way to add the profile pictures in the chat, and also to be able to click on users in chat to open their profiles. This way other users could see each other&#39;s profiles which would have been way more natural.

So, this project should definitely be worked on a little more before any kind of release. As I said before, this is only a functional template that could be useful for creating a chat app. It does its job in my opinion.

# 6References

| [1] | F. Lardinois, &quot;Google Acquires Firebase To Help Developers Build Better Real-Time Apps,&quot; TechCrunch, 21 10 2014. [Online]. Available: https://tinyurl.com/yx76bcvm. [Accessed 09 02 2022]. |
| --- | --- |
| [2] | Firebase, &quot;Products / Build,&quot; Google Firebase, 2022. [Online]. Available: https://firebase.google.com/products-build. [Accessed 09 02 2022]. |