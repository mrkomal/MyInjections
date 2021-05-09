# MyInjections 💉
### Description
This application is aimed for people that have to take injections or just simply want to keep record of vaccinations that they have already taken. It is written in Kotlin in 
MVVM architecture. It offers the following functionalities:
1) Adding new injection: </br>
<img src="images/add_video.gif" width="250" height="500"> </br>
2) Displaying taken injections and filtering them: </br>
<img src="images/display_video.gif" width="250" height="500"> </br>
3) Sharing new aricles and YouTube videos about vaccines to keep the user informed: </br>
<img src="images/articles_video.gif" width="250" height="500"> </br>
4) Displaying the nearest pharmacies (to buy injection) or clinics (to vaccine) based on user location: </br>
<img src="images/maps_video.gif" width="250" height="500"> </br>
5) Ticking off injection (for people that have to take injections daily): </br>
<img src="images/dose_video.gif" width="250" height="500"> </br>
6) Sending Notifications with urgent information via Firebase: </br>
<img src="images/notification_video.gif" width="250" height="500"> </br>
-----
### Tests
Additionally some basic units tests are written to test repositories, viewmodels and services. There are also instrumental tests for the most important user interface functionalities that are run with AndroidJUnitRunner. 

-----
### Used libraries/frameworks/platforms/API:
Dev:pencil2::
- Android Room;
- Material Components for Android;
- Retrofit2; 
- Picasso;
- Firebase; 
- Android Coroutines;
- Google Maps API;
- Koin. </br>

Test:open_book::
- Espresso;
- JUnit4;
- Mockito.
