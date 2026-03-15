# Music Rating & Sharing App - Project Specifications
Project Code: SYT (Share Your Taste)

## Overview
An app that manages users taste and rating for albums and songs, allowing users to search for others taste, look at comments and comment on albums and songs. The app does not include administrative interface for CRUD operations for songs and albums, all this being done through the database.

## Scope
This is a university project focused on Software Design

## Features
For a visual representation of how features interact and their use case, see the [Use Case Diagram](use_case.md)

### F1 - Database
+ **Albums & Songs:** all albums & songs are created and updated via database or admin CRUD operations in the app

### F2 - Authentication
+ **Login:** Users authenticate with their credentials to access the system
+ **Logout:** Users can securely log out of their session
+ **Guest:** Users can accest all features in a readolny mode without autentification

### F3 - Rate Songs & Albums
+ **Rate Songs:** users can rate songs from a 0 to 5 stars scale
+ **Rate Albums:** users can rate albums (this doesn't mean that they rate all the songs) from a 0 to 5 stars scale

### F4 - Comment on Songs & Albums
+ **Comment on Songs:** users can comment on songs like a reddit-like thread
+ **Comment on Albums:** users can comment on songs like a reddit-like thread
+ **Comment on Profile:** users cam comment on other users profiles 

### F5 - View Charts
+ **Album Top:** users can see the top rated albums of all time but also by year

### F6 - View Profile
+ **Your profile:** users can see their profiles, with favorite 6 albums (based on rating or songs avg. on that album) and most recent comments.
+ **Edit Profile:**  users can edit their profiles
+ **Others profiles:** users can see others profiles and leave comments with SOME MODERATION

### F7 - Notifications
+ Users will receive notifications for answers to their comments