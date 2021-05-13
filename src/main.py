import gym
import gym_game


if __name__ == "__main__":
      env = gym.make('Aquarium-v0',mode='6x6_Easy')
      while True:
          env.render()