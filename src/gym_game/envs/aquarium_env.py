import gym
import numpy as np
from gym import spaces
from gym_game.envs.aquarium_2d import Aquarium2D

class AquariumEnv(gym.Env):
    
    # AquariumEnv constructor
    def __init__(self,mode):
        self.game = Aquarium2D(mode)
        self.action_space = spaces.Discrete(self.game.getActionsNr())
        self.observation_space = spaces.Discrete(self.game.getObservationNr())
        self.initiated = False
    
    # resets the game interface and the game state
    def reset(self):
        self.game.reset()
        obs = self.game.observe()
        return obs
    
    # of the given action returns the reward, game state and a flag that indicates if the puzzle has reached the end
    def step(self, action):
        self.game.action(action)
        obs = self.game.observe()
        done = self.game.is_done()
        reward = self.game.evaluate()
        return obs , reward, done, {}
    
    # updates pygame window
    def render(self , mode='human'):
        if self.initiated:
        	self.game.view()   
    
    # initiates pygame interface
    def init_view(self):
    	self.initiated = True
    	self.game.init_view()     
        
