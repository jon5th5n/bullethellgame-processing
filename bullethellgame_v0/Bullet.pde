class Bullet
{
  float x;
  float y;
  
  float z;
  
  float speed;
  float size;
  
  
  
  
  Bullet(float bZ)
  {
    z = bZ;
    
    speed = map(z, 0, 20, 5, 9);
    size =map(z, 0, 20, 35, 10);;
    
    x = random(1080);
    y = random(-730, -10);
  }
  
  
  
  
  void display()
  {
    fill(bulletColor[colorScheme]);
    stroke(bulletColor[colorScheme]);
    
    ellipse(x, y, size, size);
    
    y = y + speed;
  }




  void respawn()
  {
    if(y >= 720 + size/2)
      {
        score += 1;
        
        y = random(-730, -10);
        x = random(1080);
      }
  }
}
