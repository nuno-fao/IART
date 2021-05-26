# IART PROJ 2
Software specifications used to test the app:
	- Python 3.8.5
		- Libraries:
			- Pygame
			- OpenAI Gym
			- Numpy
			- Time
			- Random
			- Sys

Instructions to execute the code:
	
	python3 [Board] 1 -> to use Q-Learning algorithm
	
	or
	
	python3 [Board] 2 -> to use Sarsa algorithm

	[Board] must be one of the following boards:
		- 3x3
		- 4x4
		- 5x5
		- 6x6_Easy
		- 6x6_Normal
		- 6x6_Hard
		- 10x10_Easy
	
	Ex: python3 3x3 1 -> this command will use the board 3x3 and the Q-Learning alg.

Note: We recommend to use only the first 5 boards because 6x6_Hard and 10x10_Easy take a long time to finish.
