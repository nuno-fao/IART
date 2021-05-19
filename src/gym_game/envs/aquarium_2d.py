import pygame, sys
from pygame.locals import *

SIDE_SIZE = 50
WHITE=(255,255,255)
BLUE=(0,225,225)
RED=(255,0,0)
POINTER_INIT_POSITION = (75,75)

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
    return nrows, ncols, row_values, col_values

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
    #draw_pointer(interface)
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
