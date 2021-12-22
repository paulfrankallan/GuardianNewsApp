# Guardian News App

<img src="guardian1.png" width="48%" /> <img src="guardian2.png" width="48%" />

## Main points covered:
   * Production quality MVP example (Not a large enough app so not really using intents/actions)
   * Well structured architecture with clean testable code
   * Demonstrate testing some business logic with unit tests
   
## Issues & Improvements:
   * Add paging, so not limited to 50 items   
   * Use proper ItemDecoration approach for recycler view padding dims
   * Clean-up favourites as they will gather stale ids
   * Ability to read and favourite articles offline
   * Better unit test coverage
   * UI Tests
   * Swap out Rx for Coroutines from retrofit through to LiveData 
   * On a larger app a more elaborate MVI implementation covering actions and have dedicated behaviour/use case classes for business logic
   * Repository pattern with multiple data sources if implementing offline functionality for example.
   * On API 21 padding on just one of the Section titles behaves a bit weird with extra end padding. This issue doesn’t occur on later API versions
   * Screen jank when images load etc - add placeholder images/shimmer etc **
   * No content screens for; no articles, no internet, api error etc.
   * Fonts, dimensions, colours etc are best approximations so would work with these to move them into proper places; themes, fonts, dimensions files etc.
   * Handle buttons/icons over images that can change colour to make them always visible, i.e. contrast enough not to blend.
   * Removed jcenter repo and replaced with MavenCentral as jcenter is being closed down.


### Guardian API Key

Note that you might need to replace the guardian API key for this project if [this link](https://content.guardianapis.com/search?api-key=09658731-cb6d-4a84-9e3c-5f030389de4e) doesn't work. To get a new key, go to https://bonobo.capi.gutools.co.uk/register/developer and register yourself as a developer. Once you get your key, replace the string [here](https://github.com/paulfrankallan/GuardianNewsApp/blob/main/app/src/main/res/values/api_constants.xml) with your key and you should be good to go!
