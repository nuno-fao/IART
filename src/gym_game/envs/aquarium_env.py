import gym
import numpy as np
from gym import spaces
from gym_game.envs.aquarium_2d import Aquarium2D

class AquariumEnv(gym.Env):
    
    def __init__(self,mode):
        self.game = Aquarium2D(mode)
        self.action_space = spaces.Discrete(self.game.getActionsNr())
        self.observation_space = spaces.Discrete(self.game.getObservationNr())
        #self.qtable = np.zeros((self.observation_space, self.action_space))
        
        
    def reset(self):
        del self.game
        self.game = Aquarium2D()
        obs = self.game.observe()
        return obs
        
    def step(self, action):
        self.game.action(action)
        obs = self.game.observe()
        reward = self.game.reward()
        done = self.game.is_done()
        return obs , reward, done, {}
        
    def render(self , mode='human'):
        self.game.view()        
        

    def close(self):
        """Performs any necessary cleanup.
        Environments will automatically close() themselves when
        garbage collected or when the program exits.
        """
        pass