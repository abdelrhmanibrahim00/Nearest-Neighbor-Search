Signal Strength Tracker App

This project is an Android application developed as part of a lab exercise, focusing on tracking and visualizing signal strengths in various locations. The app utilizes local storage with Room database and communicates with a REST API for remote data caching and synchronization. It incorporates Material Design principles, ensuring a user-friendly interface and smooth performance.
Features
User Interface

    User Identification: Allows users to view and edit their unique MAC address.
    Signal Strength List: Displays a list of signal strengths detected at various points.
    Add/Edit Signal Strengths: Provides screens to add new signal strength readings or edit existing ones.
    Location Area View (Map): Visualizes the user’s route on a map, showing signal strength distribution over the area.

Navigation & Interaction

    Material Design Compliance: Intuitive and visually appealing navigation using Material Design principles.
    RecyclerViews: Efficient list handling with RecyclerViews, supporting smooth scrolling and interactions.
    Action Bar: Includes an app bar for easy navigation and access to key functions.
    Floating Action Buttons (FABs): Quick access to actions like adding or editing signal strengths.

Implementation Details
Local Data

The app employs a Room database for local data persistence, which stores:

    User-specific data (e.g., MAC address and signal strengths).
    Cached signal strength map data from the remote service.

Architectural Components

    LiveData/Kotlin Flow: Ensures asynchronous Room database queries, minimizing UI lag and keeping the app responsive.
    ViewModel: Manages UI data across configuration changes, preserving UI state and sharing data across fragments when needed.

Remote Data

    The app integrates a REST API to retrieve the signal strength map stored in a remote database.
    A background task loads the signal strength map upon the app’s first launch, caching it locally. This setup ensures the app functions even when remote data is unavailable temporarily.

Data Persistence & UI State

    UI state is preserved, even when activities are killed by the system (tested with "Don’t keep activities" in Developer options).

Recommended Libraries

The following libraries were used for implementation:

    http4k: For handling HTTP and JSON serialization.
    Retrofit: Facilitates API calls from the app, ensuring efficient and secure remote data access.
