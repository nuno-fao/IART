import pygame, sys
from pygame.locals import *
import numpy as np

SIDE_SIZE = 50
WHITE=(255,255,255)
BLUE=(0,225,225)
RED=(255,0,0)

#aquarium holder
class Aquarium:
    def __init__(self,id,top):
        self.id=id
        self.top=top
        self.nrlevels=0
        self.levels=[]
        self.paintedLevels=0
        self.bottom=None
    
    def addCell(self, row, col):
        if self.top - len(self.levels) < row :
            self.levels[0].append(col)
            #self.levels.append(col)  #This way higher levels are ordered top-down instead
        else:
            newlevel=[col]
            self.levels.insert(0,newlevel)
            self.nrlevels+=1
    
    def paint(self):
        if self.paintedLevels<self.nrlevels:
            self.paintedLevels+=1
            return True
        return False
    
    def unpaint(self):
        if self.paintedLevels>0:
            self.paintedLevels-=1
            return True
        return False
    
    def setBottom(self):
        self.bottom = self.top - self.nrlevels + 1

    def updateRestrictions(self,horizontals,verticals):
        for i in range(self.paintedLevels):
            horizontals[len(horizontals)-i-self.bottom] += len(self.levels[i])
            for j in self.levels[i]:
                verticals[j-1]+=1

    def paintStateString(self,state):
        for i in range(self.paintedLevels):
            for j in self.levels[i]:
                state[len(state)-(i+self.bottom)][j-1]=1
                
    def reset(self):
        while(self.unpaint()):
            pass
        
    def printAquarium(self):
        print('AQUARIUM '+ str(self.id))
        print('TOP '+ str(self.top))
        print('BOTTOM '+ str(self.bottom))
        for x in self.levels:
            print(x)

# Load the board image
def load_board(mode,rows,cols):
    image = pygame.image.load(r'../boards/'+mode+'/board1.png')
    image = pygame.transform.scale(image, (rows*SIDE_SIZE,cols*SIDE_SIZE))
    return image

# Load from the board1.txt all the information
def load_board_info(mode):
    f = open('../boards/'+mode+'/board1.txt', 'r')
    nrows = int(f.readline())
    ncols = int(f.readline())
    row_values = [int(x) for x in f.readline().split(' ')]
    col_values = [int(x) for x in f.readline().split(' ')]
    
    #remove separator
    f.readline()

    #initiate aquariums
    aquariums = []
    for y in range(nrows):
        for i, x in enumerate(f.readline().split(' ')):
            index = int(x)
            if index > len(aquariums) :
                aquariums.append(Aquarium(index,nrows-y))
            aquariums[index-1].addCell(nrows-y,i+1)

    for x in aquariums:
        x.setBottom()

    # test purposes
    # horizontal = [0]*6
    # vertical = [0]*6
    # for x in aquariums:
    #     x.updateRestrictions(horizontal,vertical)
    # print('Horizontals' + str(horizontal))
    # print('Verticals' + str(vertical))

    f.close()
    return nrows, ncols, row_values, col_values, aquariums

# Draw all the aquarium that are full of water
def draw_full_aquariums(interface):
    # TODO
    for aquarium in interface.aquariums:
        for paintedLevel in range(aquarium.paintedLevels):
            for col in aquarium.levels[paintedLevel]:
                    pygame.draw.rect(
                        interface.screen,
                        BLUE,
                        (col*SIDE_SIZE,
                        (interface.rows - ( (aquarium.bottom + paintedLevel) - 1))*SIDE_SIZE,
                        SIDE_SIZE,SIDE_SIZE))
                    #print(col,(aquarium.bottom + paintedLevel))

# Draw all the board components
def draw_board(interface):
    interface.screen.fill(WHITE)
    draw_full_aquariums(interface)
    interface.screen.blit(interface.board,(0,0))
    pygame.display.update()


class Aquarium2D:
    # init pygame library
    def __init__(self,mode):
        self.rows , self.cols, self.row_values, self.col_values, self.aquariums = load_board_info(mode)

        pygame.init()
        pygame.display.set_caption('Aquarium')
        self.screen = pygame.display.set_mode(((self.rows+1)*SIDE_SIZE,(self.cols+1)*SIDE_SIZE))
        self.screen.fill(WHITE)
        
        self.board = load_board(mode,(self.rows+1),(self.cols+1))
        
        self.currentState = [0]*len(self.aquariums)
        self.states = [self.currentState] # will contain all the possible states
        
    def getActionsNr(self):
        return len(self.aquariums)*2 #doubled because of unpaint

    def getObservationNr(self):
        aux = 1
        for x in self.aquariums:
            aux *= (len(x.levels) + 1)
        return aux
    
    # def getStateString(self):
    #     state = []
    #     for _ in range(self.rows):
    #         aux = [0]*self.cols
    #         state.append(aux)
    #     for x in self.aquariums:
    #         x.paintStateString(state)
    #     return state

    def reset(self):
        self.currentState = [0]*len(self.aquariums)
        for aquarium in self.aquariums:
            aquarium.reset()
        
    # deals with an action using the information in table Q
    def action(self,action):

        if (action%2) == 0 :
            if(self.aquariums[action//2].paint()):
                # when paint action is possible
                self.reward = -1;
                self.currentState[action//2] = self.aquariums[action//2].paintedLevels
                if(self.currentState not in self.states):
                    self.states.append(self.currentState.copy())
            else:
                # otherwise
                self.reward = -2;
        else:
            if(self.aquariums[(action-1)//2].unpaint()):
                # when unpaint action is possible
                self.reward = -1;
                self.currentState[(action-1)//2] = self.aquariums[(action-1)//2].paintedLevels
                if(self.currentState not in self.states):
                    self.states.append(self.currentState.copy())
            else:
                # otherwise
                self.reward = -2;

        
    # return reward
    def evaluate(self):
        return self.reward;
    
    # return game over or not
    def is_done(self):
        horizontal = [0]*self.rows
        vertical = [0]*self.cols
        for x in self.aquariums:
            x.updateRestrictions(horizontal,vertical)

        for i in range(self.rows):
            if(horizontal[i]!=self.row_values[i]):
                return False
            
        for i in range(self.cols):
            if(vertical[i]!=self.col_values[i]):
                return False
        # reward when the puzzle is successfully completed
        self.reward = 100;
        return True
        
    
    # returns all the information that can be observable
    def observe(self):
        return self.states.index(self.currentState)
        
        
    # render game interface
    def view(self):
        for event in pygame.event.get():
            if event.type== QUIT:
                pygame.quit()
                sys.exit()
        draw_board(self)
