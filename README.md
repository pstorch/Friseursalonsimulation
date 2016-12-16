# Simulation of a Barbershop

This piece of code was created during my studies of CS in 1997. Together with four colleagues, we've created a Simulation of a Barbershop for the mathematics course.
Recently I was looking around on my hard drive to search for the oldest code I've ever written and found this one.
I've written code before. Starting with BASIC on a C64 in the 80s and later Turbo Pascal at High School. But that seems to be lost :cry:.

This code is not solely written by me, but I don't mention my former colleagues to protect the innocent :innocent:. So blame me for the harm it might do to your eyes.
At the time of writing Java was still in its infancy. I think it was Java 1.1. We had no IDE. Only a text editor and the command line. And Java Applets were the new Kids on the block.

Looking at it after around 20 years was quite a shock :scream:. As you might have experienced when looking at your own code from a few months, that it looks different to the code you write today. But this is nearly 2 decades ago.

I had to fix a few things to actually make it compile. There were `import` statements of the classes in this one directory (packages are not used at all). There were superfluous `;` characters after some `if` and `for` statements which resulted in `unreachable code` errors. Looks like the Java 1.1 compiler back then was a little bit more relaxed than todays. I also changed the line endings to Unix and the encoding to UTF-8. There are some german umlauts in the code :wink:.

## Watch it running:

[![Example Run of the Barbershop](http://img.youtube.com/vi/7PnGgxZh6GA/0.jpg)](http://www.youtube.com/watch?v=7PnGgxZh6GA "Friseursalonsimulation")

## Build

`javac *.java`

## Run

`appletviewer file:Friseursalon.html`
