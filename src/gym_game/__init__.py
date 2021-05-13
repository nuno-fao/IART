from gym.envs.registration import register
from gym import error

try:
    register(
    id = 'Aquarium-v0',
    entry_point= 'gym_game.envs:AquariumEnv'    
    )
except error.Error:
    pass