# de0121
Tool Rental application

This can be run as a command line application. The tools that can be selected are specified inside of the tools.csv file. The specifications of different tool types can be found at
the bottom of this README.

Select a tool to rent along with a rental date, duration, and the checkout date. This application will produce a Rental Agreement
describing the tool rental.


## Tool Type Specifications

|             | Daily Charge  |   Weekday Charge | Weekend Charge  | Holiday Charge  |
| ----------  | --------------| ---------------- | --------------- | --------------- |
| Ladder      |     1.99      |        Yes       |        Yes      |         No      |
| Chainsaw    |     1.49      |        Yes       |        No       |         Yes     |
| Jackhammer  |     2.99      |        Yes       |        No       |         No      |