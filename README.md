# Stock-Search-App 


Android app to search stocks using alpha vantage API

This is a Android app written in Java and back-end written in node.js on AWS Elastic Beanstalk. Basically, this app allow user to search for stock information using Alpha Vantage API and display the result using HighCharts, save some stock symbols as favorites, and post to Facebook timeline.

# APP screenshots

Search Page 

<img width="284" alt="screenshot1" src="https://user-images.githubusercontent.com/29028455/41131960-0b148b6a-6a73-11e8-8f95-e1ea11cfe00c.png"> <img width="281" alt="2" src="https://user-images.githubusercontent.com/29028455/41132071-9d1ee578-6a73-11e8-95d1-330a8515c129.png">



Stock Information 

<img width="318" alt="3" src="https://user-images.githubusercontent.com/29028455/41132162-53e1c4ce-6a74-11e8-9fea-8237e49d93d5.png">

Historical Charts 


<img width="311" alt="4" src="https://user-images.githubusercontent.com/29028455/41132193-8238758e-6a74-11e8-9c26-8b00fa59841f.png"> <img width="313" alt="5" src="https://user-images.githubusercontent.com/29028455/41132196-8414bcf0-6a74-11e8-8904-ed3de621e41e.png">

News

<img width="322" alt="6" src="https://user-images.githubusercontent.com/29028455/41132204-9581f17e-6a74-11e8-869d-49f845771cd7.png">

Sharing to Facebook 

<img width="299" alt="7" src="https://user-images.githubusercontent.com/29028455/41132222-b2f71e8c-6a74-11e8-9c10-4d079b0d700e.png">


# Main Function 
1. AutoComplete : The user can enter the stock name or symbol in the text view to get the stock information from our PHP script. Based on the user input, the AutoComplete would display the all the matching companies and symbols by making a HTTP request. 

2. Favorite List: 

The Favorites list interface consists of the following:

• An Automatic Refresh switch, labeled AutoRefresh

• A Refresh button

• Two “Spinners” controlling the order of the list

• The Favorite “Custom ListView” showing a list of favorite stocks
