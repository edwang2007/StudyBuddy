# StudyBuddy

The problem is that students often want to work with other students in a class on homework or study for exams,
but may feel socially awkward if they do not know others on a personal basis. StuddyBuddy is an android application
that solves this problem by allowing students to create/join classes and create events that other students in the class can join.

Concepts used:
1. Users login, classes, and events are stored on **firebase**. Note events only exist under their corresponding class and users can only see the classes that they added.

2. Each class and event is a **cardview** in order to make them pop up the screen and look more aesthetically pleasing.

3. The list of classes and events are generated using **recycler view** in order to optimize performance for the future, in case dataset increase

4. When users say going to an event, they are allowed to add that event to **their Calendar**

Note: that the **Butterknife library** is used to make code look cleaner and avoid writing repetitive code.
