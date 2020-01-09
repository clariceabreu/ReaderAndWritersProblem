# Reader And Writers Problem
This is Java application developed by Clarice Abreu.
The project was developed for the Operation Systems course from University of São Paulo.

## Specification and App Report
The discription of the project is available at: 
Report of the project is available at: https://docs.google.com/document/d/18XSamfLG6u7XeN9CTWI0hQ-W9QifN4bFXoIaQJ543DI/edit?usp=sharing

## Objective
The main objective of the app is to read and write in a shared file which is treated as a critial area.
The app tests three different approches:

1. Blocking the entire critical area every time a writer or reader object enters it.
2. Blocking the critical area only when writer's objects enter it and giving preference to readers over writers
3. Blocking the critical area only when writer's objects enter it and giving preference to writers over readers

The access to the file is made 100 times by 100 Threads. The ratio of reader-writer Threads ranges from 0 writers and 100 readers to 100 readers and 0 writers, and the order between writers and readers is randomized.
Therefore the application runs as the following:
* 1st Iteration: 0 writers Threads and 100 readers Threads access the critical area in the three different approachs
* 2nd Iteration: 1 writer Thread and 99 readers Threads access the critical area in the three different approachs
* 3rd Iteration: 2 writers Threads and 98 readers Threads access the critical area in the three different approachs
* ...
* 99th Iteration: 99 writers Threads and 1 reader Thread access the critical area in the three different approachs
* 100th Iteration: 100 writers Threads and 0 readers Threads access the critical area in the three different approachs

## Results
<img src="https://scontent.fbhz8-1.fna.fbcdn.net/v/t1.15752-9/82157397_1101685596865344_341226544552738816_n.png?_nc_cat=101&_nc_ohc=FY6YixnCHxkAQkHGTAxbyEkU6MHVq5wq9YVZB0SofqKH8POunVUg4gRlQ&_nc_ht=scontent.fbhz8-1.fna&oh=4c70c4913f21e912a292b25def2ad1d0&oe=5E9E4BE6" alt="graph of the results" width="300">
* The blue line represents the critical area being blocked every time it is entered fro any object (Approach 1)
* The blue red line represents the critical area being blocked only by writers and readers have the preference to eneter it (Approach 2)
* The yelllow line represents the critical area being blocked only by writers and they have the preference to enter it (Approach 3)
