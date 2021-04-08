class Screen
{
  Screen()
  {
    
  }
  
  
  
  
  void homescreen()
  {
    background(backColor[colorScheme]);
    
    grid();
       
    scoreDisplay();
    
    bt[0] = new Button(1080/2, 720/2, 125, playerColor[colorScheme], lplayerColor[colorScheme], "PLAY", f8Bit, "TEXT");
    if(bt[0].clicked)
    {
      state = "Ingame";
    }
    
    bt[1] = new Button(200, 720/2-40, 50, playerColor[colorScheme], lplayerColor[colorScheme], "color: "+(colorScheme+1), f8Bit, "TEXT");
    bt[4] = new Button(200, 720/2+10, 50, playerColor[colorScheme], lplayerColor[colorScheme], "difficulty: "+bCount, f8Bit, "TEXT");
  }
  
  
  
  
  void failscreen()
  {
    background(backColor[colorScheme]);
    
    grid();
    
    
    if(pulse)
    {
      index += .2;
      if(index>=10)
      {
        pulse = false;
      }
    }
    else
    {
      index -= .2;
      if(index<=0)
      {
        pulse = true;
      }
    }
    
    fill(playerColor[colorScheme]);
    textAlign(CENTER);
    textFont(f8Bit);
    
    pushMatrix();
    
    translate(1080/2+150, 720/2-105);
    rotate(radians(20-index*.4));
    textSize(50+index*.2);
    text("score: "+int(lastScore), 0, 0); 
    
    popMatrix(); 
    
    
    bt[2] = new Button(1080/2, 720/2, 125, playerColor[colorScheme], lplayerColor[colorScheme], "RETRY", f8Bit, "TEXT");
    if(bt[2].clicked)
    {
      state = "Ingame";
    }
    
    bt[3] = new Button(1080/2, 720/2+105, 125, playerColor[colorScheme], lplayerColor[colorScheme], "BACK", f8Bit, "TEXT");
    if(bt[3].clicked)
    {
      state = "Homescreen";
    }
  }
  
  
  
  
  void grid()
  {
    stroke(bulletColor[colorScheme]);
    strokeWeight(.5);
    
    for(int i=-10; i<=width; i+=100)
    {
      line(i, 0, i, height);
      line(0, i, width, i);
    }
  }
  
  
  
  
  void scoreDisplay()
  { 
    if(pulse)
    {
      index += .2;
      if(index>=10)
      {
        pulse = false;
      }
    }
    else
    {
      index -= .2;
      if(index<=0)
      {
        pulse = true;
      }
    }
    
    fill(playerColor[colorScheme]);
    textFont(f8Bit);
    textAlign(CENTER);
    
    pushMatrix();
    
    translate(130, 50);
    rotate(radians(0-index*.3));
    textSize(40+index*.3);
    text("points: "+int(points), 0, 0);
    
    popMatrix();
    
    
    pushMatrix();
    
    translate(width/2, 50);
    rotate(radians(-2+index*.5));
    textSize(45+index*.8);
    text("highscore: "+int(highscore), 0, 0);
    
    popMatrix();
    
    
    pushMatrix();
    
    translate(930, 50);
    rotate(radians(3-index*.4));
    textSize(35+index*.2);
    text("last score: "+int(lastScore), 0, 0); 
    
    popMatrix(); 
  }
}
