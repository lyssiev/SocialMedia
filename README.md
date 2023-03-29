# SocialMedia
### ECM1410 - Object Oriented Programming Coursework
_Java Project, Computer Science BSc - Exeter University_

__Completed 28/03/2023__

## Description
(pasted from project specification)

Social media platforms are increasingly growing in popularity and they are among the most used apps and accessed websites. Despite being initially designed to promote online social interactions, they are more than connecting people, they have been used to promote disinformation campaigns, and they are a huge marketplace filled with advertisements and algorithms pushing you to consume and buy.

The UoK plans to build a very simple social media platform, free of adds and disinformation, focusing on posting short messages (text only). The users, i.e., accounts registered in the system, will be able to post original messages and comments, which can also be endorsed by endorsement posts.

Accounts have a unique numerical identifier, but also a unique string handle to be more easily identified throughout the system. They can have a description field to add personal information they want to share. Posts (original, comments, and endorsements) have a unique numerical identifier and contain a message with up to 100-characters. The post ID is a sequential number such that its ordering is a proxy for a post’s chronology. They are always associated with an author, i.e., the account who posted it. To allow the creation of meaningful conversation trees, posts must keep track of the list of endorsements and comments they received. Endorsements and comments are also categorized as posts, but with special features. For instance, comments always have to point to another post (original or comment). Endorsements automatically replicate the endorsed message and also refer to original or comment posts. Endorsements are not endorsed or commented. The system should provide basic analytics such as the most popular post and the most popular account.

The UoK wants the back-end of the new social media platform to be compatible with the front-end which is being developed by another group, as such they have already designed a Java interface for the new system, which their frontend application will use. You are to develop a class that implements this interface, and also develop the necessary additional supporting classes in the Java package called socialmedia.

## Getting Started
To get started with the project, follow these steps:

- Clone the repository
- Install the necessary dependencies
- Run the project

