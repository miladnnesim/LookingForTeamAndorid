# Valorant LFT (Looking For Team) - Android App

Een moderne Android applicatie gebouwd met **Kotlin** en **Jetpack Compose** waarmee Valorant-spelers snel en efficiënt geschikte teamgenoten kunnen vinden op basis van rank, rol en regio.

## 🚀 Features

* **Player Profiles**: Maak een profiel aan met username (inclusief #ID validatie), rank, main role en regio.
* **Dynamic LFT Feed**: Een live overzicht van spelers die op zoek zijn naar een team.
* **Custom LFT Posts**: De mogelijkheid om een eigen bericht te typen en je profiel naar de feed te sturen.
* **Post Management**: Verwijder je eigen posts uit de feed wanneer je een team hebt gevonden.
* **Modern UI**: Gebouwd met Material 3 en een intuïtieve navigatiebalk.

---

## 🛠 Tech Stack

* **Taal**: Kotlin
* **UI Framework**: Jetpack Compose
* **Architectuur**: MVVM (Model-View-ViewModel)
* **State Management**: StateFlow & ViewModels
* **Navigatie**: Navigation Suite Scaffold

---

## 📂 Project Structuur

De code is georganiseerd volgens de officiële Android richtlijnen:

```text
app/java/com.ehb.lookingforteam/
│
├── model/
│   ├── PlayerProfile.kt   # Data model voor de gebruiker
│   └── LftPost.kt         # Data model voor feed-berichten
│
├── viewmodel/
│   ├── ProfileViewModel.kt # Logica voor gebruikersdata
│   └── FeedViewModel.kt    # Logica voor de post-lijst
│
└── ui/theme/
    ├── MainActivity.kt    # Hoofdingang en navigatie-logica
    ├── ProfileScreen.kt   # UI voor profielweergave en bewerken
    ├── FeedScreen.kt      # UI voor de live feed met LFT kaarten
    └── ...                # Thema en styling bestanden
```

## Installatie

1.  Open **Android Studio**.
    
2.  Importeer dit project via File > New > Import Project.
    
3.  Kotlinimplementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    
4.  Sync de Gradle bestanden.
    
5.  Run de app op een emulator of fysiek toestel.
    

## Toekomstvisie (Roadmap)


*   **Database Integratie**: Implementatie van Firebase voor persistente dataopslag.
    
*   **Real-time Chat**: Directe berichten sturen naar spelers in de feed.
    
*   **Riot API**: Automatische rank-verificatie via de officiële Riot Games API.
    
*   **DarkMode Support**: Volledige styling voor nachtgebruik.

## Referenties
Github Copilot

_Ontwikkeld als onderdeel van een Android Development leertraject._
