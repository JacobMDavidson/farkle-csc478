# Farkle

For the software engineering capstone course at University of Illinois at Springfield, my team developed a version of the popular dice game Farkle using Java. This project went well beyond simply coding an application. It was an exercise in overall software engineering. In addition to coding, this project included overall project planning, team management, requirements documentation, design documentation, testing documentation, and user documentation.

## Traditional Rules of Farkle
Farkle is a game typically played with six dice and more than one player. The traditional rules dictate that each player takes turns rolling the dice in succession, with each turn producing a score. The score produced from the players current turn is added to that players previous score accumulation. The goal is to be the first player to reach 10,000 points. The scoring is generated as follows:

1. At the beginning of the turn, the player rolls all 6 dice.
2. Scoring for each roll is as follows: Each 1 = 100 points, each 5 = 50 points, (3) 1s = 1000 points, (3) 2s = 200 points, (3) 3s = 300 points, (3) 4s = 400 points, (3) 5s = 500 points, (3) 6s = 600 points, a straight = 1500 points, (3) pair = 750 points, and more than three of a kind doubles the value for each additional match (e.g. (5) 3s = 300 x 2 x 2 = 1200 points).
3. Dice resulting in a score are chosen and removed by the player (the player must pick up at least one scoring die, but does not need to pick up all scoring die), and the player decides if they want to roll with the remaining dice or pass to the next player.
4. At least one die must be set aside after each roll.
5. A “farkle” occurs when a roll results in no points. All points accrued during that turn are forfeit, and play is passed to the next player.
6. If the player has scored all six dice, he or she can roll again with all six dice.
7. Once a player has surpassed the winning point total, each successive player has one last chance to score enough points to surpass the leader.

#### Rules for this Single Player Game Version
The single player version follows the same scoring rules as dictated by the traditional rules of Farkle. In the single player version, the player is limited to a total of ten turns while trying to earn as many points as possible. The goal in this version is to try and beat your old high score.

#### Rules for the Two Player Game Version
The two player version follows all of the same rules as dictated by the traditional rules of Farkle. When a player finishes a turn and has a total game score greater than 10,000 points, the other player has one last chance to try and score enough points to surpass the leader. The player with the most points after that final turn, wins the game.

## Installation

For windows systems, this application can be installed via the Farkle disk image. Download and extract *Farkle Disk Image.zip* and run *Install Farkle.msi*. Simply follow the prompts to install the application.

For those that like to tinker, feel free to fork the repository. The project was created in eclipse, and the .exe file was built with Maven.

## Farkle Gameplay

Gameplay is fairly straight forward. Should you run into difficulty, download the user's documentation file. That file contains a user's manual with detailed instructions on how to play the game.
