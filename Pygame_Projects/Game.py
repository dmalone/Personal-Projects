import pygame

n = 1

screen = pygame.display.set_mode((640,480))
background = pygame.Surface((320,240))
background.fill((255,255,255))
background = background.convert()
screen.blit(background,(160,120))

while n == 1:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            n = 0
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_ESCAPE:
                n = 0
    pygame.display.flip()

pygame.quit()
