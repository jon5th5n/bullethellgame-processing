class Button
{
  String mode;
  boolean touching;
  boolean clicked;
  
  
  Button(float x, float y, float w, float h, color clr, color hClr, String bMode)
  {
    mode = bMode;
    
    if(mode == "CIRCLE")
    {
      fill(clr);
      stroke(clr);
      
      ellipse(x, y, w, h);
      
      if(dist(mouseX, mouseY, x, y) <= w/2)
      {
        fill(hClr);
        stroke(hClr);
        
        ellipse(x, y, w, h);
        
        touching = true;
        
        if(mousePressed)
        {
          clicked = true;
        }
      }
    }
    
    if(mode == "RECT")
    {
      fill(clr);
      stroke(clr);
      
      rectMode(CENTER);
      rect(x, y, w, h);
      
      if(mouseX >= x-w/2 && mouseX <= x+w/2 && mouseY >= y-h/2 && mouseY <= y+h/2)
      {
        fill(hClr);
        stroke(hClr);
        
        rect(x-w*.01/2, y-h*.01/2, w*1.01, h*1.01);
        
        touching = true;
        
        if(mousePressed)
        {
          clicked = true;
        }
      }
    }
  }
  
  Button(float x, float y, float size, color clr, color hClr, String text, PFont bFont, String bMode)
  {
    mode = bMode;
    
    if(mode == "TEXT")
    {
      textAlign(CENTER);
      textFont(bFont);
      textSize(size);
      fill(clr);
      
      text(text, x, y);
      
      if(mouseX >= x-size && mouseX <= x+size && mouseY >= y-size/2 && mouseY <= y)
      {
        textAlign(CENTER);
        textFont(bFont);
        textSize(size*1.04);
        fill(hClr);
        
        text(text, x, y);
        
        touching = true;
        
        if(mousePressed)
        {
          clicked = true;
        }
      }
    }
  }
}
