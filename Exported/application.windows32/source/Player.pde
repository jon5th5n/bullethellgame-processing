class Player
{
  float x;
  float y;
  
  float speed = 5;
  float size = 40;
  
  
  
  
  Player(float xPos, float yPos)
  {
    x = xPos;
    y = yPos;
  }
  
  
  
  
  void display()
  {
    fill(playerColor[colorScheme]);
    stroke(playerColor[colorScheme]);
    
    ellipse(x, y, size, size);
  }
  
  
  
  
  void border()
  {
    if(x - size/2 <= 0)
    {
      x += speed;
    }
    if(x + size/2 >= width)
    {
      x -= speed;
    }
  }
  
  
  
  
  void controll()
  {
    if(keys['a'])
    {
      x -= speed;
    }
    
    if(keys['d'])
    {
      x += speed;
    }
  }
}
