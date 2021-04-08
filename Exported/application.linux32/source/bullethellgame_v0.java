import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bullethellgame_v0 extends PApplet {

Player p = new Player(1080/2, 650);

int bCount = 60;
Bullet[] bullets = new Bullet[200];

Button[] bt = new Button[5];

Screen s = new Screen();

int colorScheme = 1; 

int[] backColor = {0xff28334A, 0xff2A2B2D, 0xffFFDDE2, 0xff695356};        // darkBlue, black, pink, darkBrown
int[] bulletColor = {0xffFBDE44, 0xffD9514E, 0xffFAA094, 0xffECB865};      // yellow, red, darkPink, orange
int[] playerColor = {0xffF65058, 0xff2DA8D8, 0xff008C76, 0xffFBC9BE};      // red, lightBlue, darkGreen, lightPink
int[] lplayerColor = {0xffEA858A, 0xff83CCE8, 0xff9ED9CC, 0xffDB7B65};     // lightRed, lightBlue2, lightGreen, pink 

boolean[] keys = new boolean[128];

Table scores;
TableRow row;

PFont f8Bit;

float score;
float highscore;
float lastScore;
float points;
String date = day()+"/"+month()+"/"+year()+" - "+hour()+":"+minute()+":"+second();
String state = "Homescreen";

boolean mPressed;
boolean pulse = true;
float index = 0;




public void declareBullets()
{
  for(int i=0; i<bCount; i++)
  {
    bullets[i] = new Bullet(random(0, 20));
  }
}



public void bullets()
{
  for(int i=0; i<bCount; i++)
  {
    bullets[i].display();
    bullets[i].respawn();
  }
}




public void collision()
{
  float d;
  
  for(int i=0; i<=bCount-1; i++)
  {
    d = dist(p.x, p.y, bullets[i].x, bullets[i].y);
    
    if(d < p.size/2 + bullets[i].size/2)
    {
      saveScore();
      
      state = "Failed";
    }
  }
}




public void displayScore()
{
  textFont(f8Bit);
  textSize(35);
  textAlign(CORNER);
  
  text("score: " + PApplet.parseInt(score), 10, 22);
}




public void loadScore()
{
  scores = loadTable("data/scores.csv", "header");
  
  row = scores.getRow(0);
  highscore = row.getFloat("score");
  
  row = scores.getRow(1);
  points = row.getFloat("points");
  
  row = scores.getRow(1);
  lastScore = row.getFloat("score");
}




public void saveScore()
{
  points += score;
  
  row = scores.getRow(0);
    
  if(score > row.getFloat("score"))
  {
    row.setFloat("score", score);
    row.setString("date", date);
  }
  
  row = scores.getRow(1);
  row.setFloat("score", score);
  row.setString("date", date);
  row.setFloat("points", points);
  
  saveTable(scores, "data/scores.csv");
}




public void play()
{
  background(backColor[colorScheme]);
  
  p.display();
  p.border();
  p.controll();
  
  bullets();
  
  collision();
  
  displayScore();
}




public void setup()
{
  
  
  declareBullets();
  loadScore();
  
  f8Bit = createFont("8BitMadness.ttf", 300);
}




public void draw()
{
  if(state == "Homescreen")
  {
    score = 0;
    declareBullets();
    
    loadScore();
    s.homescreen();
  }
  
  if(state == "Ingame")
  {
    play();
  }
  
  if(state == "Failed")
  {
    score = 0;
    declareBullets();
    
    loadScore();
    s.failscreen();
  }
}




public void keyPressed()
{
  keys[key] = true;
}




public void keyReleased()
{
  keys[key] = false;
}




public void mousePressed()
{
  if(bt[1].touching)
  {
    if(colorScheme < backColor.length-1)
      {
        colorScheme++;
      }
      else
      {
        colorScheme = 0;
      }
  }
  
  if(bt[4].touching)
  {
    if(bCount < 200)
      {
        bCount += 10;
      }
      else
      {
        bCount = 30;
      }
  }
}




/* 
Namensidee: CircleHell
*/
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
  
  
  
  
  public void display()
  {
    fill(bulletColor[colorScheme]);
    stroke(bulletColor[colorScheme]);
    
    ellipse(x, y, size, size);
    
    y = y + speed;
  }




  public void respawn()
  {
    if(y >= 720 + size/2)
      {
        score += 1;
        
        y = random(-730, -10);
        x = random(1080);
      }
  }
}
class Button
{
  String mode;
  boolean touching;
  boolean clicked;
  
  
  Button(float x, float y, float w, float h, int clr, int hClr, String bMode)
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
        
        rect(x-w*.01f/2, y-h*.01f/2, w*1.01f, h*1.01f);
        
        touching = true;
        
        if(mousePressed)
        {
          clicked = true;
        }
      }
    }
  }
  
  Button(float x, float y, float size, int clr, int hClr, String text, PFont bFont, String bMode)
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
        textSize(size*1.04f);
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
  
  
  
  
  public void display()
  {
    fill(playerColor[colorScheme]);
    stroke(playerColor[colorScheme]);
    
    ellipse(x, y, size, size);
  }
  
  
  
  
  public void border()
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
  
  
  
  
  public void controll()
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
class Screen
{
  Screen()
  {
    
  }
  
  
  
  
  public void homescreen()
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
  
  
  
  
  public void failscreen()
  {
    background(backColor[colorScheme]);
    
    grid();
    
    
    if(pulse)
    {
      index += .2f;
      if(index>=10)
      {
        pulse = false;
      }
    }
    else
    {
      index -= .2f;
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
    rotate(radians(20-index*.4f));
    textSize(50+index*.2f);
    text("score: "+PApplet.parseInt(lastScore), 0, 0); 
    
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
  
  
  
  
  public void grid()
  {
    stroke(bulletColor[colorScheme]);
    strokeWeight(.5f);
    
    for(int i=-10; i<=width; i+=100)
    {
      line(i, 0, i, height);
      line(0, i, width, i);
    }
  }
  
  
  
  
  public void scoreDisplay()
  { 
    if(pulse)
    {
      index += .2f;
      if(index>=10)
      {
        pulse = false;
      }
    }
    else
    {
      index -= .2f;
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
    rotate(radians(0-index*.3f));
    textSize(40+index*.3f);
    text("points: "+PApplet.parseInt(points), 0, 0);
    
    popMatrix();
    
    
    pushMatrix();
    
    translate(width/2, 50);
    rotate(radians(-2+index*.5f));
    textSize(45+index*.8f);
    text("highscore: "+PApplet.parseInt(highscore), 0, 0);
    
    popMatrix();
    
    
    pushMatrix();
    
    translate(930, 50);
    rotate(radians(3-index*.4f));
    textSize(35+index*.2f);
    text("last score: "+PApplet.parseInt(lastScore), 0, 0); 
    
    popMatrix(); 
  }
}
  public void settings() {  size(1080, 720, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bullethellgame_v0" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
