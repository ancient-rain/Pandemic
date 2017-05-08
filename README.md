# Pandemic 2: Electric Boogaloo

This is an attempt at redesiging a 376 project, [Pandemic](https://ada.csse.rose-hulman.edu/bednartd/BirdUpProject)

## Problem with the old project and why this exists

The old project had the gui developed in parallel with the backend. As a result the gui never used the backend and just had a ton of logic mixed in with gui redraws. This lead to a "God class" where one class pretty much did everything and became quite possibly the most brittle class I have ever seen.

## Current List of Features
* [ ] Cards 
    * [x] Card Model Class
    * [ ] Card View Class
    * [x] Card Controller Class
        * [x] Abstract Deck
        * [x] Infection Deck
        * [x] Player Deck
* [ ] Diseases
    * [x] Disease Model Class
    * [ ] Disease View Class
    * [x] Disease Controller Class
* [ ] Cities
    * [x] City Model Class
    * [ ] City View Class
    * [x] City Controller Class
* [ ] Characters
    * [x] Character Model Class
    * [ ] Character View Class
    * [ ] Character Controller Class 
        * [x] Abstract Character Controller Class
        * [ ] Contingency Planner Character Controller Class
        * [ ] Dispatcher Character Controller Class 
        * [x] Medic Character Controller Class
        * [x] Operation Expert Character Controller Class
        * [x] Quarentine Specialist Character Controller Class
        * [x] Researcher Character Controller Class
        * [x] Scientist Character Controller Class
* [ ] Game
    * [x] Game Model Class
    * [ ] Game View Class
    * [x] Game Controller Class
* [ ] Other
    * [ ] Tests
    * [ ] Locale Settings
    * [ ] Non-Code Resources
    * [ ] Startup Menu
    * [ ] Character Selection