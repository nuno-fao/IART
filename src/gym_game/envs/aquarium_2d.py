import pygame, sys
from pygame.locals import *

SIDE_SIZE = 50
WHITE=(255,255,255)
BLUE=(0,225,225)
RED=(255,0,0)
POINTER_INIT_POSITION = (75,75)

# Load the board image
def load_board(mode,rows,cols):
    image = pygame.image.load(r'../boards/'+mode+'/board1.png')
    image = pygame.transform.scale(image, (rows*SIDE_SIZE,cols*SIDE_SIZE))
    return image

# Load from the board1.txt all the information
def load_board_info(mode):
    f = open('../boards/'+mode+'/board1.txt', 'r')
    rows = int(f.readline())
    cols = int(f.readline())
    row_values = [int(x) for x in f.readline().split(' ')]
    col_values = [int(x) for x in f.readline().split(' ')]
    f.close()
    return rows, cols, row_values, col_values

# Draw all the aquarium that are full of water
def draw_full_aquariums(interface):
    # TODO
    pygame.draw.rect(interface.screen,BLUE,(SIDE_SIZE,SIDE_SIZE,SIDE_SIZE,SIDE_SIZE))

def draw_pointer(interface):
    pygame.draw.circle(interface.screen, RED, POINTER_INIT_POSITION, 10)

# Draw all the board components
def draw_board(interface):
    pygame.display.init()
    draw_full_aquariums(interface)
    interface.screen.blit(interface.board,(0,0))
    draw_pointer(interface)
    pygame.display.update()


class Aquarium2D:
    # init pygame library
    def __init__(self,mode):
        self.rows , self.cols, self.row_values, self.col_values = load_board_info(mode)
        
        pygame.init()
        pygame.display.set_caption('Aquarium')
        self.screen = pygame.display.set_mode(((self.rows+1)*SIDE_SIZE,(self.cols+1)*SIDE_SIZE))
        self.screen.fill(WHITE)
        
        self.board = load_board(mode,(self.rows+1),(self.cols+1))
        
       
        
    # deals with an action using the information in table Q
    #def action(self,action):
        
    # return reward
    #def evaluate(self):
    
    # return game over or not
    #def is_done(self):
    
    # returns all the information that can be observable
    #def observe(self):
        
    # render game interface
    def view(self):
        for event in pygame.event.get():
            if event.type== QUIT:
                pygame.quit()
                sys.exit()
        draw_board(self)