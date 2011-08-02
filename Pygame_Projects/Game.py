import pygame

n = 1

class Player (pygame.sprite.Sprite):
    
    def __init__(self,position):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface((64,64))
        self.image.fill((255,0,0))
        self.image = self.image.convert()
        self.rect = self.image.get_rect()
        self.rect.x = position[0]
        self.rect.y = position[1]

    def update(self, distance):
        self.old = self.rect
        self.rect = self.rect.move(distance)
        
        
pygame.init()
clock = pygame.time.Clock()


screen = pygame.display.set_mode((640,480))
background = pygame.Surface((320,240))
background.fill((255,255,255))
background = background.convert()
screen.blit(background,(160,120))

character = Player((320,416))
screen.blit(character.image,(character.rect.x, character.rect.y))

blank = pygame.Surface((character.rect.x,character.rect.y))
blank.fill((0,0,0))


while n == 1:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            n = 0
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_ESCAPE:
                n = 0
            #elif event.key == pygame.K_RIGHT:
    key = pygame.key.get_pressed()[pygame.K_RIGHT]
    if key:
        pygame.key.set_repeat(10,10)
        character.update([1,0])
        screen.blit(blank, (character.old.x, character.old.y))
        screen.blit(character.image,(character.rect.x,character.rect.y))
    key = pygame.key.get_pressed()[pygame.K_LEFT]
    if key:
        pygame.key.set_repeat(10,10)
        character.update([-1,0])
        screen.blit(blank, (character.old.x, character.old.y))
        screen.blit(character.image,(character.rect.x,character.rect.y))
    key = pygame.key.get_pressed()[pygame.K_UP]
    if key:
        pygame.key.set_repeat(10,10)
        character.update([0,-1])
        screen.blit(blank, (character.old.x, character.old.y))
        screen.blit(character.image,(character.rect.x,character.rect.y))
    key = pygame.key.get_pressed()[pygame.K_DOWN]
    if key:
        pygame.key.set_repeat(10,10)
        character.update([0,1])
        screen.blit(blank, (character.old.x, character.old.y))
        screen.blit(character.image,(character.rect.x,character.rect.y))
 
    pygame.display.flip()
    clock.tick(100)

pygame.quit()
