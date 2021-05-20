import gym
import gym_game
import random
import numpy as np


if __name__ == "__main__":
    env = gym.make('Aquarium-v0',mode='6x6_Easy')
    
    # Create Q-table
    action_size = env.action_space.n
    state_size = env.observation_space.n
    qtable = np.zeros((state_size, action_size))
    
    # Set hyperparameters for Q-learning

    # @hyperparameters
    total_episodes = 200          # Total episodes
    learning_rate = 0.8           # Learning rate
    max_steps = 99                # Max steps per episode
    gamma = 0.95                  # Discounting rate
    
    # Exploration parameters
    epsilon = 1.0                 # Exploration rate
    max_epsilon = 1.0             # Exploration probability at start
    min_epsilon = 0.01            # Minimum exploration probability 
    decay_rate = 0.001            # Exponential decay rate for exploration prob
            
    # Learn through Q-learning

    # List of rewards
    rewards = []
    
    for episode in range(total_episodes):
        # Reset the environment
        state = env.reset()
        step = 0
        done = False
        total_rewards = 0
        
        for step in range(max_steps):
            # Visualize learning outcome
            env.render();
            
            # Choose an action a in the current world state (s)
            # First we randomize a number
            exp_exp_tradeoff = random.uniform(0, 1)
                        
            # If this number > greater than epsilon --> exploitation 
            #(taking the biggest Q value for this state)
            if exp_exp_tradeoff > epsilon:
                action = np.argmax(qtable[state,:])
            else:  # Else doing a random choice --> exploration
                action = env.action_space.sample()
                
            # Take the action (a) and observe the outcome state(s') and reward (r)
            new_state, reward, done, info = env.step(action)
                
            # Update Q(s,a):= Q(s,a) + lr [R(s,a) + gamma * max Q(s',a') - Q(s,a)]
            # qtable[new_state,:] : all the actions we can take from new state
            qtable[state, action] = qtable[state, action] + learning_rate * (reward + gamma * np.max(qtable[new_state, :]) - qtable[state, action])
                
            # Update total_rewards value
            total_rewards += reward
                        
            # Our new state is state
            state = new_state
                        
            # If done : finish episode
            if done == True: 
                break
            
        # Visualize learning outcome
        env.render();  
        
        episode += 1
        # Reduce epsilon (because we need less and less exploration, i.e random choices)
        epsilon = min_epsilon + (max_epsilon - min_epsilon)*np.exp(-decay_rate*episode) 
        rewards.append(total_rewards)
    
    print ("Score/time: " +  str(sum(rewards)/total_episodes))
    print(qtable)
    print(epsilon)